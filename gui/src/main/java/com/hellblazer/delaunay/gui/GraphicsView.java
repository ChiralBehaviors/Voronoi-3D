package com.hellblazer.delaunay.gui;

import java.awt.GraphicsConfiguration;
import java.util.Collection;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.hellblazer.delaunay.Vertex;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.picking.behaviors.PickRotateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickZoomBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class GraphicsView extends Canvas3D {
    private static final Color3f   BACKGROUND_COLOR                      = new Color3f(0.0F,
                                                                                       0.0F,
                                                                                       0.0F);
    private static final int       NODE_ALLOW_AUTO_COMPUTE_BOUNDS_WRITE  = 10;
    private static final int       NODE_ALLOW_LOCAL_TO_VWORLD_READ       = 11;
    private static final int       NODE_ENABLE_PICK_REPORTING            = 1;
    private static final long      serialVersionUID                      = 1L;
    private static final int       TRANSFORM_GROUP_ALLOW_TRANSFORM_READ  = 17;
    private static final int       TRANSFORM_GROUP_ALLOW_TRANSFORM_WRITE = 18;
    private static final Vector3f  VIEW_POSITION                         = new Vector3f(0.0F,
                                                                                        0.0F,
                                                                                        0.0F);
    protected static final int     BRANCH_GROUP_ALLOW_DETACH             = 17;
    protected static final Color3f COLOR_OF_DT                           = new Color3f(1.0F,
                                                                                       1.0F,
                                                                                       0.0F);
    protected static final Color3f COLOR_OF_HIGHLIGHTED_REGION           = new Color3f(1.0F,
                                                                                       0.0F,
                                                                                       0.0F);
    protected static final Color3f COLOR_OF_VD                           = new Color3f(1.0F,
                                                                                       1.0F,
                                                                                       1.0F);
    protected static final Color3f DEFAULT_COLOR                         = new Color3f(1.0F,
                                                                                       1.0F,
                                                                                       1.0F);
    protected static final int     GROUP_ALLOW_CHILDREN_EXTEND           = 14;
    protected static final int     GROUP_ALLOW_CHILDREN_READ             = 12;
    protected static final int     GROUP_ALLOW_CHILDREN_WRITE            = 13;

    protected static Point3f[] convertToPoint3f(List<Vertex> somePoints) {
        Point3f tmp[] = new Point3f[somePoints.size()];
        int i = 0;
        for (Vertex v : somePoints) {
            tmp[i++] = v.asPoint3f();
        }

        return tmp;
    }

    private BranchGroup      scene;
    protected BranchGroup    diagram;
    protected TransformGroup transformGroup;

    public GraphicsView(GraphicsConfiguration gc) {
        super(gc);
    }

    protected void createDiagram() {
        if (diagram != null) {
            diagram.detach();
        }
        if (transformGroup != null) {
            transformGroup.removeAllChildren();
        }
        diagram = new BranchGroup();
        diagram.setCapability(BRANCH_GROUP_ALLOW_DETACH);
        diagram.setCapability(GROUP_ALLOW_CHILDREN_READ);
        diagram.setCapability(GROUP_ALLOW_CHILDREN_WRITE);
        diagram.setCapability(GROUP_ALLOW_CHILDREN_EXTEND);
    }

    protected void createSceneGraph() {
        scene = new BranchGroup();
        scene.setCapability(GROUP_ALLOW_CHILDREN_READ);
        Transform3D transform = new Transform3D();
        BoundingSphere bounds = new BoundingSphere();
        transform.setTranslation(VIEW_POSITION);
        Background background = new Background(BACKGROUND_COLOR);
        background.setApplicationBounds(bounds);
        scene.addChild(background);
        transformGroup = new TransformGroup();
        transformGroup.setCapability(GROUP_ALLOW_CHILDREN_READ);
        setCapabilities(transformGroup);
        PickRotateBehavior pickRotate = new PickRotateBehavior(scene, this,
                                                               bounds);
        pickRotate.setCapability(GROUP_ALLOW_CHILDREN_READ);
        scene.addChild(pickRotate);
        PickZoomBehavior pickZoom = new PickZoomBehavior(scene, this, bounds);
        scene.addChild(pickZoom);
        PickTranslateBehavior pickTranslate = new PickTranslateBehavior(scene,
                                                                        this,
                                                                        bounds);
        pickTranslate.setCapability(GROUP_ALLOW_CHILDREN_READ);
        scene.addChild(pickTranslate);
        scene.addChild(transformGroup);
        scene.setCapability(GROUP_ALLOW_CHILDREN_READ);
        SimpleUniverse simpleUniverse = new SimpleUniverse(this);
        simpleUniverse.getViewingPlatform()
                      .setNominalViewingTransform();
        simpleUniverse.addBranchGraph(scene);
    }

    protected TransformGroup createSphereAround(Point3f aPoint3f,
                                                Color3f aColor,
                                                double aRadius) {
        ColoringAttributes cA = new ColoringAttributes();
        cA.setColor(aColor);
        Appearance app = new Appearance();
        app.setColoringAttributes(cA);
        app.setCapability(NODE_ALLOW_AUTO_COMPUTE_BOUNDS_WRITE);
        app.setCapability(NODE_ALLOW_LOCAL_TO_VWORLD_READ);
        TransparencyAttributes tA = new TransparencyAttributes(2, 0.5F, 2, 1);
        app.setPolygonAttributes(new PolygonAttributes(2, 0, 0.0F));
        app.setTransparencyAttributes(tA);
        Sphere sphere = new Sphere((float) aRadius, 100, 100);
        sphere.setAppearance(app);
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(aPoint3f));
        TransformGroup sphereTG = new TransformGroup(transform);
        sphereTG.addChild(sphere);
        return sphereTG;
    }

    protected void displaySpheres(Collection<Vertex> selected, float aRadius,
                                  Color3f aColor, BranchGroup aBranchGroup) {
        BranchGroup pointsBG = new BranchGroup();
        for (Vertex v : selected) {
            pointsBG.addChild(createSphereAround(v.asPoint3f(), aColor,
                                                 aRadius));
        }

        aBranchGroup.addChild(pointsBG);
    }

    protected Appearance getCapabilities() {
        Appearance anAppearance = new Appearance();
        anAppearance.setCapability(TRANSFORM_GROUP_ALLOW_TRANSFORM_READ);
        anAppearance.setCapability(TRANSFORM_GROUP_ALLOW_TRANSFORM_WRITE);
        anAppearance.setCapability(NODE_ENABLE_PICK_REPORTING);
        anAppearance.setCapability(GROUP_ALLOW_CHILDREN_READ);
        anAppearance.setCapability(GROUP_ALLOW_CHILDREN_WRITE);
        anAppearance.setCapability(GROUP_ALLOW_CHILDREN_EXTEND);
        anAppearance.setCapability(NODE_ALLOW_LOCAL_TO_VWORLD_READ);
        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(0);
        anAppearance.setPolygonAttributes(new PolygonAttributes(1, 0, 0.0F));
        return anAppearance;
    }

    protected Appearance getCapabilities(TransparencyAttributes transparency) {
        Appearance anAppearance = getCapabilities();
        anAppearance.setTransparencyAttributes(transparency);
        return anAppearance;
    }

    protected boolean isAuxillary(Point3f[] face) {
        return false;
    }

    protected Shape3D newFace(Point3f points[], boolean triangle,
                              Color3f aColor) {
        Color3f[] color = null;
        if (triangle) {
            color = new Color3f[(points.length - 2) * 3];
        } else {
            color = new Color3f[2 * points.length];
        }

        if (aColor != null) {
            for (int i = 0; i < color.length; i++) {
                color[i] = aColor;
            }

        } else {
            for (int i = 0; i < color.length; i++) {
                color[i] = DEFAULT_COLOR;
            }

        }
        Shape3D face = new Shape3D();
        if (triangle) {
            TriangleArray faceTriangleArray = new TriangleArray((points.length
                                                                 - 2)
                                                                * 3, 5);
            Point3f tmpPoint[] = new Point3f[(points.length - 2) * 3];
            for (int i = 0; i < points.length - 2; i++) {
                tmpPoint[i * 3 + 0] = points[0];
                tmpPoint[i * 3 + 1] = points[i + 1];
                tmpPoint[i * 3 + 2] = points[i + 2];
            }

            faceTriangleArray.setCoordinates(0, tmpPoint);
            faceTriangleArray.setColors(0, color);
            face.setGeometry(faceTriangleArray);
        } else {
            LineArray faceLineArray = new LineArray(points.length * 2, 5);
            Point3f tmpPoint[] = new Point3f[2 * points.length];
            tmpPoint[0] = points[0];
            for (int i = 1; i < points.length; i++) {
                tmpPoint[2 * i - 1] = points[i];
                tmpPoint[2 * i] = points[i];
            }

            tmpPoint[2 * points.length - 1] = points[0];
            faceLineArray.setCoordinates(0, tmpPoint);
            faceLineArray.setColors(0, color);
            face.setGeometry(faceLineArray);
        }
        return face;
    }

    protected void render(List<Point3f[]> region, Color3f color,
                          BranchGroup group, boolean showFaces,
                          Appearance appearance) {
        appearance.setPolygonAttributes(new PolygonAttributes(2, 0, 0.0F));
        for (Point3f[] face : region) {
            if (isAuxillary(face)) {
                continue;
            }
            if (showFaces) {
                Shape3D triangle = newFace(face, true, color);
                group.addChild(triangle);
                triangle.setAppearance(appearance);
            }
            Shape3D lines = newFace(face, false, color);
            group.addChild(lines);
        }
    }

    protected void setCapabilities(TransformGroup aTG) {
        aTG.setCapability(TRANSFORM_GROUP_ALLOW_TRANSFORM_READ);
        aTG.setCapability(TRANSFORM_GROUP_ALLOW_TRANSFORM_WRITE);
        aTG.setCapability(NODE_ENABLE_PICK_REPORTING);
        aTG.setCapability(GROUP_ALLOW_CHILDREN_READ);
        aTG.setCapability(GROUP_ALLOW_CHILDREN_WRITE);
        aTG.setCapability(GROUP_ALLOW_CHILDREN_EXTEND);
        aTG.setCapability(NODE_ALLOW_LOCAL_TO_VWORLD_READ);
    }

}