package gui;

import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class FadePane extends GridPane {
    private Controller controller = Controller.getController();
    private ListView<Lager> lstLager = new ListView<>();
    private Button btnOpretLager = new Button("Opret lager");
    private Label antalLagre = new Label("Antal lagre: " + controller.getAlleLagre().size());
    private Label totalAntalFad = new Label("Total antal fad: " + controller.totalAntalFad());
    public FadePane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        //----------------------------------------------


        this.add(lstLager, 0, 0);
        this.lstLager.setEditable(false);
        this.lstLager.getItems().setAll(controller.getAlleLagre());
        this.add(btnOpretLager, 15, 2);
//        btnOpretLager.setOnAction(event -> actionopretLager());
        this.add(antalLagre, 0, 1);
        this.add(totalAntalFad, 0 , 2);
}}
