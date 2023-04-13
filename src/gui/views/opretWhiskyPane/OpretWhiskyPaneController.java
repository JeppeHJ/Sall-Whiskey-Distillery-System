package gui.views.opretWhiskyPane;


import application.*;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class OpretWhiskyPaneController {
    private final Controller controller = Controller.getController();

    @FXML
    private RadioButton rbSingleCask, rbSingleMalt, rbBlended;

    @FXML
    private ComboBox<LagretVæske> comboBoxVælgVæse;

    @FXML
    private TextArea txtAreaInfo;

    @FXML
    private ListView<Fad> lstFade;

    @FXML
    private ComboBox<Flasketype> comboBoxFlasketype;

    @FXML
    private TextField txtNavn, txtVandkilde, txtFortyndelsesprocent;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnOpretWhisky;

    private final ToggleGroup radioGroup = new ToggleGroup();

    @FXML
    public void initialize() {
        // Initialize radio buttons
        rbSingleCask.setToggleGroup(radioGroup);
        rbSingleMalt.setToggleGroup(radioGroup);
        rbSingleMalt.setDisable(true);
        rbBlended.setToggleGroup(radioGroup);
        rbBlended.setDisable(true);

        // Initialize comboBoxVælgVæse
        comboBoxVælgVæse.setEditable(false);
        comboBoxVælgVæse.getItems().setAll(controller.getFaerdigLagretVaeske());
        comboBoxVælgVæse.setDisable(true);

        // Initialize comboBoxFlasketype
        comboBoxFlasketype.getItems().addAll(Flasketype.values());

        // Initialize txtAreaInfo
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setPrefRowCount(6);

        // Add event handlers
        btnOpretWhisky.setOnAction(event -> btnOpretWhisky());

        radioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxVælgVæse.setDisable(false);
                lstFade.setDisable(false);
            } else {
                comboBoxVælgVæse.setDisable(true);
                lstFade.setDisable(true);
            }
        });

        comboBoxVælgVæse.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtAreaInfo.setText("Distillat: " + newValue.getDistillater() + "\n" +
                        "Fadehistorik: " + newValue.getFadehistorik() + "\n"
                );

                lstFade.getItems().setAll(controller.getBarrelsContainingLagretVaeske(newValue));

                if (!lstFade.getItems().isEmpty()) {
                    lstFade.getSelectionModel().selectFirst();
                }
            } else {
                txtAreaInfo.clear();
                lstFade.getItems().clear();
            }
        });
    }

    private void btnOpretWhisky() {
        LagretVæske lV = comboBoxVælgVæse.getValue();
        String navn = txtNavn.getText().trim();
        Flasketype flasketype = comboBoxFlasketype.getValue();
        String liter = String.valueOf(flasketype.getCapacity());
        LocalDate date = datePicker.getValue();
        String vandKilde = txtVandkilde.getText().trim();
        String fortyndelsesprocent = txtFortyndelsesprocent.getText().trim();
        Fad fad = lstFade.getSelectionModel().getSelectedItem();

        if (navn.isEmpty() || date == null || fad == null) {
            showErrorAlert("Udfyld venligst alle felter eller vælg et fad der indeholder den valgte lagrede væske.");
            return;
        }

        try {
            double literValue = Double.parseDouble(liter);
            double fortyndelsesprocentValue = Double.parseDouble(fortyndelsesprocent);

            controller.opretWhisky(navn, date, literValue, flasketype, lV, fad, vandKilde, fortyndelsesprocentValue);

            comboBoxVælgVæse.getItems().setAll(controller.getFaerdigLagretVaeske());

            txtNavn.clear();
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
        comboBoxVælgVæse.getItems().clear();
        comboBoxVælgVæse.getItems().setAll(controller.getFaerdigLagretVaeske());
    }

}

