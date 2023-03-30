package gui;

import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LagerePane extends GridPane {
    private final Controller controller = Controller.getController();
    private final ListView<Lager> lstLager = new ListView<>();
    private final Label lblAntalLagre = new Label("Antal lagre: " + controller.getAlleLagre().size());
    private final Label lblTotalAntalFad = new Label("Total antal fad: " + controller.totalAntalFad());
    private final TextField txtLagerLokation = new TextField();
    private final TextField txtLagerPladser = new TextField();

    public LagerePane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // list
        this.add(lstLager, 0, 1);
        this.lstLager.setEditable(false);
        this.lstLager.getItems().setAll(controller.getAlleLagre());
        this.lstLager.setMinHeight(200);

        // labels
        Label lblOpretlager = new Label("Nyt lager oplysninger");
        this.add(lblOpretlager, 1, 0);
        this.add(lblTotalAntalFad, 0, 7);
        this.add(lblAntalLagre, 0, 5);
        Label lblNuvaerendeLager = new Label("Lager Liste:");
        this.add(lblNuvaerendeLager, 0, 0);
        Label lblPladser = new Label("Pladser");
        Label lblLokation = new Label("Lokation");

        // button
        Button btnOpretLager = new Button("Opret lager");
        btnOpretLager.setOnAction(event -> btnOpretAction());

        // opretLagerInnerPane
        GridPane opretLagerInnerPane = new GridPane();
        opretLagerInnerPane.setHgap(10);
        opretLagerInnerPane.setVgap(20);
        this.add(opretLagerInnerPane, 1, 1);
        opretLagerInnerPane.setGridLinesVisible(false);
        opretLagerInnerPane.add(lblLokation, 0, 0);
        opretLagerInnerPane.add(lblPladser, 0, 1);
        opretLagerInnerPane.add(txtLagerLokation, 1, 0);
        opretLagerInnerPane.add(txtLagerPladser, 1, 1);
        opretLagerInnerPane.add(btnOpretLager, 0, 3);
        
    }

    public void updateControls() {
        this.lstLager.getItems().setAll(controller.getAlleLagre());
        lblAntalLagre.setText("Antal lagre: " + controller.getAlleLagre().size());
        lblTotalAntalFad.setText("Total antal fad: " + controller.totalAntalFad());
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void btnOpretAction() {
        String lokation = txtLagerLokation.getText().trim();
        String pladserStr = txtLagerPladser.getText().trim();
        int pladser;

        if (lokation.isEmpty() || pladserStr.isEmpty()) {
            showErrorAlert("Udfyld venligst alle felter");
            return;
        }

        try {
            pladser = Integer.parseInt(pladserStr);
        } catch (NumberFormatException e) {
            showErrorAlert("Indtast venligst et gyldigt tal for pladser");
            return;
        }

        if (pladser <= 0) {
            showErrorAlert("Antallet af pladser skal være større end 0");
            return;
        }

        controller.opretLager(lokation, pladser);
        updateControls();
        clearFields();
    }

    private void clearFields() {
        txtLagerLokation.clear();
        txtLagerPladser.clear();
    }
}
