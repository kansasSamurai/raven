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

package com.kitfox.swf.tags.action.swf5;

import com.kitfox.swf.dataType.SWFDataReader;
import com.kitfox.swf.tags.action.ActionRecord;
import com.kitfox.swf.tags.action.ActionRecordLoader;
import java.io.IOException;

/**
 *
 * @author kitfox
 */
public class ActionWith extends ActionRecord
{
    public static class Loader extends ActionRecordLoader
    {
        public Loader()
        {
            super(CODE);
        }

        @Override
        public ActionRecord read(SWFDataReader in, int length) throws IOException
        {
            int size = in.getUI16();
            return new ActionWith(size);
        }
    }

    public static final int CODE = 0x94;

    int size;

    public ActionWith(int size) {
        this.size = size;
    }
}
