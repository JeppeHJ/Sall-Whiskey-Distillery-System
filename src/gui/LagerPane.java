package gui;

import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LagerPane extends GridPane {
    private final Controller controller = Controller.getController();
    private final ListView<Lager> lstLager = new ListView<>();
    private final Label lblAntalLagre = new Label("Antal lagre: " + controller.getAlleLagre().size());
    private final Label lblTotalAntalFad = new Label("Total antal fad: " + controller.totalAntalFad());
    private final TextField txtLagerLokation = new TextField();
    private final TextField txtLagerPladser = new TextField();
    private final VBox listViewBox = new VBox(10); // Set spacing to 10

    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.updateControls();

        lstLager.setPrefHeight(150); // Set the preferred height for the ListView
        listViewBox.getChildren().addAll(new Label("Lager Liste:"), lstLager);

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        // Labels
        Label lblLokation = new Label("Lokation");
        Label lblPladser = new Label("Pladser");

        inputGrid.add(lblLokation, 0, 0);
        inputGrid.add(txtLagerLokation, 1, 0);
        inputGrid.add(lblPladser, 0, 1);
        inputGrid.add(txtLagerPladser, 1, 1);

        Button btnOpretLager = new Button("Opret");
        btnOpretLager.setOnAction(event -> btnOpretAction());

        Button btnOpdater = new Button("Opdater");
        btnOpdater.setDisable(true);
        btnOpdater.setOnAction(event -> btnOpdaterAction());

        Button btnSlet = new Button("Slet");
        btnSlet.setDisable(true);
        btnSlet.setOnAction(event -> btnSletAction());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(btnSlet, btnOpdater, btnOpretLager);

        lstLager.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnOpdater.setDisable(false);
                btnSlet.setDisable(false);
            } else {
                btnOpdater.setDisable(true);
                btnSlet.setDisable(true);
            }
        });

        this.add(listViewBox, 0, 0);
        this.add(inputGrid, 1, 0);
        this.add(buttonBox, 1, 1);
        this.add(lblAntalLagre, 0, 2);
        this.add(lblTotalAntalFad, 0, 3);
    }

    public void updateControls() {
        lstLager.getItems().setAll(controller.getAlleLagre());
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

    private void btnOpdaterAction() {
        // Add your update logic here
    }

    private void btnSletAction() {
        // Add your delete logic here
    }
}

