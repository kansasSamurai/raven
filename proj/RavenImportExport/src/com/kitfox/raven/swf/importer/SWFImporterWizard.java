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

package com.kitfox.raven.swf.importer;

import com.kitfox.raven.util.tree.NodeSymbol;
import com.kitfox.raven.wizard.RavenWizardPageIteratorSimple;
import java.util.Properties;

/**
 *
 * @author kitfox
 */
public class SWFImporterWizard extends RavenWizardPageIteratorSimple
{
    SWFImporterContext ctx;
    SWFImporterPanel panel;

    private SWFImporterWizard(SWFImporterContext ctx, SWFImporterPanel panel)
    {
        super(panel);

        this.ctx = ctx;
        this.panel = panel;
    }

    public static SWFImporterWizard create(NodeSymbol doc, Properties preferences)
    {
        SWFImporterContext ctx = new SWFImporterContext(doc, preferences);
        
        SWFImporterPanel panel = new SWFImporterPanel(ctx);
        return new SWFImporterWizard(ctx, panel);
    }
    
    @Override
    public Object finish()
    {
        ctx.savePreferences();
        ctx.doImport();
        return null;
    }
    
}
