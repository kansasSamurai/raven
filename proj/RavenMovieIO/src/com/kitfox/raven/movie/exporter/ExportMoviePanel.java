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

import com.kitfox.raven.wizard.RavenWizardPage;
import java.awt.BorderLayout;
import java.awt.Component;

/**
 *
 * @author kitfox
 */
public class ExportMoviePanel extends javax.swing.JPanel
        implements RavenWizardPage
{
    public static final String PREF_FORMAT_SEQ = "formatSeq";
    public static final String PREF_FORMAT_FRAMES = "formatFrames";
    
    MovieExporterContext ctx;
    
    ExportMovieFramesPanel framesPanel;
    ExportMovieSeqPanel seqPanel;
    
    boolean updating = true;

    /**
     * Creates new form ExportMoviePanel
     */
    public ExportMoviePanel(MovieExporterContext ctx)
    {
        this.ctx = ctx;
        
        initComponents();

        framesPanel = new ExportMovieFramesPanel(ctx);
        seqPanel = new ExportMovieSeqPanel(ctx);
        
        updateFromContext();
    }

    private void updateFromContext()
    {
        updating = true;

        radio_formatSeq.setSelected(ctx.getFormat() == MovieExporterFormat.SEQ);
        radio_formatFrames.setSelected(ctx.getFormat() == MovieExporterFormat.FRAMES);
        check_frameCur.setSelected(ctx.isFrameCur());
        spinner_frameStart.setValue(ctx.getFrameStart());
        spinner_frameEnd.setValue(ctx.getFrameEnd());
        spinner_frameStride.setValue(ctx.getFrameStride());

        panel_formatArea.removeAll();
        switch (ctx.getFormat())
        {
            case FRAMES:
                panel_formatArea.add(framesPanel, BorderLayout.CENTER);
                break;
            case SEQ:
                panel_formatArea.add(seqPanel, BorderLayout.CENTER);
                break;
        }
        
        updating = false;
        
        revalidate();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_format = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        radio_formatSeq = new javax.swing.JRadioButton();
        radio_formatFrames = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        spinner_width = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        spinner_height = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        spinner_frameStart = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        spinner_frameEnd = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        spinner_frameStride = new javax.swing.JSpinner();
        check_frameCur = new javax.swing.JCheckBox();
        panel_formatArea = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        buttonGroup_format.add(radio_formatSeq);
        radio_formatSeq.setText("Sequence");
        radio_formatSeq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_formatSeqActionPerformed(evt);
            }
        });

        buttonGroup_format.add(radio_formatFrames);
        radio_formatFrames.setSelected(true);
        radio_formatFrames.setText("Frames");
        radio_formatFrames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_formatFramesActionPerformed(evt);
            }
        });

        jLabel1.setText("Width");

        spinner_width.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(640), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel2.setText("Height");

        spinner_height.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(480), Integer.valueOf(1), null, Integer.valueOf(1)));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Frame Range"));

        jLabel3.setText("Start Frame");

        spinner_frameStart.setModel(new javax.swing.SpinnerNumberModel());
        spinner_frameStart.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spinner_frameStartPropertyChange(evt);
            }
        });

        jLabel4.setText("End Frame");

        spinner_frameEnd.setModel(new javax.swing.SpinnerNumberModel());
        spinner_frameEnd.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spinner_frameEndPropertyChange(evt);
            }
        });

        jLabel5.setText("Frame Stride");

        spinner_frameStride.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        spinner_frameStride.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spinner_frameStridePropertyChange(evt);
            }
        });

        check_frameCur.setText("Current Frame");
        check_frameCur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_frameCurActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spinner_frameStart, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(spinner_frameEnd))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spinner_frameStride)))
                    .addComponent(check_frameCur))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(check_frameCur)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spinner_frameStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spinner_frameEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(spinner_frameStride, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(radio_formatFrames)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radio_formatSeq))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinner_width, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinner_height, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_formatSeq)
                    .addComponent(radio_formatFrames))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spinner_width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(spinner_height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.NORTH);

        panel_formatArea.setLayout(new java.awt.BorderLayout());
        add(panel_formatArea, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void radio_formatFramesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_radio_formatFramesActionPerformed
    {//GEN-HEADEREND:event_radio_formatFramesActionPerformed
        ctx.setFormat(MovieExporterFormat.FRAMES);
        
    }//GEN-LAST:event_radio_formatFramesActionPerformed

    private void radio_formatSeqActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_radio_formatSeqActionPerformed
    {//GEN-HEADEREND:event_radio_formatSeqActionPerformed
        ctx.setFormat(MovieExporterFormat.SEQ);

    }//GEN-LAST:event_radio_formatSeqActionPerformed

    private void check_frameCurActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_check_frameCurActionPerformed
    {//GEN-HEADEREND:event_check_frameCurActionPerformed
        ctx.setFrameCur(check_frameCur.isSelected());
    }//GEN-LAST:event_check_frameCurActionPerformed

    private void spinner_frameStartPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_spinner_frameStartPropertyChange
    {//GEN-HEADEREND:event_spinner_frameStartPropertyChange
        ctx.setFrameStart((Integer)spinner_frameStart.getValue());
    }//GEN-LAST:event_spinner_frameStartPropertyChange

    private void spinner_frameEndPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_spinner_frameEndPropertyChange
    {//GEN-HEADEREND:event_spinner_frameEndPropertyChange
        ctx.setFrameEnd((Integer)spinner_frameEnd.getValue());
    }//GEN-LAST:event_spinner_frameEndPropertyChange

    private void spinner_frameStridePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_spinner_frameStridePropertyChange
    {//GEN-HEADEREND:event_spinner_frameStridePropertyChange
        ctx.setFrameStride((Integer)spinner_frameStride.getValue());
    }//GEN-LAST:event_spinner_frameStridePropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_format;
    private javax.swing.JCheckBox check_frameCur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panel_formatArea;
    private javax.swing.JRadioButton radio_formatFrames;
    private javax.swing.JRadioButton radio_formatSeq;
    private javax.swing.JSpinner spinner_frameEnd;
    private javax.swing.JSpinner spinner_frameStart;
    private javax.swing.JSpinner spinner_frameStride;
    private javax.swing.JSpinner spinner_height;
    private javax.swing.JSpinner spinner_width;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getTitle()
    {
        return "Movie Exporter";
    }

    @Override
    public Component getComponent()
    {
        return this;
    }

}