package com.hellblazer.delaunay.jfx;

import java.util.Random;

import com.hellblazer.delaunay.Examples;
import com.hellblazer.delaunay.Tetrahedralization;
import com.hellblazer.delaunay.Vertex;

/**
 *
 * @author <a href="mailto:hal.hildebrand@gmail.com">Hal Hildebrand</a>
 *
 */

public class Inspector extends Jfx3dViewerApp {
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public ContentModel createContentModel() {
        ContentModel model = super.createContentModel();

        final Tetrahedralization tet = new Tetrahedralization(new Random(666));
        for (Vertex v : Examples.getGrid()) {
            tet.insert(v);
        }

        TetrahedralizationView content = new TetrahedralizationView(tet);
        content.update(false, true, true, true);
        model.setContent(content);
        return model;
    }

}
