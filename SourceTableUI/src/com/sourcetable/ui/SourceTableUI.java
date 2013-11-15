/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sourcetable.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.sourcetable.ui.common.UIConstants.*;

/**
 *
 * @author Tai Hu
 */
public class SourceTableUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        SourceTableMainWindow mainWindow = new SourceTableMainWindow();        
        
        // Set up stage
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        
        Scene scene = new Scene(mainWindow, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        scene.getStylesheets().add(SourceTableUI.class.getResource("SourceTableUI.css").toExternalForm());
                
        primaryStage.setTitle("Source Table");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.err.println("Warning: This main method should not be used. Check JavaFX configuration.");
        launch(args);
    }
}
