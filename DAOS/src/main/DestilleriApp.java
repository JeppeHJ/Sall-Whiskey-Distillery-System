package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DestilleriApp extends Application {

    private static final String DB_URL = "jdbc:sqlserver://localhost\\MSSQLSERVER;databaseName=Sall";
    private static final String USER = "sa";
    private static final String PASSWORD = "1234";


    public void start(Stage primaryStage) {
        Controller controller = new Controller();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            controller.setConnection(connection);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main/DestilleriApp.fxml"));
            AnchorPane root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.initialize();
            Scene scene = new Scene(root, 1023, 534);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lagerstyring");
            primaryStage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
