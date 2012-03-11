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

package com.kitfox.raven.editor.node.tools.common.shape.curveEdit;

import com.kitfox.raven.editor.RavenEditor;
import com.kitfox.raven.editor.node.tools.ToolProvider;
import com.kitfox.raven.editor.node.tools.ToolUser;
import com.kitfox.raven.util.PropertiesData;
import com.kitfox.raven.util.service.ServiceInst;
import java.awt.Component;
import java.util.Properties;

/**
 *
 * @author kitfox
 */
@ServiceInst(service=ToolProvider.class)
public class ToolCurveEditProvider extends ToolProvider<ToolCurveEditDispatch>
{
    public ToolCurveEditProvider()
    {
        super("Curve Edit", "/icons/tools/curveEdit.png", "/manual/tools/curveEdit.html");
    }

    @Override
    public void loadPreferences(Properties properties)
    {
        super.loadPreferences(properties);

        PropertiesData prop = new PropertiesData(properties);
    }

    @Override
    public Properties savePreferences()
    {
        Properties properties = new Properties();
        PropertiesData prop = new PropertiesData(properties);

        return properties;
    }

    @Override
    public ToolCurveEditDispatch create(ToolUser user)
    {
        return new ToolCurveEditDispatch(user, this);
    }

    @Override
    public Component createToolSettingsEditor(RavenEditor editor)
    {
        return new ToolCurveEditSettings(editor, this);
    }
}
