package gui;

import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class LagerePane extends GridPane {
    private Controller controller = Controller.getController();
    private ListView<Lager> lstLager = new ListView<>();
    private Button btnOpretLager = new Button("Opret lager");
    private Label lblAntalLagre = new Label("Antal lagre: " + controller.getAlleLagre().size());
    private Label lblTotalAntalFad = new Label("Total antal fad: " + controller.totalAntalFad());
    private Label lblOpretlager = new Label("Nyt lager oplysning") ;
    private Label lblNuværendeLager = new Label("Lager Liste:");
    private Label lblLokation = new Label("Lokation");
    public LagerePane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        //----------------------------------------------list
        this.add(lstLager, 0, 1);
        this.lstLager.setEditable(false);
        this.lstLager.getItems().setAll(controller.getAlleLagre());



        //------------------------------------------------- Labels
        this.add(lblOpretlager,1, 0);
        this.add(lblTotalAntalFad, 0 , 3);
        this.add(lblAntalLagre, 0, 2);
        this.add(lblNuværendeLager,0, 0);

        //------------------------------------------------button
        this.add(btnOpretLager, 15, 2);
        btnOpretLager.setOnAction(event -> actionopretLager());
        //-------------------------------------------------text


    }
    public void actionopretLager() {
        OpretLagerDialog dia =new OpretLagerDialog();
        dia.showAndWait();

    }
    public void updateControls() {
        lstLager.getSelectionModel().clearSelection();

    }


}
