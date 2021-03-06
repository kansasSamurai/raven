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

package com.kitfox.raven.paint.control;

import com.kitfox.coyote.math.CyColor4f;
import com.kitfox.raven.paint.common.RavenPaintColor;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.event.ChangeEvent;

/**
 * A dynamic data structure for interactively building gradients.
 * 
 * @author kitfox
 */
public class Gradient
        implements StopModel<GradientStop>, ColorField, PropertyChangeListener
{
    ArrayList<StopModelListener> stopListeners = new ArrayList<StopModelListener>();
    ArrayList<ColorFieldListener> colorListeners = new ArrayList<ColorFieldListener>();

    ArrayList<GradientStop> midStops = new ArrayList<GradientStop>();
    private boolean horizontal;

    ArrayList<Span> spans = new ArrayList<Span>();

    private StopEditor stopEditor;

    public Gradient(GradientStop... stops)
    {
        Arrays.sort(stops);

        for (int i = 0; i < stops.length; ++i)
        {
            stops[i].addPropertyChangeListener(this);
            midStops.add(stops[i]);
        }

        buildSpans();
    }

    public Gradient(CyColor4f firstColor, CyColor4f lastColor)
    {
        this(new GradientStop(firstColor, 0), new GradientStop(lastColor, 1));
    }

    private void buildSpans()
    {
        ArrayList<GradientStop> stops = new ArrayList<GradientStop>();
        stops.addAll(midStops);
        Collections.sort(stops);

        spans.clear();
        GradientStop lag = stops.get(0);
        for (int i = 1; i < stops.size(); ++i)
        {
            GradientStop cur = stops.get(i);
            if (cur.getOffset() > lag.getOffset())
            {
                spans.add(new Span(lag.getColor(), cur.getColor(), lag.getOffset(), cur.getOffset() - lag.getOffset()));
            }
            lag = cur;
        }
    }

    @Override
    public void addStopModelListener(StopModelListener listener)
    {
        stopListeners.add(listener);
    }

    @Override
    public void removeStopModelListener(StopModelListener listener)
    {
        stopListeners.remove(listener);
    }

    private void fireStopModelChanged()
    {
        ChangeEvent evt = new ChangeEvent(this);
        for (StopModelListener l: new ArrayList<StopModelListener>(stopListeners))
        {
            l.stopModelChanged(evt);
        }
    }

    protected void fireBeginStopEdits()
    {
        ChangeEvent evt = new ChangeEvent(this);
        for (StopModelListener l: new ArrayList<StopModelListener>(stopListeners))
        {
            l.beginStopEdits(evt);
        }
    }

    protected void fireEndStopEdits()
    {
        ChangeEvent evt = new ChangeEvent(this);
        for (StopModelListener l: new ArrayList<StopModelListener>(stopListeners))
        {
            l.endStopEdits(evt);
        }
    }

    @Override
    public void setStopValue(GradientStop stop, float value)
    {
        stop.setOffset(value);
    }

    @Override
    public float getStopValue(GradientStop stop)
    {
        return stop.getOffset();
    }

    @Override
    public ArrayList<GradientStop> getStopObjects()
    {
        ArrayList<GradientStop> stops = new ArrayList<GradientStop>();
        stops.addAll(midStops);
        Collections.sort(stops);
        return stops;
    }

    @Override
    public void removeStop(GradientStop stop)
    {
        //Do not remove all stops
        if (midStops.size() <= 1)
        {
            return;
        }

        if (midStops.remove(stop))
        {
            buildSpans();
            fireColorModelChanged();
            fireStopModelChanged();
        }
    }

    @Override
    public void editStop(GradientStop stop)
    {
        if (stopEditor != null)
        {
            stopEditor.editStop(stop);
            buildSpans();
            fireColorModelChanged();
            fireStopModelChanged();
        }
    }

    @Override
    public void addStop(float value)
    {
        GradientStop stop = new GradientStop(
                toColor(value, value).asColor(), value);
        stop.addPropertyChangeListener(this);
        midStops.add(stop);
        buildSpans();
        fireColorModelChanged();
        fireStopModelChanged();
    }

    @Override
    public RavenPaintColor toColor(float x, float y)
    {
        float value = horizontal ? x : y;

        Span spanFirst = spans.get(0);
        if (value <= spanFirst.offset)
        {
            return new RavenPaintColor(spanFirst.first);
        }

        Span spanLast = spans.get(spans.size() - 1);
        if (value >= spanLast.offset + spanLast.length)
        {
            return new RavenPaintColor(spanLast.last);
        }

        for (Span span: spans)
        {
            if (span.contains(value))
            {
                return span.getColor(value);
            }
        }
        return RavenPaintColor.BLACK;
    }

    @Override
    public RavenPaintColor toDisplayColor(float x, float y)
    {
        return toColor(x, y);
    }

    @Override
    public Point2D.Float toCoords(RavenPaintColor color)
    {
        //return new Point2D.Float();
        return null;
    }

    @Override
    public void addColorFieldListener(ColorFieldListener listener)
    {
        colorListeners.add(listener);
    }

    @Override
    public void removeColorFieldListener(ColorFieldListener listener)
    {
        colorListeners.remove(listener);
    }

    protected void fireColorModelChanged()
    {
        ChangeEvent evt = new ChangeEvent(this);
        for (ColorFieldListener l: new ArrayList<ColorFieldListener>(colorListeners))
        {
            l.colorFieldChanged(evt);
        }
    }


    /**
     * @return the horizontal
     */
    public boolean isHorizontal()
    {
        return horizontal;
    }

    /**
     * @param horizontal the horizontal to set
     */
    public void setHorizontal(boolean horizontal)
    {
        this.horizontal = horizontal;
        fireColorModelChanged();
    }

    /**
     * @return the stopEditor
     */
    public StopEditor getStopEditor()
    {
        return stopEditor;
    }

    /**
     * @param stopEditor the stopEditor to set
     */
    public void setStopEditor(StopEditor stopEditor)
    {
        this.stopEditor = stopEditor;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        buildSpans();
        fireColorModelChanged();
        fireStopModelChanged();
    }

    @Override
    public void beginStopEdits()
    {
        fireBeginStopEdits();
    }

    @Override
    public void endStopEdits()
    {
        fireEndStopEdits();
    }

    //------------------------------------
    public interface StopEditor
    {
        public void editStop(GradientStop stop);
    }

    class Span
    {
        final CyColor4f first;
        final CyColor4f last;
        final float offset;
        final float length;

        Span(CyColor4f first, CyColor4f last, float offset, float length)
        {
            this.first = first;
            this.last = last;
            this.offset = offset;
            this.length = length;
        }

        boolean contains(float value)
        {
            return value >= offset && (value <= offset + length);
        }

        RavenPaintColor getColor(float value)
        {
            float alpha = (value - offset) / length;
            alpha = Math.min(Math.max(alpha, 0), 1);

            float r = (first.r * (1 - alpha) + last.r * alpha);
            float g = (first.g * (1 - alpha) + last.g * alpha);
            float b = (first.b * (1 - alpha) + last.b * alpha);
            float a = (first.a * (1 - alpha) + last.a * alpha);
            return new RavenPaintColor(r, g, b, a);
        }
    }
}
