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

package com.kitfox.raven.editor.node.exporter;

import com.kitfox.raven.util.tree.NodeSymbol;
import com.kitfox.raven.wizard.RavenWizardPageIterator;
import java.util.Properties;

/**
 *
 * @author kitfox
 */
abstract public class ExporterProvider
{
    private final String name;

    public ExporterProvider(String name)
    {
        this.name = name;
    }

    abstract public RavenWizardPageIterator createWizard(NodeSymbol doc);

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    public void loadPreferences(Properties properties)
    {
    }

    public Properties savePreferences()
    {
        return new Properties();
    }

}
