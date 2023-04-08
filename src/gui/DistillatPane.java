package gui;

import application.Distillat;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

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

    public DistillatPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // list
        this.add(lstDistillater, 0, 1);
        this.lstDistillater.setEditable(false);
        this.lstDistillater.setMinHeight(200);
        this.lstDistillater.getItems().setAll(controller.getDistillaterMedActualVaeske());

        // labels
        Label lblDistillatList = new Label("Distillat Liste:");
        this.add(lblDistillatList, 0, 0);
        Label lblLiter = new Label("Liter");
        this.add(lblLiter, 1, 0);
        Label lblMaltbatch = new Label("Maltbatch");
        this.add(lblMaltbatch, 1, 1);
        Label lblKornsort = new Label("Kornsort");
        this.add(lblKornsort, 1, 2);
        Label lblAlkoholprocent = new Label("Alkoholprocent");
        this.add(lblAlkoholprocent, 1, 3);
        Label lblRygemateriale = new Label("Rygemateriale");
        this.add(lblRygemateriale, 1, 4);
        Label lblDate = new Label("Dato");
        this.add(lblDate, 1, 5);
        Label lblMedarbejder = new Label("Medarbejder");
        this.add(lblMedarbejder, 1,6);

        // text fields
        this.add(txtLiter, 2, 0);
        this.add(txtMaltbatch, 2, 1);
        this.add(txtKornsort, 2, 2);
        this.add(txtAlkoholprocent, 2, 3);
        this.add(txtRygemateriale, 2, 4);
        this.add(txtMedarbejder, 2, 6);

        // date picker
        this.add(datePicker, 2, 5);

        // button
        Button btnOpretDistillat = new Button("Opret Distillat");
        this.add(btnOpretDistillat, 1, 7);
        btnOpretDistillat.setOnAction(event -> btnOpretDistillatAction());
    }

    private void btnOpretDistillatAction() {
        // Get the input values
        String liter = txtLiter.getText().trim();
        String maltbatch = txtMaltbatch.getText().trim();
        String kornsort = txtKornsort.getText().trim();
        String alkoholprocent = txtAlkoholprocent.getText().trim();
        String rygemateriale = txtRygemateriale.getText().trim();
        String medarbejder = txtMedarbejder.getText().trim();
        LocalDate date = datePicker.getValue();
        // Validation checks
        if (liter.isEmpty() || maltbatch.isEmpty() || kornsort.isEmpty() || alkoholprocent.isEmpty() || rygemateriale.isEmpty() || date == null) {
            showErrorAlert("Udfyld venligst alle felter");
            return;
        }

        try {
            // Convert input values if necessary
            double literValue = Double.parseDouble(liter);
            double alkoholprocentValue = Double.parseDouble(alkoholprocent);

            // Call controller method to create Distillat
            controller.opretDistillat(literValue, maltbatch, kornsort, alkoholprocentValue, rygemateriale, date, medarbejder);

            // Update ListView
            lstDistillater.getItems().setAll(controller.getDistillaterMedActualVaeske());

            // Clear input fields
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
}