/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

/*
 * CombatDialog.java
 *
 * Created on Feb 10, 2010, 3:35:02 PM
 */

package mage.client.dialog;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import mage.client.cards.BigCard;
import mage.client.game.CombatGroup;
import mage.view.CombatGroupView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatDialog extends MageDialog {

	private UUID gameId;
	private BigCard bigCard;
	private int lastX = 500;
	private int lastY = 300;

    /** Creates new form CombatDialog */
    public CombatDialog() {
    	
        JPanel contentPane = new JPanel() {
			private static final long serialVersionUID = -8283955788355547309L;
			public void paintComponent(Graphics g) {
				g.setColor(new Color(50, 50, 50, 100));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
        setContentPane(contentPane);
    	
        initComponents();
		this.setModal(false);
		
        combatArea.setOpaque(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        getRootPane().setOpaque(false);

		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    }

	public void init(UUID gameId, BigCard bigCard) {
		this.gameId = gameId;
		this.bigCard = bigCard;
	}

	public void showDialog(List<CombatGroupView> combat) {
		combatArea.removeAll();
		for (CombatGroupView group: combat) {
			CombatGroup combatGroup = new CombatGroup();
			combatGroup.init(gameId, bigCard);
			combatGroup.update(group);
			combatGroup.setVisible(true);
			combatArea.add(combatGroup);
			combatGroup.revalidate();
		}
		try {
			this.setSelected(true);
		} catch (PropertyVetoException ex) {
			Logger.getLogger(CombatDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		pack();
		this.revalidate();
		this.repaint();
		if (!this.isVisible())  {
			this.setVisible(true);
			this.setLocation(lastX, lastY);
		}
	}

	public void hideDialog() {
		this.lastX = this.getX();
		this.lastY = this.getY();
		this.setVisible(false);
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        combatArea = new javax.swing.JPanel();

        setResizable(true);
        setTitle("Combat");
        setNormalBounds(new java.awt.Rectangle(400, 200, 410, 307));
        setVisible(true);
        getContentPane().setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(combatArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel combatArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
