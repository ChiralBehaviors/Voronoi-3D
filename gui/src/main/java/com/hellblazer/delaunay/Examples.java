/**
 * Copyright (C) 2008 Hal Hildebrand. All rights reserved.
 *
 * This file is part of the 3D Incremental Voronoi GUI
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

import java.util.ArrayList;

/**
 *
 * @author <a href="mailto:hal.hildebrand@gmail.com">Hal Hildebrand</a>
 *
 */

public class Examples {

    public static Vertex[] getCubicCrystalStructure() {
        ArrayList<Vertex> list = new ArrayList<Vertex>();
        list.add(new Vertex(-0.89999799999999996D, -0.89999200000000001D,
                            -0.900003D));
        list.add(new Vertex(-0.90000199999999997D, -0.90000800000000003D,
                            -6.9999999999999999E-06D));
        list.add(new Vertex(-0.89999499999999999D, -0.90000199999999997D,
                            0.900003D));
        list.add(new Vertex(-0.89999099999999999D, 5.0000000000000004E-06D,
                            -0.89999099999999999D));
        list.add(new Vertex(-0.89999399999999996D, 9.9999999999999995E-07D,
                            5.0000000000000004E-06D));
        list.add(new Vertex(-0.90000800000000003D, 5.0000000000000004E-06D,
                            0.90000500000000005D));
        list.add(new Vertex(-0.89999499999999999D, 0.89999099999999999D,
                            -0.89999600000000002D));
        list.add(new Vertex(-0.90000100000000005D, 0.90000000000000002D,
                            -1.9999999999999999E-06D));
        list.add(new Vertex(-0.89999499999999999D, 0.90000199999999997D,
                            0.89999899999999999D));
        list.add(new Vertex(-6.0000000000000002E-06D, -0.89999600000000002D,
                            -0.90000599999999997D));
        list.add(new Vertex(-6.0000000000000002E-06D, -0.900007D,
                            -6.0000000000000002E-06D));
        list.add(new Vertex(-3.0000000000000001E-06D, -0.90000100000000005D,
                            0.89999600000000002D));
        list.add(new Vertex(3.9999999999999998E-06D, 0.0D,
                            -0.89999399999999996D));
        list.add(new Vertex(-9.0000000000000002E-06D, 5.0000000000000004E-06D,
                            1.0000000000000001E-05D));
        list.add(new Vertex(-6.0000000000000002E-06D, -1.9999999999999999E-06D,
                            0.89999799999999996D));
        list.add(new Vertex(9.0000000000000002E-06D, 0.89999600000000002D,
                            -0.90000599999999997D));
        list.add(new Vertex(1.0000000000000001E-05D, 0.90000100000000005D,
                            3.9999999999999998E-06D));
        list.add(new Vertex(-6.9999999999999999E-06D, 0.90000899999999995D,
                            0.89999600000000002D));
        list.add(new Vertex(0.89999799999999996D, -0.90000800000000003D,
                            -0.89999600000000002D));
        list.add(new Vertex(0.90000500000000005D, -0.89999899999999999D,
                            -1.9999999999999999E-06D));
        list.add(new Vertex(0.89999600000000002D, -0.89999099999999999D,
                            0.90000199999999997D));
        list.add(new Vertex(0.89999499999999999D, 9.9999999999999995E-07D,
                            -0.89999700000000005D));
        list.add(new Vertex(0.90000500000000005D, -5.0000000000000004E-06D,
                            -3.9999999999999998E-06D));
        list.add(new Vertex(0.89999799999999996D, -3.9999999999999998E-06D,
                            0.89999899999999999D));
        list.add(new Vertex(0.90000599999999997D, 0.900003D,
                            -0.89999600000000002D));
        list.add(new Vertex(0.89999600000000002D, 0.89999899999999999D,
                            -6.9999999999999999E-06D));
        list.add(new Vertex(0.90000400000000003D, 0.89999499999999999D,
                            0.900003D));
        list.add(new Vertex(-0.44999499999999998D, -0.44999099999999997D,
                            -0.45001000000000002D));
        list.add(new Vertex(-0.45000200000000001D, -0.45000499999999999D,
                            0.44999499999999998D));
        list.add(new Vertex(-0.449992D, 0.44999099999999997D, -0.449992D));
        list.add(new Vertex(-0.44999499999999998D, 0.45000600000000002D,
                            0.44999099999999997D));
        list.add(new Vertex(0.45000099999999998D, -0.44999600000000001D,
                            -0.44999600000000001D));
        list.add(new Vertex(0.44999800000000001D, -0.45000899999999999D,
                            0.44999299999999998D));
        list.add(new Vertex(0.44999400000000001D, 0.44999499999999998D,
                            -0.45000800000000002D));
        list.add(new Vertex(0.45000800000000002D, 0.44999600000000001D,
                            0.45000699999999999D));
        return list.toArray(new Vertex[list.size()]);
    }

