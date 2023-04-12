package gui;

import application.Fad;
import application.Flasketype;
import application.LagretVæske;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class FeardigWhiskeysPane extends GridPane {
    private final Controller controller = Controller.getController();
    private final ComboBox<LagretVæske> comboBoxVælgVæse = new ComboBox<>();
    private final TextField txtNavn = new TextField();
    private final TextArea txtAreaInfo = new TextArea();
    private final ListView<Fad> lstFade = new ListView<>();
    private final ComboBox<Flasketype> comboBoxFlasketype = new ComboBox();
    private final TextField txtVandkilde = new TextField();
    private final TextField txtFortyndelsesprocent = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final RadioButton rbSingleCask = new RadioButton("Single Cask");
    private final RadioButton rbSingleMalt = new RadioButton("Single Malt");
    private final RadioButton rbBlended = new RadioButton("Blended");
    private final ToggleGroup radioGroup = new ToggleGroup();

    public FeardigWhiskeysPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // Radio buttons for whisky type
        HBox radioBox = new HBox(10);
        radioBox.setAlignment(Pos.CENTER);
        rbSingleCask.setToggleGroup(radioGroup);
        rbSingleMalt.setToggleGroup(radioGroup);
        // Funktionalitet for rbSingleMalt er ikke lavet
        rbSingleMalt.setDisable(true);

        // Funktionalitet for rbBlended er ikke lavet
        rbBlended.setDisable(true);
        radioBox.getChildren().addAll(rbSingleCask, rbSingleMalt, rbBlended);
        this.add(radioBox, 0, 0, 3, 1);

        // Liste over færdiglagrede væsker
        this.add(comboBoxVælgVæse, 0, 2);
        comboBoxVælgVæse.setEditable(false);
        comboBoxVælgVæse.getItems().setAll(controller.getFaerdigLagretVaeske());
        comboBoxVælgVæse.setDisable(true);

        comboBoxFlasketype.getItems().addAll(Flasketype.values());

        // Tekstfelt til visning af information
        this.add(txtAreaInfo, 0, 3);
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setPrefRowCount(6);

        // Labels
        Label lblDistillatList = new Label("Fade med færdiglagrede væsker:");
        this.add(lblDistillatList, 0, 1);
        Label lblNavn = new Label("Navn");
        this.add(lblNavn, 1, 1);
        Label lblLiter = new Label("Flasketype");
        this.add(lblLiter, 1, 2);
        Label lblDate = new Label("Dato");
        this.add(lblDate, 1, 6);
        Label lblVandkilde = new Label("Vandkilde");
        this.add(lblVandkilde, 1, 5);
        Label lblFortyndelsesprocent = new Label("Fortyndelsesprocent");
        this.add(lblFortyndelsesprocent, 1, 7);

        // Tekstfelter
        this.add(txtNavn, 2, 1);
        this.add(comboBoxFlasketype, 2, 2);
        this.add(txtVandkilde, 2, 5);
        this.add(txtFortyndelsesprocent, 2, 7);

        // ListView til fade
        this.add(lstFade, 0, 4);

        // DatePicker
        this.add(datePicker, 2, 6);

        // Knap til oprettelse af whisky
        Button btnOpretWhisky = new Button("Opret Whisky");
        this.add(btnOpretWhisky, 1, 8);
        btnOpretWhisky.setOnAction(event -> btnOpretWhisky());

        // Update controls based on radio button selection
        radioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxVælgVæse.setDisable(false);
                lstFade.setDisable(false);
            } else {
                comboBoxVælgVæse.setDisable(true);
                lstFade.setDisable(true);
            }
        });

        // Opdater tekstfelt med info om valgt LagretVæske og liste over fade
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
            showErrorAlert("Udfyld venligst alle felter eller vælg en fad der indeholder den valgte lagrede væske.");
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