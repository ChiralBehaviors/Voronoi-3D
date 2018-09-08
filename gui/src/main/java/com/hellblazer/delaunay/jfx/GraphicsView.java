package com.hellblazer.delaunay.jfx;

import static javafx.scene.paint.Color.DARKRED;
import static javafx.scene.paint.Color.RED;

import java.util.Collection;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
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

    protected void newFace(Point3D vertices[], boolean triangle,
                           Material material) {
        if (triangle) {
            triangleFace(vertices, material);
        } else {
            triangleEdge(vertices, material);
        }
    }

    protected void render(List<Point3D[]> region, Material color,
                          boolean showFaces) {
        for (Point3D[] face : region) {
            if (isAuxillary(face)) {
                continue;
            }
            newFace(face, showFaces, color);
        }
    }

    private Cylinder createEdge(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(),
                                                 mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize()
                                     .dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle),
                                               axisOfRotation);

        Cylinder line = new Cylinder(1, height);

        line.getTransforms()
            .addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    private void triangleEdge(Point3D[] vertices, Material material) {
        for (int i = 0; i < vertices.length; i++) {
            Cylinder edge = createEdge(vertices[i], (vertices[(i + 1) % i]));
            edge.setMaterial(material);
            getChildren().add(edge);
        }
    }

    private void triangleFace(Point3D[] vertices, Material material) {
        float[] points = new float[vertices.length * 3];
        int i = 0;
        for (Point3D v : vertices) {
            points[i++] = (float) v.getX();
            points[i++] = (float) v.getY();
            points[i++] = (float) v.getZ();
        }
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints()
            .addAll(points);
        mesh.getTexCoords()
            .addAll(texCoords);
        mesh.getFaces()
            .addAll(faces);
        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(material);
        getChildren().add(meshView);
    }
}