    public static Vertex[] getGrid() {
        ArrayList<Vertex> list;
        list = new ArrayList<Vertex>();
        list.add(new Vertex(-0.74999800000000005D, -0.74999199999999999D,
                            -0.75000299999999998D));
        list.add(new Vertex(-0.75000199999999995D, -0.75000800000000001D,
                            -0.25000699999999998D));
        list.add(new Vertex(-0.74999499999999997D, -0.75000199999999995D,
                            0.25000299999999998D));
        list.add(new Vertex(-0.74999099999999996D, -0.74999499999999997D,
                            0.75000900000000004D));
        list.add(new Vertex(-0.74999400000000005D, -0.249999D,
                            -0.74999499999999997D));
        list.add(new Vertex(-0.75000800000000001D, -0.24999499999999999D,
                            -0.24999499999999999D));
        list.add(new Vertex(-0.74999499999999997D, -0.25000899999999998D,
                            0.250004D));
        list.add(new Vertex(-0.75000100000000003D, -0.25D, 0.74999800000000005D));
        list.add(new Vertex(-0.74999499999999997D, 0.250002D,
                            -0.75000100000000003D));
        list.add(new Vertex(-0.75000599999999995D, 0.250004D,
                            -0.25000600000000001D));
        list.add(new Vertex(-0.75000599999999995D, 0.24999299999999999D,
                            0.24999399999999999D));
        list.add(new Vertex(-0.75000299999999998D, 0.249999D, 0.749996D));
        list.add(new Vertex(-0.749996D, 0.75D, -0.74999400000000005D));
        list.add(new Vertex(-0.75000900000000004D, 0.75000500000000003D,
                            -0.24998999999999999D));
        list.add(new Vertex(-0.75000599999999995D, 0.74999800000000005D,
                            0.249998D));
        list.add(new Vertex(-0.74999099999999996D, 0.749996D,
                            0.74999400000000005D));
        list.add(new Vertex(-0.24998999999999999D, -0.74999899999999997D,
                            -0.749996D));
        list.add(new Vertex(-0.25000699999999998D, -0.74999099999999996D,
                            -0.250004D));
        list.add(new Vertex(-0.250002D, -0.75000800000000001D, 0.250004D));
        list.add(new Vertex(-0.24999499999999999D, -0.74999899999999997D,
                            0.74999800000000005D));
        list.add(new Vertex(-0.250004D, -0.24999099999999999D,
                            -0.74999800000000005D));
        list.add(new Vertex(-0.25000499999999998D, -0.249999D, -0.249997D));
        list.add(new Vertex(-0.24999499999999999D, -0.25000499999999998D,
                            0.249996D));
        list.add(new Vertex(-0.250002D, -0.250004D, 0.74999899999999997D));
        list.add(new Vertex(-0.24999399999999999D, 0.25000299999999998D,
                            -0.749996D));
        list.add(new Vertex(-0.250004D, 0.249999D, -0.25000699999999998D));
        list.add(new Vertex(-0.249996D, 0.24999499999999999D,
                            0.25000299999999998D));
        list.add(new Vertex(-0.24999499999999999D, 0.25000899999999998D,
                            0.74999000000000005D));
        list.add(new Vertex(-0.250002D, 0.74999499999999997D,
                            -0.75000500000000003D));
        list.add(new Vertex(-0.24999199999999999D, 0.74999099999999996D,
                            -0.24999199999999999D));
        list.add(new Vertex(-0.24999499999999999D, 0.75000599999999995D,
                            0.24999099999999999D));
        list.add(new Vertex(-0.249999D, 0.750004D, 0.750004D));
        list.add(new Vertex(0.249998D, -0.75000900000000004D,
                            -0.75000699999999998D));
        list.add(new Vertex(0.24999399999999999D, -0.75000500000000003D,
                            -0.25000800000000001D));
        list.add(new Vertex(0.25000800000000001D, -0.750004D,
                            0.25000699999999998D));
        list.add(new Vertex(0.25000099999999997D, -0.75D, 0.75000800000000001D));
        list.add(new Vertex(0.250002D, -0.25000699999999998D,
                            -0.75000900000000004D));
        list.add(new Vertex(0.249997D, -0.249997D, -0.24999299999999999D));
        list.add(new Vertex(0.249996D, -0.24999499999999999D,
                            0.25001000000000001D));
        list.add(new Vertex(0.25000800000000001D, -0.25000699999999998D,
                            0.75000100000000003D));
        list.add(new Vertex(0.24999499999999999D, 0.249996D, -0.749996D));
        list.add(new Vertex(0.249998D, 0.250004D, -0.24999099999999999D));
        list.add(new Vertex(0.249998D, 0.25000099999999997D,
                            0.25000299999999998D));
        list.add(new Vertex(0.25000099999999997D, 0.24999399999999999D,
                            0.74999300000000002D));
        list.add(new Vertex(0.25001000000000001D, 0.749996D,
                            -0.75000699999999998D));
        list.add(new Vertex(0.250004D, 0.750004D, -0.25000899999999998D));
        list.add(new Vertex(0.25000800000000001D, 0.74999099999999996D,
                            0.249996D));
        list.add(new Vertex(0.24999299999999999D, 0.74999000000000005D,
                            0.75000100000000003D));
        list.add(new Vertex(0.75000299999999998D, -0.74999700000000002D,
                            -0.75000900000000004D));
        list.add(new Vertex(0.74999899999999997D, -0.75000199999999995D,
                            -0.24999399999999999D));
        list.add(new Vertex(0.74999700000000002D, -0.75000599999999995D,
                            0.249997D));
        list.add(new Vertex(0.75000900000000004D, -0.75000800000000001D, 0.75D));
        list.add(new Vertex(0.75000900000000004D, -0.24999499999999999D,
                            -0.74999499999999997D));
        list.add(new Vertex(0.74999800000000005D, -0.25000899999999998D,
                            -0.250002D));
        list.add(new Vertex(0.75D, -0.250004D, 0.25000699999999998D));
        list.add(new Vertex(0.749996D, -0.25D, 0.749996D));
        list.add(new Vertex(0.75000199999999995D, 0.24999199999999999D,
                            -0.75000500000000003D));
        list.add(new Vertex(0.750004D, 0.25000899999999998D, -0.25D));
        list.add(new Vertex(0.75000500000000003D, 0.25001000000000001D,
                            0.250002D));
        list.add(new Vertex(0.74999800000000005D, 0.25000699999999998D,
                            0.75000599999999995D));
        list.add(new Vertex(0.75000599999999995D, 0.75000299999999998D, -0.75D));
        list.add(new Vertex(0.74999199999999999D, 0.74999800000000005D,
                            -0.25000600000000001D));
        list.add(new Vertex(0.74999300000000002D, 0.74999400000000005D,
                            0.24998999999999999D));
        list.add(new Vertex(0.75000299999999998D, 0.75000699999999998D,
                            0.75000199999999995D));
        return list.toArray(new Vertex[list.size()]);
    }

