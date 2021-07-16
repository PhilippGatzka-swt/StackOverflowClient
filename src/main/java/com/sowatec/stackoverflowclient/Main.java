package main.java.com.sowatec.stackoverflowclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("../../../../resources/fxml/application.fxml"));
        primaryStage.setTitle("StackOverflowClient");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("main/resources/css/dark.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
