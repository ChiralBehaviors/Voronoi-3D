/*
 * Copyright (c) 2010, 2014, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.hellblazer.delaunay.jfx;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.util.Duration;

/**
 * Controller class for main fxml file.
 */
public class MainController {
    public SplitMenuButton     openMenuBtn;
    public Label               status;
    public SplitPane           splitPane;
    public ToggleButton        settingsBtn;
    public CheckMenuItem       loadAsPolygonsCheckBox;
    public CheckMenuItem       optimizeCheckBox;
    public Button              startBtn;
    public Button              rwBtn;
    public ToggleButton        playBtn;
    public Button              ffBtn;
    public Button              endBtn;
    public ToggleButton        loopBtn;
    public TimelineDisplay     timelineDisplay;
    private Accordion          settingsPanel;
    private double             settingsLastWidth = -1;
    private int                nodeCount         = 0;
    private int                meshCount         = 0;
    private int                triangleCount     = 0;
    private ContentModel       contentModel;
    private TimelineController timelineController;
    private SessionManager     sessionManager    = SessionManager.getSessionManager();

    private void updateStatus() {
        nodeCount = 0;
        meshCount = 0;
        triangleCount = 0;
        updateCount(contentModel.getRoot3D());
        Node content = contentModel.getContent();
        final Bounds bounds = content == null ? new BoundingBox(0, 0, 0, 0)
                                              : content.getBoundsInLocal();
        status.setText(String.format("Nodes [%d] :: Meshes [%d] :: Triangles [%d] :: "
                                     + "Bounds [w=%.2f,h=%.2f,d=%.2f]",
                                     nodeCount, meshCount, triangleCount,
                                     bounds.getWidth(), bounds.getHeight(),
                                     bounds.getDepth()));
    }

    private void updateCount(Node node) {
        nodeCount++;
        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                updateCount(child);
            }
        } else if (node instanceof Box) {
            meshCount++;
            triangleCount += 6 * 2;
        } else if (node instanceof MeshView) {
            TriangleMesh mesh = (TriangleMesh) ((MeshView) node).getMesh();
            if (mesh != null) {
                meshCount++;
                triangleCount += mesh.getFaces()
                                     .size()
                                 / mesh.getFaceElementSize();
            }
        }
    }

    public void open(ActionEvent actionEvent) {
    }

    public void export(ActionEvent event) {
    }

    public void toggleSettings(ActionEvent event) {
        final SplitPane.Divider divider = splitPane.getDividers()
                                                   .get(0);
        if (settingsBtn.isSelected()) {
            if (settingsLastWidth == -1) {
                settingsLastWidth = settingsPanel.prefWidth(-1);
            }
            final double divPos = 1
                                  - (settingsLastWidth / splitPane.getWidth());
            new Timeline(new KeyFrame(Duration.seconds(0.3),
                                      event1 -> settingsPanel.setMinWidth(Region.USE_PREF_SIZE),
                                      new KeyValue(divider.positionProperty(),
                                                   divPos,
                                                   Interpolator.EASE_BOTH))).play();
        } else {
            settingsLastWidth = settingsPanel.getWidth();
            settingsPanel.setMinWidth(0);
            new Timeline(new KeyFrame(Duration.seconds(0.3),
                                      new KeyValue(divider.positionProperty(),
                                                   1))).play();
        }
    }

    public void setContentModel(ContentModel contentModel) {
        this.contentModel = contentModel;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("navigation.fxml"));
            // CREATE NAVIGATOR CONTROLS
            Parent navigationPanel = loader.load();
            NavigationController nav = loader.<NavigationController> getController();
            nav.setContentModel(contentModel);
            
            // CREATE SETTINGS PANEL
            loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            settingsPanel = loader.load();
            SettingsController settings = loader.<SettingsController> getController();
            settings.setContentModel(contentModel);
            
            // SETUP SPLIT PANE
            splitPane.getItems()
                     .addAll(new SubSceneResizer(contentModel.subSceneProperty(),
                                                 navigationPanel),
                             settingsPanel);
            splitPane.getDividers()
                     .get(0)
                     .setPosition(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create timelineController;
        timelineController = new TimelineController(startBtn, rwBtn, playBtn,
                                                    ffBtn, endBtn, loopBtn);
        timelineController.timelineProperty()
                          .bind(contentModel.timelineProperty());
        timelineDisplay.timelineProperty()
                       .bind(contentModel.timelineProperty());
        sessionManager.bind(settingsBtn.selectedProperty(), "settingsBtn");
        sessionManager.bind(splitPane.getDividers()
                                     .get(0)
                                     .positionProperty(),
                            "settingsSplitPanePosition");
        sessionManager.bind(optimizeCheckBox.selectedProperty(), "optimize");
        sessionManager.bind(loadAsPolygonsCheckBox.selectedProperty(),
                            "loadAsPolygons");
        sessionManager.bind(loopBtn.selectedProperty(), "loop");

        String url = sessionManager.getProperties()
                                   .getProperty(Jfx3dViewerApp.FILE_URL_PROPERTY);
        if (url == null) {
            url = ContentModel.class.getResource("drop-here-large-yUp.obj")
                                    .toExternalForm();
        }
        // load(url);

        // do initial status update
        updateStatus();
    }
}
