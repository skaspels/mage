
package mage.cards.k;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;
import mage.target.common.TargetSacrifice;

/**
 *
 * @author jeffwadsworth
 */
public final class KeldonFirebombers extends CardImpl {

    public KeldonFirebombers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Keldon Firebombers enters the battlefield, each player sacrifices all lands they control except for three.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KeldonFirebombersEffect()));

    }

    private KeldonFirebombers(final KeldonFirebombers card) {
        super(card);
    }

    @Override
    public KeldonFirebombers copy() {
        return new KeldonFirebombers(this);
    }
}

class KeldonFirebombersEffect extends OneShotEffect {

    public KeldonFirebombersEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "each player sacrifices all lands they control except for three";
    }

    private KeldonFirebombersEffect(final KeldonFirebombersEffect effect) {
        super(effect);
    }

    @Override
    public KeldonFirebombersEffect copy() {
        return new KeldonFirebombersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> landsToSacrifice = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int amount = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LANDS, playerId, game).size() - 3;
                if (amount > 0) {
                    Target target = new TargetSacrifice(amount, StaticFilters.FILTER_LANDS);
                    player.choose(outcome.Sacrifice, target, source, game);
                    for (UUID landId : target.getTargets()) {
                        Permanent land = game.getPermanent(landId);
                        if (land != null) {
                            landsToSacrifice.add(land);
                        }
                    }
                }
            }
        }
        for (Permanent land : landsToSacrifice) {
            land.sacrifice(source, game);
        }
        return true;
    }
}
