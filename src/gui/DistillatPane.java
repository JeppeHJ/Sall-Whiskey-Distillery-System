package gui;

import application.Distillat;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class DistillatPane extends GridPane {
    private final Controller controller = Controller.getController();
    private final ListView<Distillat> lstDistillater = new ListView<>();
    private final TextField txtLiter = new TextField();
    private final TextField txtMaltbatch = new TextField();
    private final TextField txtKornsort = new TextField();
    private final TextField txtAlkoholprocent = new TextField();
    private final TextField txtRygemateriale = new TextField();
    private final TextField txtMedarbejder = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final VBox listViewBox = new VBox(10);

    public DistillatPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        lstDistillater.setPrefHeight(150);
        listViewBox.getChildren().addAll(new Label("Distillat Liste:"), lstDistillater);

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        Label lblLiter = new Label("Liter");
        Label lblMaltbatch = new Label("Maltbatch");
        Label lblKornsort = new Label("Kornsort");
        Label lblAlkoholprocent = new Label("Alkoholprocent");
        Label lblRygemateriale = new Label("Rygemateriale");
        Label lblDate = new Label("Dato");
        Label lblMedarbejder = new Label("Medarbejder");

        inputGrid.add(lblLiter, 0, 0);
        inputGrid.add(txtLiter, 1, 0);
        inputGrid.add(lblMaltbatch, 0, 1);
        inputGrid.add(txtMaltbatch, 1, 1);
        inputGrid.add(lblKornsort, 0, 2);
        inputGrid.add(txtKornsort, 1, 2);
        inputGrid.add(lblAlkoholprocent, 0, 3);
        inputGrid.add(txtAlkoholprocent, 1, 3);
        inputGrid.add(lblRygemateriale, 0, 4);
        inputGrid.add(txtRygemateriale, 1, 4);
        inputGrid.add(lblDate, 0, 5);
        inputGrid.add(datePicker, 1, 5);
        inputGrid.add(lblMedarbejder, 0, 6);
        inputGrid.add(txtMedarbejder, 1, 6);

        Button btnOpretDistillat = new Button("Opret");
        btnOpretDistillat.setOnAction(event -> btnOpretDistillatAction());

        Button btnOpdater = new Button("Opdater");
        btnOpdater.setDisable(true);
        btnOpdater.setOnAction(event -> btnOpdaterAction());

        Button btnSlet = new Button("Slet");
        btnSlet.setDisable(true);
        btnSlet.setOnAction(event -> btnSletAction());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(btnSlet, btnOpdater, btnOpretDistillat);
        lstDistillater.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
    }

    private void btnSletAction() {
    }

    private void btnOpdaterAction() {
    }

    private void btnOpretDistillatAction() {
        // Hent inputværdier
        String liter = txtLiter.getText().trim();
        String maltbatch = txtMaltbatch.getText().trim();
        String kornsort = txtKornsort.getText().trim();
        String alkoholprocent = txtAlkoholprocent.getText().trim();
        String rygemateriale = txtRygemateriale.getText().trim();
        String medarbejder = txtMedarbejder.getText().trim();
        LocalDate date = datePicker.getValue();

        // Valideringskontrol
        if (liter.isEmpty() || maltbatch.isEmpty() || kornsort.isEmpty() || alkoholprocent.isEmpty() || rygemateriale.isEmpty() || date == null) {
            showErrorAlert("Udfyld venligst alle felter");
            return;
        }

        try {
            // Konverter inputværdier, hvis nødvendigt
            double literValue = Double.parseDouble(liter);
            double alkoholprocentValue = Double.parseDouble(alkoholprocent);

            // Kald controller-metoden til at oprette Distillat
            controller.opretDistillat(literValue, maltbatch, kornsort, alkoholprocentValue, rygemateriale, date, medarbejder);

            // Opdater ListView
            listViewBox.getChildren().addAll(new Label("Distillat Liste:"), lstDistillater);

            // Ryd inputfelter
            txtLiter.clear();
            txtMaltbatch.clear();
            txtKornsort.clear();
            txtAlkoholprocent.clear();
            txtRygemateriale.clear();
            datePicker.setValue(null);
        } catch (NumberFormatException e) {
            showErrorAlert("Ugyldigt input. Kontrollér venligst dine indtastede værdier.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateControls() {
        lstDistillater.getItems().setAll(controller.getDistillaterMedFaktiskVæske());
    }
}
