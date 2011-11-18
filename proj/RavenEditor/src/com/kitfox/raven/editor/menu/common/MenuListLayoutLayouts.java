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

package com.kitfox.raven.editor.menu.common;

import com.kitfox.raven.editor.RavenEditor;
import com.kitfox.raven.editor.RavenViewManager;
import com.kitfox.raven.editor.menu.MenuListProvider;
import com.kitfox.raven.util.service.ServiceInst;
import com.kitfox.xml.ns.raveneditorpreferences.ViewLayoutType;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;

/**
 *
 * @author kitfox
 */
@ServiceInst(service=MenuListProvider.class)
public class MenuListLayoutLayouts extends MenuListProvider
{

    @Override
    public void buildMenu(JMenu parent)
    {
        RavenViewManager viewManager = RavenEditor.inst().getViewManager();

        for (ViewLayoutType info: viewManager.getLayoutList())
        {
            parent.add(new ViewLayoutAction(info));
        }
    }

    //--------------------------

    class ViewLayoutAction extends AbstractAction
    {
        ViewLayoutType info;

        public ViewLayoutAction(ViewLayoutType info)
        {
            super(info.getName());
            this.info = info;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            RavenViewManager viewManager = RavenEditor.inst().getViewManager();
            viewManager.layoutViews(info);
        }
    }
}
