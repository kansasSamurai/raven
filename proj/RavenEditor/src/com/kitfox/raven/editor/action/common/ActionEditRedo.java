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

package com.kitfox.raven.editor.action.common;

import com.kitfox.raven.editor.RavenDocument;
import com.kitfox.raven.editor.RavenEditor;
import com.kitfox.raven.editor.action.ActionProvider;
import com.kitfox.raven.util.service.ServiceInst;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 *
 * @author kitfox
 */
public class ActionEditRedo extends AbstractAction
{
    private static final String name = "Redo";
    private static final String id = "editRedo";
    private static final KeyStroke stroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Z,
            ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK);

    public ActionEditRedo()
    {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        RavenDocument doc = RavenEditor.inst().getDocument();
        if (doc == null)
        {
            return;
        }
        doc.getHistory().redo();
    }

    @ServiceInst(service=ActionProvider.class)
    public static class Provider extends ActionProvider
    {
        public Provider()
        {
            super(id, name, stroke, new ActionEditRedo());
        }
    }
}
