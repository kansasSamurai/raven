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

package com.kitfox.coyote.material.marquis;

import com.kitfox.coyote.drawRecord.CyDrawRecordFactory;

/**
 *
 * @author kitfox
 */
public class CyMaterialMarquisDrawRecordFactory
        extends CyDrawRecordFactory<CyMaterialMarquisDrawRecord>
{
    static CyMaterialMarquisDrawRecordFactory instance = new CyMaterialMarquisDrawRecordFactory();

    private CyMaterialMarquisDrawRecordFactory()
    {
    }

    public static CyMaterialMarquisDrawRecordFactory inst()
    {
        return instance;
    }

    @Override
    protected CyMaterialMarquisDrawRecord createRecord()
    {
        CyMaterialMarquisDrawRecord rec = new CyMaterialMarquisDrawRecord();
        return rec;
    }

    @Override
    protected void recycleRecord(CyMaterialMarquisDrawRecord rec)
    {
        super.recycleRecord(rec);
    }

}
