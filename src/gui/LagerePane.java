package gui;

import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LagerePane extends GridPane {
    private Controller controller = Controller.getController();
    private ListView<Lager> lstLager = new ListView<>();
    private Button btnOpretLager = new Button("Opret lager");
    private Label lblAntalLagre = new Label("Antal lagre: " + controller.getAlleLagre().size());
    private Label lblTotalAntalFad = new Label("Total antal fad: " + controller.totalAntalFad());
    private Label lblOpretlager = new Label("Nyt lager oplysninger");
    private Label lblNuværendeLager = new Label("Lager Liste:");
    private Label lblLokation = new Label("Lokation");
    private Label lblPladser = new Label("Pladser");
    private Label lblError = new Label();
    private TextField txtLagerLokation = new TextField();
    private TextField txtLagerPladser = new TextField();

    public LagerePane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //----------------------------------------------list
        this.add(lstLager, 0, 1);
        this.lstLager.setEditable(false);
        this.lstLager.getItems().setAll(controller.getAlleLagre());
        this.lstLager.setMinHeight(200);


        //------------------------------------------------- Labels
        this.add(lblOpretlager, 1, 0);
        this.add(lblTotalAntalFad, 0, 7);
        this.add(lblAntalLagre, 0, 5);
        this.add(lblNuværendeLager, 0, 0);


        //------------------------------------------------button

        //-------------------------------------------------text


        //-------------------------------------------------opretLagerInnerpane
        GridPane opretLagerInnerpane = new GridPane();
        opretLagerInnerpane.setHgap(10);
        opretLagerInnerpane.setVgap(20);
        this.add(opretLagerInnerpane, 1, 1);
        opretLagerInnerpane.setGridLinesVisible(false);
        opretLagerInnerpane.add(lblLokation,0,0);
        opretLagerInnerpane.add(lblPladser, 0, 1);
        opretLagerInnerpane.add(txtLagerLokation,1,0);
        opretLagerInnerpane.add(txtLagerPladser,1,1);
        opretLagerInnerpane.add(btnOpretLager, 0, 3);
        btnOpretLager.setOnAction(event -> btnOpretAction());
        opretLagerInnerpane.add(lblError, 1,3);




    }



    public void updateControls() {
        this.lstLager.getItems().setAll(controller.getAlleLagre());
        lblAntalLagre.setText("Antal lagre: " + controller.getAlleLagre().size());
        lblTotalAntalFad.setText("Total antal fad: " + controller.totalAntalFad());



    }
    //TODO lav input validering
    private void btnOpretAction() {
        String lokation = txtLagerLokation.getText().trim();
        int pladser = Integer.parseInt(txtLagerPladser.getText().trim());
        if (lokation.length() == 0 || pladser == 0 || txtLagerPladser.getText().trim().length() == 0) {
            lblError.setText("Udfyld venligst alle felter");
        } else {
            controller.opretLager(lokation, pladser);
            this.updateControls();
        }
    }
}
