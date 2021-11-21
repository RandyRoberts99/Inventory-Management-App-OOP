/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Randall Roberts
 */

package inventoryapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryManagementApplication extends Application
{
    // Starts main stage/scene of the application
    @Override
    public void start(Stage stage) throws IOException
    {
        Scene mainScene = new Scene(FXMLLoader.load(getClass().getResource("MainAppBox.fxml")));

        stage.setResizable(false);
        stage.setTitle("Inventory Management Application");
        stage.setScene(mainScene);
        stage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}