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
 * ToolPanSettings.java
 *
 * Created on Dec 11, 2010, 1:31:09 AM
 */

package com.kitfox.raven.editor.node.tools.common;

import com.kitfox.raven.editor.RavenDocument;
import com.kitfox.raven.editor.RavenEditor;
import com.kitfox.raven.util.tree.NodeDocument;
import java.awt.geom.AffineTransform;

/**
 *
 * @author kitfox
 */
@Deprecated
public class ToolSubselectSettings extends javax.swing.JPanel
{
    RavenEditor editor;
    ToolSubselect.Provider toolProvider;

    /** Creates new form ToolPanSettings */
    public ToolSubselectSettings(RavenEditor editor, ToolSubselect.Provider toolProvider)
    {
        this.editor = editor;
        this.toolProvider = toolProvider;
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

        bn_reset = new javax.swing.JButton();

        bn_reset.setText("Reset View");
        bn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bn_resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bn_reset)
                .addContainerGap(305, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bn_reset)
                .addContainerGap(266, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bn_resetActionPerformed
        RavenDocument doc = editor.getDocument();
        if (doc == null)
        {
            return;
        }

        NodeDocument root = doc.getCurDocument();
        ServiceDeviceCamera service = root.getNodeService(ServiceDeviceCamera.class, false);
        service.setWorldToDeviceTransform(new AffineTransform());

    }//GEN-LAST:event_bn_resetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bn_reset;
    // End of variables declaration//GEN-END:variables

}
