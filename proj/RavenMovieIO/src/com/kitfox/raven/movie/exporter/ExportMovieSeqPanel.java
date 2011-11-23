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

package com.kitfox.raven.movie.exporter;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author kitfox
 */
public class ExportMovieSeqPanel extends javax.swing.JPanel
{
    JFileChooser chooser = new JFileChooser();
    
    final MovieExporterContext ctx;

    boolean updating = true;

    /**
     * Creates new form ExportMoviePanel
     */
    public ExportMovieSeqPanel(MovieExporterContext ctx)
    {
        this.ctx = ctx;
        initComponents();
        
//        chooser.setFileFilter(new ExportMovieFramesPanel.Filter());
        
//        for (String suf: ImageIO.getReaderFileSuffixes())
//        {
//            combo_imgFmt.addItem(suf);
//        }
        
        updateFromContext();
    }

    private void updateFromContext()
    {
        updating = true;

        text_file.setText(ctx.getSeqFile());
        
        updating = false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        text_file = new javax.swing.JTextField();
        bn_fileBrowse = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        combo_type = new javax.swing.JComboBox();

        jLabel1.setText("File");

        text_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_fileActionPerformed(evt);
            }
        });
        text_file.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_fileFocusLost(evt);
            }
        });

        bn_fileBrowse.setText("...");
        bn_fileBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bn_fileBrowseActionPerformed(evt);
            }
        });

        jLabel2.setText("Type");

        combo_type.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                combo_typePropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_file)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bn_fileBrowse))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 198, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(text_file, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bn_fileBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(240, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void text_fileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_text_fileActionPerformed
    {//GEN-HEADEREND:event_text_fileActionPerformed
        ctx.setSeqFile(text_file.getText());
    }//GEN-LAST:event_text_fileActionPerformed

    private void text_fileFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_text_fileFocusLost
    {//GEN-HEADEREND:event_text_fileFocusLost
        ctx.setSeqFile(text_file.getText());
    }//GEN-LAST:event_text_fileFocusLost

    private void bn_fileBrowseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bn_fileBrowseActionPerformed
    {//GEN-HEADEREND:event_bn_fileBrowseActionPerformed
        File file = new File(text_file.getText());
        if (file.exists())
        {
            chooser.setCurrentDirectory(file);
        }
        
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            file = chooser.getSelectedFile();
            text_file.setText(file.getAbsolutePath());
            ctx.setSeqFile(text_file.getText());
        }
    }//GEN-LAST:event_bn_fileBrowseActionPerformed

    private void combo_typePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_combo_typePropertyChange
    {//GEN-HEADEREND:event_combo_typePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_typePropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bn_fileBrowse;
    private javax.swing.JComboBox combo_type;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField text_file;
    // End of variables declaration//GEN-END:variables
}
