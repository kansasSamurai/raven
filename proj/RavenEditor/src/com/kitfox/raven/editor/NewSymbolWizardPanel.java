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
 * NewDocumentWizardPanel.java
 *
 * Created on Feb 20, 2011, 9:16:46 AM
 */

package com.kitfox.raven.editor;

import com.kitfox.raven.util.tree.NodeSymbolProvider;
import com.kitfox.raven.util.tree.NodeSymbolProviderIndex;
import com.kitfox.raven.wizard.RavenWizardPage;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 *
 * @author kitfox
 */
public class NewSymbolWizardPanel extends JPanel
        implements RavenWizardPage
{
    public static final String WIZ_DESC = "Choose document";

    final NewSymbolWizard wizard;

    /** Creates new form NewDocumentWizardPanel */
    public NewSymbolWizardPanel(NewSymbolWizard wizard)
    {        
        initComponents();
        this.wizard = wizard;

        list_providers.setCellRenderer(new ProviderRenderer());

        ArrayList<NodeSymbolProvider> list = NodeSymbolProviderIndex.inst().getServices();
        Collections.sort(list);
        list_providers.setListData(list.toArray());
        
        if (!list.isEmpty())
        {
            list_providers.setSelectedIndex(0);
        }
    }

    @Override
    public String getTitle()
    {
        return WIZ_DESC;
    }

    @Override
    public Component getComponent()
    {
        return this;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        list_providers = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();

        list_providers.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                list_providersValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_providers);

        jLabel1.setText("New Document Type");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void list_providersValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_list_providersValueChanged
    {//GEN-HEADEREND:event_list_providersValueChanged
        NodeSymbolProvider prov = (NodeSymbolProvider)list_providers.getSelectedValue();
        wizard.setProvider(prov);

    }//GEN-LAST:event_list_providersValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList list_providers;
    // End of variables declaration//GEN-END:variables

    class ProviderRenderer extends JLabel implements ListCellRenderer
    {
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus)
        {
            if (value instanceof String)
            {
                //Empty lists will provide an empty string
                setText("");
                return this;
            }

            NodeSymbolProvider provider = (NodeSymbolProvider)value;
            setOpaque(true);
            if (isSelected)
            {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else
            {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            if (provider == null)
            {
                setText("");
                setIcon(null);
            }
            else
            {
                setText(provider.getName());
                setIcon(provider.getIcon());
            }
            return this;
        }
    }

}