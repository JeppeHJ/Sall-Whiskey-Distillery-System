package gui.application;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    private final Controller controller = Controller.getController();


    public void start(Stage primaryStage) {
        AppController appController;
        controller.createSomeObjects();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/application/App.fxml"));
            AnchorPane root = fxmlLoader.load();
            appController = fxmlLoader.getController();
            appController.initialize();
            Scene scene = new Scene(root, 1023, 534);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lagerstyring");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}