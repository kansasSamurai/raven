/*
 * Copyright 2011 Mark McKay
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * LayerNavigatorCellRendererPanel.java
 *
 * Created on Jun 24, 2009, 12:42:43 PM
 */

package com.kitfox.raven.editor.view.outliner;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author kitfox
 */
public class OutlinerCellRenderer extends javax.swing.JPanel
        implements TreeCellRenderer
{
    private static final long serialVersionUID = 1;


    /** Creates new form LayerNavigatorCellRendererPanel */
    public OutlinerCellRenderer()
    {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_text = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());
        add(label_text, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        UIDefaults uid = UIManager.getLookAndFeel().getDefaults();

        Color bg = selected ? uid.getColor("List.selectionBackground")
                : uid.getColor("List.background");
        setBackground(bg);

        OutlinerNode node = (OutlinerNode)value;
        label_text.setText(node.getName());
        label_text.setIcon(node.getIcon());
        
        return this;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel label_text;
    // End of variables declaration//GEN-END:variables

}
