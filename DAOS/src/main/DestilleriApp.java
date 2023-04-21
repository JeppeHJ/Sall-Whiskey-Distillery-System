package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DestilleriApp extends Application {

    private static final String DB_URL = "jdbc:sqlserver://localhost\\LUKASSERVER;databaseName=Sall;encrypt=false;";
    private static final String USER = "sa";
    private static final String PASSWORD = "1234";

    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DestilleriApp.fxml"));
            AnchorPane root = fxmlLoader.load();

            Controller controller = fxmlLoader.getController();
            controller.setConnection(connection);
            controller.initData();

            Scene scene = new Scene(root, 782, 694);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lagerstyring");
            primaryStage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
