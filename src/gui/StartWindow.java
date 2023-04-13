package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class StartWindow extends Application {

    private  Controller controller = Controller.getController();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sporbarhed i produktionen og fadlagerstyring");
        BorderPane pane = new BorderPane();
        this.initContent(pane);


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(800);
        stage.setWidth(1000);
        stage.show();
        stage.setResizable(false);
    }



        private void initContent(BorderPane pane) {
        controller.createSomeObjects();
            TabPane tabPane = new TabPane();
            this.initTabPane(tabPane);
            pane.setCenter(tabPane);

        }

        private void initTabPane(TabPane tabPane) {
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


            Tab tab01 = new Tab("Lager");
            Tab tab02 = new Tab("Fad");
            Tab tab03 = new Tab("Distillat");
            Tab tab04 = new Tab("Påfyldning");
            Tab tab05 = new Tab("Whisky");
            Tab tab06 = new Tab("Produkter");

            LagerPane lagerPane = new LagerPane();
            FadPane fadPane = new FadPane();
            DistillatPane distillatPane = new DistillatPane();
            ProduktPane produktPane = new ProduktPane();
            WhiskyPane whiskyPane = new WhiskyPane();
            PaafyldningPane paafyldningPane = new PaafyldningPane();

            tab01.setContent(lagerPane);
            tab02.setContent(fadPane);
            tab03.setContent(distillatPane);
            tab04.setContent(paafyldningPane);
            tab05.setContent(whiskyPane);
            tab06.setContent(produktPane);

            tabPane.getTabs().add(tab01);
            tabPane.getTabs().add(tab02);
            tabPane.getTabs().add(tab03);
            tabPane.getTabs().add(tab04);
            tabPane.getTabs().add(tab05);
            tabPane.getTabs().add(tab06);

            tab01.setOnSelectionChanged(event -> lagerPane.updateControls());
            tab02.setOnSelectionChanged(event -> fadPane.updateControls());
            tab03.setOnSelectionChanged(event -> distillatPane.updateControls());
            tab04.setOnSelectionChanged(event -> paafyldningPane.updateControls());
            tab05.setOnSelectionChanged(event -> whiskyPane.updateControls());
            tab06.setOnSelectionChanged(event -> produktPane.updateControls());







        }
}
