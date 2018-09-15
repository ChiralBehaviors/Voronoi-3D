package com.hellblazer.delaunay.jfx;

import static javafx.scene.paint.Color.DARKRED;
import static javafx.scene.paint.Color.RED;

import java.util.Collection;
import java.util.List;

import com.javafx.experiments.shape3d.PolygonMesh;
import com.javafx.experiments.shape3d.PolygonMeshView;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class GraphicsView extends Group {

    private static final int[]           faces     = { 0, 0, 2, 2, 1, 1,                                             // iv0, it0, iv2, it2, iv1, it1 (front face)
                                                       0, 0, 1, 1, 2, 2                                              // iv0, it0, iv1, it1, iv2, it2 back face
    };
    protected static final PhongMaterial redMaterial;

    private static final float[]         texCoords = { 0.5f, 0.5f,                                                   // t0 (it0 = 0)
                                                       0.0f, 1.0f,                                                   // t1 (it1 = 1)
                                                       1.0f, 1.0f                                                    // t2 (it2 = 2)
    };

    static {
        redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(new Color(DARKRED.getRed(),
                                              DARKRED.getGreen(),
                                              DARKRED.getBlue(), 0.6));
        redMaterial.setSpecularColor(new Color(RED.getRed(), RED.getGreen(),
                                               RED.getBlue(), 0.6));
    }

    protected void createSphereAround(Point3D vertex, Material material,
                                      double aRadius) {
        Sphere sphere = new Sphere();
        sphere.setRadius(aRadius);
        sphere.setTranslateX(vertex.getX());
        sphere.setTranslateY(vertex.getY());
        sphere.setTranslateZ(vertex.getZ());
        sphere.setMaterial(material);
        getChildren().add(sphere);
    }

    protected void displaySpheres(Collection<Point3D> selected, float aRadius,
                                  PhongMaterial material) {
        for (Point3D v : selected) {
            createSphereAround(v, material, aRadius);
        }

    }

    protected boolean isAuxillary(Point3D[] face) {
        return false;
    }

    protected void displayFace(Point3D vertices[], boolean triangle,
                               Material material) {
        if (triangle) {
            face(vertices, material);
        }
        edge(vertices, material);
    }

    protected void render(List<Point3D[]> region, Material color,
                          boolean showFaces) {
        for (Point3D[] face : region) {
            if (isAuxillary(face)) {
                continue;
            }
            displayFace(face, showFaces, color);
        }
    }

    private Cylinder createEdge(Point3D origin, Point3D target) {
        Point3D diff = target.subtract(origin);

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(),
                                                 mid.getZ());

        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize()
                                     .dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle),
                                               axisOfRotation);

        Cylinder line = new Cylinder(0.001, diff.magnitude());

        line.getTransforms()
            .addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    private void edge(Point3D[] vertices, Material material) {
        for (int i = 0; i < vertices.length; i++) {
            Cylinder edge = createEdge(vertices[i],
                                       (vertices[(i + 1) % vertices.length]));
            edge.setMaterial(material);
            getChildren().add(edge);
        }
    }

    private void face(Point3D[] vertices, Material material) {
        float[] points = new float[vertices.length * 3];
        float[] textCoords = new float[] { 0.0f, 1.0f };
        int i = 0;
        for (Point3D v : vertices) {
            points[i] = (float) v.getX();
            points[i++] = (float) v.getY();
            points[i++] = (float) v.getZ();
            i++;
        }
        int[][] faces = new int[1][];
        faces[0] = new int[vertices.length * 4];
        int index = 0;
        for (i = 0; i < vertices.length; i++) {
            faces[0][index++] = i;
            faces[0][index++] = 1;
        }
        PolygonMesh mesh = new PolygonMesh(points, textCoords, faces);
        mesh.getPoints()
            .addAll(points);
        mesh.getTexCoords()
            .addAll(textCoords);
        mesh.getFaceSmoothingGroups()
            .addAll(1);
        PolygonMeshView meshView = new PolygonMeshView(mesh);
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setCullFace(CullFace.NONE);
        meshView.setMaterial(material);
        getChildren().add(meshView);
    }
}