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

package com.kitfox.raven.editor;

import com.kitfox.raven.editor.node.exporter.ExporterProvider;
import com.kitfox.raven.editor.node.exporter.ExporterProviderIndex;
import com.kitfox.xml.ns.raveneditorpreferences.PropertiesSetType;
import com.kitfox.xml.ns.raveneditorpreferences.PropertiesType;
import com.kitfox.xml.ns.raveneditorpreferences.PropertyType;
import com.kitfox.xml.ns.raveneditorpreferences.RavenEditorPreferencesType;
import java.util.Properties;

/**
 *
 * @author kitfox
 */
public class RavenExportManager
{
    final RavenEditor editor;

    public RavenExportManager(RavenEditor editor)
    {
        this.editor = editor;
    }

    void loadPreferences(RavenEditorPreferencesType pref)
    {
        if (pref == null)
        {
            return;
        }

        //Load settings
        PropertiesSetType propViews = pref.getExporterProperties();
        if (propViews != null)
        {
            for (PropertiesType prop: propViews.getProperties())
            {
                Properties props = new Properties();
                for (PropertyType type: prop.getProperty())
                {
                    props.setProperty(type.getName(), type.getValue());
                }

                ExporterProvider prov = ExporterProviderIndex.inst().getProvider(prop.getClazz());
                if (prov == null)
                {
                    continue;
                }
                prov.loadPreferences(props);
            }
        }
    }

    void export(RavenEditorPreferencesType pref)
    {
        PropertiesSetType propSet = new PropertiesSetType();
        pref.setExporterProperties(propSet);

        for (ExporterProvider prov: ExporterProviderIndex.inst().getProviders())
        {
            PropertiesType propsType = new PropertiesType();
            propsType.setClazz(prov.getClass().getCanonicalName().replace('$', '.'));
            propSet.getProperties().add(propsType);

            Properties saveProp = prov.savePreferences();
            for (String name: saveProp.stringPropertyNames())
            {
                PropertyType prop = new PropertyType();
                propsType.getProperty().add(prop);

                prop.setName(name);
                prop.setValue(saveProp.getProperty(name));
            }
        }
    }

}
