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

package com.kitfox.raven.editor.node.tools.common.shape.pen;

import com.kitfox.coyote.material.color.CyMaterialColorDrawRecord;
import com.kitfox.coyote.material.color.CyMaterialColorDrawRecordFactory;
import com.kitfox.coyote.math.CyMatrix4d;
import com.kitfox.coyote.math.CyVector2d;
import com.kitfox.coyote.renderer.CyDrawStack;
import com.kitfox.coyote.renderer.CyVertexBuffer;
import com.kitfox.coyote.renderer.vertex.CyVertexBufferDataSquare;
import com.kitfox.coyote.shape.CyPath2d;
import com.kitfox.coyote.shape.CyRectangle2d;
import com.kitfox.coyote.shape.CyRectangle2i;
import com.kitfox.coyote.shape.ShapeLinesProvider;
import com.kitfox.coyote.shape.bezier.BezierCubic2i;
import com.kitfox.coyote.shape.bezier.BezierCurve2i;
import com.kitfox.coyote.shape.bezier.BezierLine2i;
import com.kitfox.coyote.shape.bezier.PickPoint;
import com.kitfox.coyote.shape.bezier.path.BezierPathVertex2i;
import com.kitfox.coyote.shape.bezier.path.cut.Coord;
import com.kitfox.raven.editor.node.scene.RavenSymbolRoot;
import com.kitfox.raven.editor.node.scene.RenderContext;
import com.kitfox.raven.editor.node.tools.common.shape.MeshUtil;
import com.kitfox.raven.editor.node.tools.common.shape.PathUtil;
import com.kitfox.raven.paint.RavenPaint;
import com.kitfox.raven.paint.RavenPaintLayout;
import com.kitfox.raven.paint.RavenStroke;
import com.kitfox.raven.shape.network.NetworkDataEdge;
import com.kitfox.raven.shape.network.NetworkDataVertex;
import com.kitfox.raven.shape.network.NetworkPath;
import com.kitfox.raven.shape.network.keys.NetworkDataTypePaint;
import com.kitfox.raven.shape.network.keys.NetworkDataTypePaintLayout;
import com.kitfox.raven.shape.network.keys.NetworkDataTypeStroke;
import com.kitfox.raven.shape.network.pick.NetworkPathHandles;
import com.kitfox.raven.shape.network.pick.NetworkPathHandles.HandleEdge;
import com.kitfox.raven.util.tree.NodeObject;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author kitfox
 */
public class ToolPenPath extends ToolPenDelegate
{
    ArrayList<Step> plan = new ArrayList<Step>();
    MouseEvent mouseStart;
    MouseEvent mouseTrackEvt;
    private final ServiceBezierPath servPath;
    
    NodeObject node;
    NetworkPathHandles cacheHandles;

    protected ToolPenPath(ToolPenDispatch dispatch, 
            NodeObject node,
            ServiceBezierPath servPath)
    {
        super(dispatch);
        this.node = node;
        this.servPath = servPath;
    }

    private NetworkPathHandles getPathHandles()
    {
        NetworkPath path = getPath();
        if (cacheHandles == null || cacheHandles.getPath() != path)
        {
            cacheHandles = path == null ? null : new NetworkPathHandles(path);
        }
        return cacheHandles;
    }

    private NetworkPath getPath()
    {
        return servPath.getNetworkPath();
//        return cachePath;
    }
    
    private void setPath(NetworkPath path, boolean history)
    {
        servPath.setNetworkPath(path, history);
    }

    protected CyVector2d xformDev2PathPoint(CyVector2d v, boolean snapGrid)
    {
        v = xformDev2LocalPoint(v, snapGrid);
        v.scale(100);
        return v;
    }

    private Step startStep(MouseEvent evt)
    {
        NetworkPathHandles handles = getPathHandles();
        NetworkPath path = handles.getPath();
        RavenSymbolRoot root = getDocument();
        //GraphLayout layout = getGraphLayout();
        
        CyVector2d pickPt = xformDev2PathPoint(
                new CyVector2d(evt.getX(), evt.getY()), true);

        //Look for existing path vertex to clamp to
        BezierPathVertex2i<NetworkDataVertex> v = path.getClosestVertex(pickPt.x, pickPt.y);
        double pickRad = root.getGraphRadiusPick() * 100;
        double pickRadiusSq = pickRad * pickRad;
        
        if (v != null &&
                v.getCoord().getDistSquared(pickPt) < pickRadiusSq)
        {
            Coord c = v.getCoord();
            return new Step(new CyVector2d(c.x, c.y));
        }
        
        //Try clamping to point we are currently plotting
        for (Step step: plan)
        {
            if (step.point.distanceSquared(pickPt) < pickRadiusSq)
            {
                return new Step(new CyVector2d(step.point));
            }
        }
        
//        //Check for clamp to edge in path
//        BezierPathEdge2i<NetworkDataEdge> e = 
//                path.getClosestEdge(pickPt.x, pickPt.y, pickRadiusSq);
//        if (e != null)
//        {
//            BezierCurve2i curve = e.asCurve();
//            PickPoint pt = curve.getClosestPoint(pickPt.x, pickPt.y);
//
//            if (pt.getDistSquared() <= pickRadiusSq)
//            {
//                Step s = new Step(new CyVector2d(pt.getX(), pt.getY()));
//                s.splitEdge = handles.getEdgeHandle(e);
//                s.splitPoint = pt;
//                return s;
//            }
//        }
        
        //We're in free space.  Just use pick coord
        return new Step(pickPt);
    }

