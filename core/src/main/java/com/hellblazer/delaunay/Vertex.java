/**
 * Copyright (C) 2009 Hal Hildebrand. All rights reserved.
 *
 * This file is part of the 3D Incremental Voronoi system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hellblazer.delaunay;

import java.util.Random;

import com.hellblazer.utils.math.Geometry;

import javafx.geometry.Point3D;

/**
 *
 * @author <a href="mailto:hal.hildebrand@gmail.com">Hal Hildebrand</a>
 *
 */
public class Vertex {

    /**
     * Minimal zero
     */
    static final double EPSILON = Math.pow(10D, -20D);
    static final Vertex ORIGIN  = new Vertex(0, 0, 0);

    /**
     * Create some random points in a sphere
     *
     * @param random
     * @param numberOfPoints
     * @param radius
     * @param inSphere
     * @return
     */
    public static Vertex[] getRandomPoints(Random random, int numberOfPoints,
                                           double radius, boolean inSphere) {
        double radiusSquared = radius * radius;
        Vertex ourPoints[] = new Vertex[numberOfPoints];
        for (int i = 0; i < ourPoints.length; i++) {
            if (inSphere) {
                do {
                    ourPoints[i] = randomPoint(random, -radius, radius);
                } while (ourPoints[i].distanceSquared(ORIGIN) >= radiusSquared);
            } else {
                ourPoints[i] = randomPoint(random, -radius, radius);
            }
        }

        return ourPoints;
    }

    /**
     * Generate a bounded random double
     *
     * @param random
     * @param min
     * @param max
     * @return
     */
    public static double random(Random random, double min, double max) {
        double result = random.nextDouble();
        if (min > max) {
            result *= min - max;
            result += max;
        } else {
            result *= max - min;
            result += min;
        }
        return result;
    }

    /**
     * Generate a random point
     *
     * @param random
     * @param min
     * @param max
     * @return
     */
    public static Vertex randomPoint(Random random, double min, double max) {
        return new Vertex(random(random, min, max), random(random, min, max),
                          random(random, min, max));
    }

    public final double x;

    public final double y;

    public final double z;

    /**
     * One of the tetrahedra adjacent to the vertex
     */
    private Tetrahedron adjacent;

    /**
     * The number of tetrahedra adjacent to the vertex
     */
    private int         order = 0;

    public Vertex(double i, double j, double k) {
        x = i;
        y = j;
        z = k;
    }

    public Vertex(double i, double j, double k, double scale) {
        this(i * scale, j * scale, k * scale);
    }

    /**
     * Account for the deletion of an adjacent tetrahedron.
     */
    public final void deleteAdjacent() {
        order--;
        assert order >= 0;
    }

    public final double distanceSquared(Vertex p1) {
        double dx, dy, dz;

        dx = x - p1.x;
        dy = y - p1.y;
        dz = z - p1.z;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Answer one of the adjacent tetrahedron
     *
     * @return
     */
    public final Tetrahedron getAdjacent() {
        return adjacent;
    }

    /**
     * Answer the number of tetrahedra adjacent to the receiver vertex in the
     * tetrahedralization
     * <p>
     *
     * @return
     */
    public final int getOrder() {
        return order;
    }

    /**
     * Return +1 if the receiver lies inside the sphere passing through a, b, c,
     * and d; -1 if it lies outside; and 0 if the five points are cospherical.
     * The vertices a, b, c, and d must be ordered so that they have a positive
     * orientation (as defined by {@link #orientation(Vertex, Vertex, Vertex)}),
     * or the sign of the result will be reversed.
     * <p>
     *
     * @param a
     *            , b, c, d - the points defining the sphere, in oriented order
     * @return +1 if the receiver lies inside the sphere passing through a, b,
     *         c, and d; -1 if it lies outside; and 0 if the five points are
     *         cospherical
     */

    public final int inSphere(Vertex a, Vertex b, Vertex c, Vertex d) {
        double result = Geometry.inSphere(a.x, a.y, a.z, b.x, b.y, b.z, c.x,
                                          c.y, c.z, d.x, d.y, d.z, x, y, z);
        if (result > 0.0) {
            return 1;
        } else if (result < 0.0) {
            return -1;
        }
        return 0;

    }

    /**
     * Answer +1 if the orientation of the receiver is positive with respect to
     * the plane defined by {a, b, c}, -1 if negative, or 0 if the test point is
     * coplanar
     * <p>
     *
     * @param a
     *            , b, c - the points defining the plane
     * @return +1 if the orientation of the query point is positive with respect
     *         to the plane, -1 if negative and 0 if the test point is coplanar
     */
    public final int orientation(Vertex a, Vertex b, Vertex c) {
        double result = Geometry.leftOfPlane(a.x, a.y, a.z, b.x, b.y, b.z, c.x,
                                             c.y, c.z, x, y, z);
        if (result > 0.0) {
            return 1;
        } else if (result < 0.0) {
            return -1;
        }
        return 0;
    }

    /**
     * Reset the state associated with a tetrahedralization.
     */
    public final void reset() {
        adjacent = null;
        order = 0;
    }

    /**
     * Note one of the adjacent tetrahedron
     * <p>
     *
     * @param tetrahedron
     */
    public final void setAdjacent(Tetrahedron tetrahedron) {
        order++;
        adjacent = tetrahedron;
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + ", " + z + "}";
    }

    public Point3D asPoint3D() {
        return new Point3D(x, y, z);
    }
}