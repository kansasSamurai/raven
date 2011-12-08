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

package com.kitfox.coyote.shape.bezier;

import com.kitfox.coyote.math.CyVector2d;
import com.kitfox.coyote.shape.PathConsumer;

/**
 *
 * @author kitfox
 */
public class BezierLine2d extends BezierCurve2d
{
    final double ax0;
    final double ay0;
    final double ax1;
    final double ay1;

    public BezierLine2d(double ax0, double ay0, double ax1, double ay1)
    {
        this.ax0 = ax0;
        this.ay0 = ay0;
        this.ax1 = ax1;
        this.ay1 = ay1;
    }

    @Override
    public int getOrder()
    {
        return 2;
    }

    @Override
    public BezierLine2d reverse()
    {
        return new BezierLine2d(ax1, ay1, ax0, ay0);
    }

    @Override
    public double getTanInX()
    {
        return ax1 - ax0;
    }

    @Override
    public double getTanInY()
    {
        return ay1 - ay0;
    }

    @Override
    public double getTanOutX()
    {
        return ax1 - ax0;
    }

    @Override
    public double getTanOutY()
    {
        return ay1 - ay0;
    }

    @Override
    public double getStartX()
    {
        return ax0;
    }

    @Override
    public double getStartY()
    {
        return ay0;
    }

    @Override
    public double getEndX()
    {
        return ax1;
    }

    @Override
    public double getEndY()
    {
        return ay1;
    }

    @Override
    public double getMinX()
    {
        return Math.min(ax0, ax1);
    }
    
    @Override
    public double getMinY()
    {
        return Math.min(ay0, ay1);
    }
    
    @Override
    public double getMaxX()
    {
        return Math.max(ax0, ax1);
    }
    
    @Override
    public double getMaxY()
    {
        return Math.max(ay0, ay1);
    }
    
    @Override
    public BezierLine2d[] split(double t)
    {
        double bx0 = ax0 + t * (ax1 - ax0);
        double by0 = ay0 + t * (ay1 - ay0);

        return new BezierLine2d[]{
            new BezierLine2d(ax0, ay0, bx0, by0),
            new BezierLine2d(bx0, by0, ax1, ay1)
        };
    }

    @Override
    public void evaluate(double t, CyVector2d pos, CyVector2d tan)
    {
        double bx0 = ax0 + t * (ax1 - ax0);
        double by0 = ay0 + t * (ay1 - ay0);

        if (pos != null)
        {
            pos.set(bx0, by0);
        }

        if (tan != null)
        {
            tan.set(ax1 - ax0, ay1 - ay0);
        }
    }

    @Override
    public BezierPoint2d getDerivative()
    {
        return new BezierPoint2d(ax1 - ax0, ay1 - ay0);
    }

    @Override
    public BezierCubic2d asCubic()
    {
        return new BezierCubic2d(ax0, ay0, 
                (2 * ax0 + ax1) / 3, (2 * ay0 + ay1) / 3, 
                (ax0 + 2 * ax1) / 3, (ay0 + 2 * ay1) / 3, 
                ax1, ay1);
    }

    @Override
    public double getCurvatureSquared()
    {
        return 0;
    }

    @Override
    public BezierLine2d offset(double width)
    {
        CyVector2d v = new CyVector2d(ax1 - ax0, ay1 - ay0);
        v.normalize();
        v.rotCCW90();
        v.scale(width);

        return new BezierLine2d(ax0 + v.x, ay0 + v.y,
                ax1 + v.x, ay1 + v.y);
    }

    @Override
    public void append(PathConsumer out)
    {
        out.lineTo(ax1, ay1);
    }


}