    private void applyTangent(MouseEvent evt)
    {
        CyVector2d dragPt = xformDev2PathPoint(
                new CyVector2d(evt.getX(), evt.getY()), false);
        
        Step step = plan.get(plan.size() - 1);
        dragPt.sub(step.point);
        step.tangent = dragPt;
    }
    
    private void commit()
    {
        NetworkPathHandles handles = getPathHandles();
        NetworkPath oldPath = handles.getPath();
        NetworkPath newPath = new NetworkPath(oldPath);
        
        //Get decoration info
        CyRectangle2i bounds = newPath.getBounds();
        for (int i = 0; i < plan.size() - 1; ++i)
        {
            Step s0 = plan.get(i);
            
            if (bounds == null)
            {
                bounds = new CyRectangle2i((int)s0.point.x, (int)s0.point.y);
            }
            bounds.union((int)s0.point.x, (int)s0.point.y);
            if (s0.tangent != null)
            {
                bounds.union((int)(s0.point.x + s0.tangent.x), 
                        (int)(s0.point.y + s0.tangent.y));
                bounds.union((int)(s0.point.x - s0.tangent.x), 
                        (int)(s0.point.y - s0.tangent.y));
            }
        }
        
        RavenSymbolRoot root = (RavenSymbolRoot)getDocument();
        RavenPaint strokePaint = root.getStrokePaint();
        RavenStroke stroke = root.getStrokeShape();
//        RavenPaintLayout layout = new RavenPaintLayout(bounds);
        CyRectangle2d boundsLocal = new CyRectangle2d(
                bounds.getX() / 100, bounds.getY() / 100,
                bounds.getWidth() / 100, bounds.getHeight() / 100);
        RavenPaintLayout layout = new RavenPaintLayout(boundsLocal);
        
        RavenPaint fillPaint = root.getFillPaint();
        
        //Add curves
        CyPath2d curveToAdd = null;
        for (int i = 0; i < plan.size() - 1; ++i)
        {
            Step s0 = plan.get(i);
            Step s1 = plan.get(i + 1);
            
            BezierCurve2i curve = buildCurve(s0, s1);
            
            NetworkDataEdge data = new NetworkDataEdge();
            data.putEdge(NetworkDataTypePaint.class, strokePaint);
            data.putEdge(NetworkDataTypeStroke.class, stroke);
            data.putEdge(NetworkDataTypePaintLayout.class, layout);
            
            //newPath.addEdge(curve, data);
            if (curveToAdd == null)
            {
                curveToAdd = new CyPath2d();
                curveToAdd.moveTo(curve.getStartX(), curve.getStartY());
            }
            if (curve instanceof BezierLine2i)
            {
                curveToAdd.lineTo(curve.getEndX(), curve.getEndY());
            }
            else
            {
                BezierCubic2i cubic = curve.asCubic();
                curveToAdd.cubicTo(cubic.getAx1(), cubic.getAy1(),
                        cubic.getAx2(), cubic.getAy2(),
                        cubic.getAx3(), cubic.getAy3());
            }
        }
        
        newPath.append(curveToAdd);
        
        //Set value
        setPath(newPath, true);
        
        dispatch.delegateDone();
    }
    
//    private void decorateFace(CutLoop face, RavenPaint fillPaint, RavenPaintLayout fillLayout)
//    {
//        RavenPaint curPaint = null;
//        RavenPaintLayout curLayout = null;
//        
//        //Check existing face edges to see if a color is already set
//        ArrayList<CutSegHalf> segs = face.getSegs();
//        for (CutSegHalf half: segs)
//        {
//            BezierPathEdge2i<NetworkDataEdge> e 
//                    = (BezierPathEdge2i)half.getEdge();
//            if (e == null)
//            {
//                //Skip extra segments that were added by cutter to
//                // connect graph
//                continue;
//            }
//            NetworkDataEdge data = e.getData();
//            RavenPaint edgePaint;
//            RavenPaintLayout edgeLayout;
//            if (half.isRight())
//            {
//                edgePaint = data.getRight(NetworkDataTypePaint.class);
//                edgeLayout = data.getRight(NetworkDataTypePaintLayout.class);
//            }
//            else
//            {
//                edgePaint = data.getLeft(NetworkDataTypePaint.class);
//                edgeLayout = data.getLeft(NetworkDataTypePaintLayout.class);
//            }
//            
//            if (curPaint == null)
//            {
//                curPaint = edgePaint;
//            }
//            if (curLayout == null)
//            {
//                curLayout = edgeLayout;
//            }
//        }
//        
//        //Use default color if face has none
//        if (curPaint == null)
//        {
//            curPaint = fillPaint;
//        }
//        if (curLayout == null)
//        {
//            curLayout = fillLayout;
//        }
//        
//        //Decorate face
//        for (CutSegHalf half: segs)
//        {
//            BezierPathEdge2i<NetworkDataEdge> e 
//                    = (BezierPathEdge2i)half.getEdge();
//            if (e == null)
//            {
//                continue;
//            }
//            NetworkDataEdge data = e.getData();
//            if (half.isRight())
//            {
//                data.putRight(NetworkDataTypePaint.class, curPaint);
//                data.putRight(NetworkDataTypePaintLayout.class, curLayout);
//            }
//            else
//            {
//                data.putLeft(NetworkDataTypePaint.class, curPaint);
//                data.putLeft(NetworkDataTypePaintLayout.class, curLayout);
//            }
//        }
//    }
    
