/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import com.vero.ui.common.UIManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static com.vero.ui.constants.UIConstants.*;

//import javafx.stage.StageStyle;

/**
 * 
 * @author Tai Hu
 */
public class VeroUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        UIManager.getInstance().setPrimaryStage(primaryStage);
        Pane rootPane = UIManager.getInstance().getVeroRootPane();

        VeroMainWindow mainWindow = new VeroMainWindow();
        rootPane.getChildren().add(mainWindow);

        // Set up stage
        // primaryStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(rootPane, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        scene.getStylesheets().add(VeroUI.class.getResource("VeroUI.css").toExternalForm());

        primaryStage.setTitle("Vero Analytic");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        System.err.println("Warning: This main method should not be used. Check JavaFX configuration.");
        launch(args);
    }
}
