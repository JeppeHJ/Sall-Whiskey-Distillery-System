package gui.views.distillatPane;

import application.Distillat;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class DistillatPaneController {

    @FXML
    private ListView<Distillat> lstDistillater;
    @FXML
    private TextField txtLiter;
    @FXML
    private TextField txtMaltbatch;
    @FXML
    private TextField txtKornsort;
    @FXML
    private TextField txtAlkoholprocent;
    @FXML
    private TextField txtRygemateriale;
    @FXML
    private TextField txtMedarbejder;
    @FXML
    private DatePicker datePicker;

    private Controller controller = Controller.getController();

    public void initialize() {
        updateControls();
    }

    @FXML
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
            updateControls();

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

    @FXML
    private void btnSletAction() {
        // TODO: Implementer
    }

    @FXML
    private void btnOpdaterAction() {
        // TODO: Implementer
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateControls() {
        lstDistillater.getItems().setAll(controller.getDistillaterMedActualVaeske());
    }

}