    @Override
    protected void click(MouseEvent evt)
    {
        Step step = startStep(evt);
        plan.add(step);
        
        mouseTrackEvt = null;
    }

    @Override
    protected void startDrag(MouseEvent evt)
    {
        Step step = startStep(evt);
        plan.add(step);
        
        mouseStart = evt;
    }
    
    @Override
    protected void dragTo(MouseEvent evt)
    {
        applyTangent(evt);
    }

    @Override
    protected void endDrag(MouseEvent evt)
    {
        applyTangent(evt);
        mouseStart = null;
    }

    @Override
    public void mouseMoved(MouseEvent evt)
    {
//        super.mouseMoved(evt);
        mouseTrackEvt = evt;
    }

    @Override
    public void keyPressed(KeyEvent evt)
    {
        switch (evt.getKeyCode())
        {
            case KeyEvent.VK_DELETE:
                if (!plan.isEmpty())
                {
                    plan.remove(plan.size() - 1);
                }
                return;
            case KeyEvent.VK_ESCAPE:
                cancel();
                return;
            case KeyEvent.VK_ENTER:
                commit();
                return;
        }
        
        super.keyPressed(evt);
    }

    @Override
    public void cancel()
    {
        dispatch.delegateDone();
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void render(RenderContext ctx)
    {
        super.render(ctx);

        CyDrawStack stack = ctx.getDrawStack();

        PathUtil.drawGraph(stack, getPathHandles(), 
                getSelection(), node, servPath.getGraphToWorldXform());
        
        if (plan.isEmpty())
        {
            return;
        }
        
        //Build graph
        CyPath2d path = new CyPath2d();
        ArrayList<CyVector2d> verts = new ArrayList<CyVector2d>();
        
        Step firstStep = plan.get(0);
        path.moveTo(firstStep.point.x, firstStep.point.y);
        verts.add(new CyVector2d(firstStep.point.x, firstStep.point.y));

        for (int i = 0; i < plan.size() - 1; ++i)
        {
            Step s0 = plan.get(i);
            Step s1 = plan.get(i + 1);
            
            BezierCurve2i curve = buildCurve(s0, s1);

            if (curve instanceof BezierLine2i)
            {
                path.lineTo(curve.getEndX(), curve.getEndY());
                verts.add(new CyVector2d(curve.getEndX(), curve.getEndY()));
            }
            else
            {
                BezierCubic2i c = (BezierCubic2i)curve;
                path.cubicTo(c.getAx1(), c.getAy1(), 
                        c.getAx2(), c.getAy2(), 
                        c.getAx3(), c.getAy3());
                verts.add(new CyVector2d(c.getAx3(), c.getAy3()));
            }
        }
        
        //Draw to cursor
        if (mouseTrackEvt != null)
        {
            CyVector2d pt = xformDev2PathPoint(
                    new CyVector2d(mouseTrackEvt.getX(), mouseTrackEvt.getY()), true);
            path.lineTo(pt.x, pt.y);
        }
        
        //Add tangents
        {
            Step lastStep = plan.get(plan.size() - 1);
            Step exitStep = lastStep;
            
            //Dragging through current line
            if (mouseStart != null)
            {
                //Tangent of dragging
                CyVector2d p = lastStep.point;
                CyVector2d t = lastStep.tangent;
                path.moveTo(p.x - t.x, p.y - t.y);
                path.lineTo(p.x + t.x, p.y + t.y);

                verts.add(new CyVector2d(p.x - t.x, p.y - t.y));
                verts.add(new CyVector2d(p.x + t.x, p.y + t.y));
                
                exitStep = plan.size() >= 2 ? plan.get(plan.size() - 2) : null;
            }

            if (exitStep != null && exitStep.tangent != null)
            {
                //End tangent of prev point
                CyVector2d p = exitStep.point;
                CyVector2d t = exitStep.tangent;
                path.moveTo(p.x, p.y);
                path.lineTo(p.x + t.x, p.y + t.y);

                verts.add(new CyVector2d(p.x + t.x, p.y + t.y));
            }
        }
        
        
//        CyDrawStack stack = ctx.getDrawStack();
        stack.pushFrame(null);

//        GraphLayout graphLayout = getGraphLayout();
        RavenSymbolRoot root = getDocument();
        
        //Draw curves and handles
        {
            ShapeLinesProvider prov = new ShapeLinesProvider(path);
            CyVertexBuffer linePath = new CyVertexBuffer(prov);

            CyMatrix4d l2d = getLocalToDevice(null);
            l2d.scale(1 / 100.0, 1 / 100.0, 1);

            CyMaterialColorDrawRecord rec =
                    CyMaterialColorDrawRecordFactory.inst().allocRecord();

            CyMatrix4d l2w = getLocalToWorld(null);
            stack.setModelXform(l2w);

            CyMatrix4d mvp = stack.getModelViewProjXform();
            mvp.scale(1 / 100.0, 1 / 100.0, 1);

            rec.setMesh(linePath);
            rec.setColor(root.getGraphColorEdge().asColor());
            rec.setOpacity(1);
    //mvp.scale(10, 100, 1);
            rec.setMvpMatrix(mvp);

    //rec.setPath(CyVertexBufferDataSquare.inst().getBuffer());
    //l2d.setIdentity();
    //l2d.scale(10, 100, 1);
    //rec.setMvpMatrix(l2d);

            stack.addDrawRecord(rec);
        }
        
        //Draw verts
        {
            CyMatrix4d l2w = getLocalToWorld(null);
            stack.setModelXform(l2w);
            
            CyMatrix4d mv = stack.getModelViewXform();
            CyMatrix4d proj = stack.getProjXform();

            CyMatrix4d mvp = new CyMatrix4d();
                
            for (CyVector2d v: verts)
            {
                CyMaterialColorDrawRecord rec =
                        CyMaterialColorDrawRecordFactory.inst().allocRecord();

                CyVector2d viewVert = new CyVector2d(v);
                //From path space to local space
                viewVert.scale(1 / 100f);
                mv.transformPoint(viewVert);

                mvp.set(proj);
//                mvp.scale(1 / 100.0, 1 / 100.0, 1);
                
                mvp.translate(viewVert.x, viewVert.y, 0);
//                int ptRad = graphLayout.getPointRadiusDisplay() * 200;
                float ptRad = root.getGraphRadiusDisplay() * 2;
                mvp.scale(ptRad, ptRad, ptRad);
                mvp.translate(-.5, -.5, 0);

                rec.setMesh(CyVertexBufferDataSquare.inst().getBuffer());
                rec.setColor(root.getGraphColorEdge().asColor());
                rec.setOpacity(1);
                rec.setMvpMatrix(mvp);

                stack.addDrawRecord(rec);                
            }
        }
        
        stack.popFrame();
    }
    
    private BezierCurve2i buildCurve(Step s0, Step s1)
    {
        if (s0.tangent == null && s1.tangent == null)
        {
            return new BezierLine2i(
                    (int)s0.point.x, (int)s0.point.y,
                    (int)s1.point.x, (int)s1.point.y);
        }

        double tx0 = s0.tangent == null ? 0 : s0.tangent.x;
        double ty0 = s0.tangent == null ? 0 : s0.tangent.y;
        double tx1 = s1.tangent == null ? 0 : s1.tangent.x;
        double ty1 = s1.tangent == null ? 0 : s1.tangent.y;

        return new BezierCubic2i(
                (int)s0.point.x, (int)s0.point.y,
                (int)(s0.point.x + tx0), (int)(s0.point.y + ty0), 
                (int)(s1.point.x - tx1), (int)(s1.point.y - ty1), 
                (int)s1.point.x, (int)s1.point.y);
    }
    
    //-------------------------
    private class Step
    {
        CyVector2d point;
        
        //If not null, creating smooth point with tangent
        CyVector2d tangent;
        
        //If not null, cut edge at this point to get 
        //BezierPathEdge2i<NetworkDataEdge> splitEdge;
        HandleEdge splitEdge;
        PickPoint splitPoint;

        public Step(CyVector2d point)
        {
            this.point = point;
        }
    }
    
}
