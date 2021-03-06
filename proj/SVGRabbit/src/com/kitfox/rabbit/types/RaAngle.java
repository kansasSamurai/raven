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

package com.kitfox.rabbit.types;

/**
 *
 * @author kitfox
 */
public class RaAngle extends Number
{
    static public enum Type { NONE, DEG, GRAD, RAD }

    final float value;
    final Type type;

    public RaAngle(float value, Type type)
    {
        this.value = value;
        this.type = type;
    }

    public String getUnitsStrn()
    {
        switch (type)
        {
            case DEG:
                return "deg";
            case GRAD:
                return "grad";
            case RAD:
                return "rad";
            default:
            case NONE:
                return "";
        }
    }

    @Override
    public int intValue()
    {
        return (int)value;
    }

    @Override
    public long longValue()
    {
        return (long)value;
    }

    @Override
    public float floatValue()
    {
        return value;
    }

    @Override
    public double doubleValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return "" + value + getUnitsStrn();
    }
}
