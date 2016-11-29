package com.script972;
import com.script972.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by script972 on 13.11.2016.
 */

    public class Main extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            String fxmlFile = "/fxml/form.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
            stage.setTitle("encrypt pleffera");

            stage.setScene(new Scene(root));
            stage.show();
        }

    public static void main(String[] args) throws Exception {
            launch(args);

        }
    }
