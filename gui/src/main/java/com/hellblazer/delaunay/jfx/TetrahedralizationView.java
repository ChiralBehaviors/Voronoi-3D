/**
 * Copyright (C) 2009 Hal Hildebrand. All rights reserved.
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

package com.hellblazer.delaunay.jfx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransparencyAttributes;

import com.hellblazer.delaunay.Tetrahedralization;
import com.hellblazer.delaunay.Tetrahedron;
import com.hellblazer.delaunay.Vertex;
import com.hellblazer.utils.collections.IdentitySet;

import javafx.geometry.Point3D;

/**
 *
 * @author <a href="mailto:hal.hildebrand@gmail.com">Hal Hildebrand</a>
 *
 */

public class TetrahedralizationView extends GraphicsView {
    private List<Point3D[]>        delaunayFaces = new ArrayList<>();
    private Set<Point3D>           fourCorners   = new HashSet<>();
    @SuppressWarnings("unused")
    private BranchGroup            highlightedRegions;
    @SuppressWarnings("unused")
    private TransparencyAttributes highlightTransparency;
    private Tetrahedralization     tetrahedralization;
    private Set<Tetrahedron>       tetrahedrons  = new IdentitySet<>(100);
    private final Set<Vertex>      vertices      = new IdentitySet<>(100);
    private List<Point3D[]>        voronoiFaces  = new ArrayList<>();
    final TransparencyAttributes   diagramTransparency;

    public TetrahedralizationView() {
        this(new Tetrahedralization());
    }

    public TetrahedralizationView(Tetrahedralization t) {
        tetrahedralization = t;
        for (Vertex v : tetrahedralization.getUniverse()) {
            fourCorners.add(v.asPoint3D());
        }
        highlightTransparency = new TransparencyAttributes(2,
                                                           (float) (Math.log(50)
                                                                    / Math.log(200D)),
                                                           2, 1);
        diagramTransparency = new TransparencyAttributes(2,
                                                         (float) (Math.log(180)
                                                                  / Math.log(200D)),
                                                         2, 1);
        diagramTransparency.setCapability(3);
        updateDiagram();
    }

    public Tetrahedralization getTetrahedralization() {
        return tetrahedralization;
    }

    public void highlightRegions(boolean highlight, List<Vertex> vertices) {
        //        assert vertices != null;
        //
        //        if (diagram != null) {
        //            if (highlightedRegions != null) {
        //                for (int i = 0; i < diagram.numChildren(); i++) {
        //                    if (diagram != null && diagram.getChild(i)
        //                                                  .equals(highlightedRegions)) {
        //                        diagram.removeChild(i);
        //                    }
        //                }
        //
        //            }
        //        }
        //        highlightedRegions = new BranchGroup();
        //        highlightedRegions.setCapability(GROUP_ALLOW_CHILDREN_EXTEND);
        //        highlightedRegions.setCapability(GROUP_ALLOW_CHILDREN_READ);
        //        highlightedRegions.setCapability(BRANCH_GROUP_ALLOW_DETACH);
        //        Appearance appearance = getCapabilities(highlightTransparency);
        //        if (highlight) {
        //            for (Vertex v : vertices) {
        //                render(tetrahedralization.getVoronoiRegion(v),
        //                       COLOR_OF_HIGHLIGHTED_REGION, highlightedRegions, true,
        //                       appearance);
        //            }
        //            displaySpheres(vertices, 0.03F, COLOR_OF_HIGHLIGHTED_REGION,
        //                           highlightedRegions);
        //        }
        //        diagram.addChild(highlightedRegions);
        //        doLayout();
    }

    public void setTransparency(int aValue) {
        float transparency = 0.0F;
        if (aValue <= 0) {
            transparency = 0.0F;
        } else {
            transparency = (float) (Math.log(aValue) / Math.log(200D));
        }
        diagramTransparency.setTransparency(transparency);
    }

    public void update(boolean showVD, boolean showDT, boolean showFaces,
                       boolean showAllPoints) {
        updateDiagram();
        if (showVD) {
            render(voronoiFaces, redMaterial, showFaces);
        }
        if (showDT) {
            render(delaunayFaces, redMaterial, showFaces);
        }
        if (showAllPoints) {
            displaySpheres(vertices.stream()
                                   .map(v -> v.asPoint3D())
                                   .collect(Collectors.toList()),
                           0.01F, redMaterial);
        }
        super.requestLayout();
    }

    public void updateDiagram() {
        tetrahedrons.clear();
        vertices.clear();
        tetrahedralization.traverse(tetrahedrons, vertices);
        voronoiFaces.clear();
        delaunayFaces.clear();
        getChildren().clear();

        for (Tetrahedron t : tetrahedrons) {
            t.addFacesCoordinates(delaunayFaces);
        }
        for (Vertex v : vertices) {
            for (Point3D[] face : tetrahedralization.getVoronoiRegion(v)) {
                voronoiFaces.add(face);
            }
        }
    }

    @Override
    protected boolean isAuxillary(Point3D[] face) {
        if (face.length < 3) {
            return true;
        }
        for (Point3D v : face) {
            if (fourCorners.contains(v)) {
                return true;
            }
        }
        return false;
    }
}