    public static Vertex[] getWorstCase() {
        ArrayList<Vertex> list;
        list = new ArrayList<Vertex>();

        list.add(new Vertex(-0.36000100000000002D, 7.9999999999999996E-06D,
                            0.96000099999999999D));
        list.add(new Vertex(0.120003D, 0.88000900000000004D, 0.120002D));
        list.add(new Vertex(-0.30000100000000002D, 0.0D, 0.900003D));
        list.add(new Vertex(0.100004D, 0.80000899999999997D, 0.100007D));
        list.add(new Vertex(-0.240008D, 7.9999999999999996E-06D,
                            0.84000399999999997D));
        list.add(new Vertex(0.080005999999999994D, 0.72000699999999995D,
                            0.080005000000000007D));
        list.add(new Vertex(-0.180009D, 6.0000000000000002E-06D, 0.780003D));
        list.add(new Vertex(0.060006999999999998D, 0.64000500000000005D,
                            0.060002D));
        list.add(new Vertex(-0.120005D, 3.9999999999999998E-06D,
                            0.72000900000000001D));
        list.add(new Vertex(0.040003999999999998D, 0.56000300000000003D,
                            0.040004999999999999D));
        list.add(new Vertex(-0.060003000000000001D, 1.9999999999999999E-06D,
                            0.66000300000000001D));
        list.add(new Vertex(0.020004000000000001D, 0.48000100000000001D,
                            0.020001999999999999D));
        list.add(new Vertex(6.0000000000000002E-06D, 9.0000000000000002E-06D,
                            0.60000600000000004D));
        list.add(new Vertex(6.0000000000000002E-06D, 0.40000799999999997D,
                            9.0000000000000002E-06D));
        list.add(new Vertex(0.060000999999999999D, 6.9999999999999999E-06D,
                            0.54000800000000004D));
        list.add(new Vertex(-0.020003D, 0.32000600000000001D,
                            -0.020005999999999999D));
        list.add(new Vertex(0.120007D, 5.0000000000000004E-06D,
                            0.48000399999999999D));
        list.add(new Vertex(-0.040003999999999998D, 0.240004D,
                            -0.040003999999999998D));
        list.add(new Vertex(0.180008D, 3.0000000000000001E-06D,
                            0.42000300000000002D));
        list.add(new Vertex(-0.060000999999999999D, 0.16000200000000001D,
                            -0.060002D));
        list.add(new Vertex(0.240007D, 9.9999999999999995E-07D,
                            0.36000700000000002D));
        list.add(new Vertex(-0.080004000000000006D, 0.080004000000000006D,
                            -0.080008999999999997D));
        list.add(new Vertex(0.30000199999999999D, 1.9999999999999999E-06D,
                            0.30000100000000002D));
        list.add(new Vertex(-0.10000299999999999D, 3.0000000000000001E-06D,
                            -0.10000199999999999D));
        list.add(new Vertex(0.36000399999999999D, 6.0000000000000002E-06D,
                            0.240006D));
        list.add(new Vertex(-0.120005D, -0.080004000000000006D, -0.120003D));
        list.add(new Vertex(0.42000599999999999D, 6.9999999999999999E-06D,
                            0.180009D));
        list.add(new Vertex(-0.14000699999999999D, -0.160001D,
                            -0.14000199999999999D));
        list.add(new Vertex(0.48000799999999999D, 3.0000000000000001E-06D,
                            0.120003D));
        list.add(new Vertex(-0.16000900000000001D, -0.240006D,
                            -0.16000600000000001D));
        list.add(new Vertex(0.54000099999999995D, 1.9999999999999999E-06D,
                            0.060000600000000001D));
        list.add(new Vertex(-0.180002D, -0.32000299999999998D,
                            -0.18000099999999999D));
        list.add(new Vertex(0.60000299999999995D, 3.9999999999999998E-06D,
                            5.0000000000000004E-06D));
        list.add(new Vertex(-0.20000399999999999D, -0.40000599999999997D,
                            -0.20000200000000001D));
        list.add(new Vertex(0.66000499999999995D, 6.9999999999999999E-06D,
                            -0.060003000000000001D));
        list.add(new Vertex(-0.22000600000000001D, -0.48000900000000002D,
                            -0.22000600000000001D));
        list.add(new Vertex(0.72000699999999995D, 7.9999999999999996E-06D,
                            -0.120004D));
        list.add(new Vertex(-0.240008D, -0.56000399999999995D,
                            -0.24000299999999999D));
        list.add(new Vertex(0.78000899999999995D, 5.0000000000000004E-06D,
                            -0.180007D));
        list.add(new Vertex(-0.26000200000000001D, -0.64000699999999999D,
                            -0.26000400000000001D));
        list.add(new Vertex(0.840001D, 7.9999999999999996E-06D,
                            -0.24000199999999999D));
        list.add(new Vertex(-0.280003D, -0.720001D, -0.280005D));
        list.add(new Vertex(0.90000500000000005D, 3.0000000000000001E-06D,
                            -0.30000700000000002D));
        list.add(new Vertex(-0.30000300000000002D, -0.80000499999999997D,
                            -0.30000300000000002D));
        list.add(new Vertex(0.96000099999999999D, 1.9999999999999999E-06D,
                            -0.36000700000000002D));
        list.add(new Vertex(-0.32000899999999999D, -0.88000100000000003D,
                            -0.32000299999999998D));
        return list.toArray(new Vertex[list.size()]);
    }
}
