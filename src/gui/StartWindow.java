package gui;

import application.Lager;
import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class StartWindow extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sporbarhed i produktionen og fadlagerstyring");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }



        private void initContent(BorderPane pane) {
            TabPane tabPane = new TabPane();
            this.initTabPane(tabPane);
            pane.setCenter(tabPane);
        }

        private void initTabPane(TabPane tabPane) {
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            Tab tab01 = new Tab("Lagere");
            Tab tab02 = new Tab("Fade");
            Tab tab03 = new Tab("Distillat");
            Tab tab04 = new Tab("Lagret distillat");
            Tab tab05 = new Tab("Færdige Whiskys");

            LagerePane lagerePane = new LagerePane();
            FadePane fadePane = new FadePane();
            DistillatPane distillatPane = new DistillatPane();
            LagretDistillatPane lagretDistillatPane = new LagretDistillatPane();
            FeardigWhiskeysPane feardigeWhiskysPane = new FeardigWhiskeysPane();

            tab01.setContent(lagerePane);
            tab02.setContent(fadePane);
            tab03.setContent(distillatPane);
            tab04.setContent(lagretDistillatPane);
            tab05.setContent(feardigeWhiskysPane);


            tabPane.getTabs().add(tab01);
            tabPane.getTabs().add(tab02);
            tabPane.getTabs().add(tab03);
            tabPane.getTabs().add(tab04);
            tabPane.getTabs().add(tab05);


//            tab01.setOnSelectionChanged(event -> lagerePane.updateControls());
//            tab02.setOnSelectionChanged(event -> fadePane.updateControls());
//            tab03.setOnSelectionChanged(event -> distillatPane.updateControls());
//            tab04.setOnSelectionChanged(event -> lagretDistillatPane.updateControls());
//            tab05.setOnSelectionChanged(event -> feardigeWhiskysPane.updateControls());







        }
}