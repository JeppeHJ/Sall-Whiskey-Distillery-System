package gui;

import application.Fad;
import application.Flasketype;
import application.Lager;
import application.LagretVæske;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class FeardigWhiskeysPane extends GridPane {
    private final Controller controller = Controller.getController();
    private final ComboBox<LagretVæske> lstFaerdigeVaesker = new ComboBox<>();
    private final TextField txtNavn = new TextField();
    private final TextArea txtAreaInfo = new TextArea();
    private final ListView<Fad> lstFade = new ListView<>();
    private final TextField txtAlkoholProcent = new TextField();
    private final TextField txtLiter = new TextField();
    private final TextField txtFlasketype = new TextField();
    private final TextField txtVandkilde = new TextField();
    private final TextField txtFortyndelsesprocent = new TextField();
    private final DatePicker datePicker = new DatePicker();

    public FeardigWhiskeysPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // Liste over færdiglagrede væsker
        this.add(lstFaerdigeVaesker, 0, 1);
        this.lstFaerdigeVaesker.setEditable(false);
        this.lstFaerdigeVaesker.getItems().setAll(controller.getFaerdigLagretVaeske());

        // Tekstfelt til visning af information
        this.add(txtAreaInfo, 0, 2);
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setPrefRowCount(6);

        // Labels
        Label lblDistillatList = new Label("Fade med færdiglagrede væsker:");
        this.add(lblDistillatList, 0, 0);
        Label lblNavn = new Label("Navn");
        this.add(lblNavn, 1, 0);
        Label lblLiter = new Label("Liter");
        this.add(lblLiter, 1, 1);
        Label lblAlkoholprocent = new Label("Alkoholprocent");
        this.add(lblAlkoholprocent, 1, 2);
        Label lblFlasketype = new Label("Flasketype");
        this.add(lblFlasketype, 1, 3);
        Label lblDate = new Label("Dato");
        this.add(lblDate, 1, 5);
        Label lblVandkilde = new Label("Vandkilde");
        this.add(lblVandkilde, 1, 4);
        Label lblFortyndelsesprocent = new Label("Fortyndelsesprocent");
        this.add(lblFortyndelsesprocent, 1, 6);

        // Tekstfelter
        this.add(txtNavn, 2, 0);
        this.add(txtAlkoholProcent, 2, 2);
        this.add(txtLiter, 2, 1);
        this.add(txtFlasketype, 2, 3);
        this.add(txtVandkilde, 2, 4);
        this.add(txtFortyndelsesprocent, 2, 6);

        // ListView til fade
        this.add(lstFade, 0, 3);

        // DatePicker
        this.add(datePicker, 2, 5);

        // Knap til oprettelse af whisky
        Button btnOpretWhisky = new Button("Opret Whisky");
        this.add(btnOpretWhisky, 1, 7);
        btnOpretWhisky.setOnAction(event -> btnOpretWhisky());

        // Opdater tekstfelt med info om valgt LagretVæske og liste over fade
        lstFaerdigeVaesker.valueProperty().addListener((observable, oldValue, newValue) -> {
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
        LagretVæske lV = lstFaerdigeVaesker.getValue();
        String navn = txtNavn.getText().trim();
        String alkoholProcent = txtAlkoholProcent.getText().trim();
        String liter = txtLiter.getText().trim();
        String flasketype = txtFlasketype.getText().trim();
        LocalDate date = datePicker.getValue();
        String vandKilde = txtVandkilde.getText().trim();
        String fortyndelsesprocent = txtFortyndelsesprocent.getText().trim();
        Fad fad = lstFade.getSelectionModel().getSelectedItem();

        if (navn.isEmpty() || alkoholProcent.isEmpty() || liter.isEmpty() || flasketype.isEmpty() || date == null || fad == null) {
            showErrorAlert("Udfyld venligst alle felter eller vælg en fad der indeholder den valgte lagrede væske.");
            return;
        }

        try {
            double literValue = Double.parseDouble(liter);
            double alkoholprocentValue = Double.parseDouble(alkoholProcent);
            double fortyndelsesprocentValue = Double.parseDouble(fortyndelsesprocent);

            controller.opretWhisky(navn, date, alkoholprocentValue, literValue, Flasketype.valueOf(flasketype), lV, fad, vandKilde, fortyndelsesprocentValue);

            lstFaerdigeVaesker.getItems().setAll(controller.getFaerdigLagretVaeske());

            txtNavn.clear();
            txtAlkoholProcent.clear();
            txtLiter.clear();
            txtFlasketype.clear();
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
        lstFaerdigeVaesker.getItems().clear();
        lstFaerdigeVaesker.getItems().setAll(controller.getFaerdigLagretVaeske());
    }
}