/*
 * Copyright (c) 2010, 2015, Oracle and/or its affiliates.
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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * JavaFX 3D Viewer Application
 */
public class SimpleViewerApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private AutoScalingGroup        autoScalingGroup  = new AutoScalingGroup(2);
    private final PerspectiveCamera camera            = new PerspectiveCamera(true);
    private final Rotate            cameraLookXRotate = new Rotate(0, 0, 0, 0,
                                                                   Rotate.X_AXIS);
    private final Rotate            cameraLookZRotate = new Rotate(0, 0, 0, 0,
                                                                   Rotate.Z_AXIS);
    private final Translate         cameraPosition    = new Translate(0, 0, -7);
    private final Rotate            cameraXRotate     = new Rotate(-20, 0, 0, 0,
                                                                   Rotate.X_AXIS);
    private final Rotate            cameraYRotate     = new Rotate(-20, 0, 0, 0,
                                                                   Rotate.Y_AXIS);

    private final Group             root3D            = new Group();

    @Override
    public void start(Stage stage) throws Exception {
        final Scene scene = new Scene(root3D, 1920, 1080, true);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        // CAMERA
        camera.getTransforms()
              .addAll(cameraXRotate, cameraYRotate, cameraPosition,
                      cameraLookXRotate, cameraLookZRotate);
        camera.setNearClip(0.1);
        camera.setFarClip(100);
        scene.setCamera(camera);
        root3D.getChildren()
              .addAll(camera, autoScalingGroup);

//        Node content = null;
//        autoScalingGroup.getChildren()
//                        .add(content);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                                                      new KeyValue(cameraYRotate.angleProperty(),
                                                                   0)),
                                         new KeyFrame(Duration.seconds(4),
                                                      new KeyValue(cameraYRotate.angleProperty(),
                                                                   360)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        stage.setScene(scene);
        stage.show();

        // MEASURE FPS
        //TODO: RT-40270 - Public PerformanceTracker support should be added
        //        Timeline fpsTimeline = new Timeline(new KeyFrame(Duration.seconds(2), t ->
        //                System.out.println("fps = " + PerformanceTracker.getSceneTracker(scene).getInstantFPS())));
        //        fpsTimeline.setCycleCount(Timeline.INDEFINITE);
        //        fpsTimeline.play();
    }
}
