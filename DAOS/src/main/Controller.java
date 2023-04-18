package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

public class Controller {

    private Connection connection;

    @FXML
    private TextField maltTextField;
    @FXML
    private TextField literTotalTextField;
    @FXML
    private TextField kornTextField;
    @FXML
    private TextField alkoholprocentTextField;
    @FXML
    private TextField rygeTextField;
    @FXML
    private TextField tilbageTextField;
    @FXML
    private TextField distillatAntalTextField;
    @FXML
    private ListView<String> lstMedarbejdere;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> comboBoxFad;
    @FXML
    private ComboBox<String> comboBoxHylde;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void initData() {
        if (connection != null) {
            loadMedarbejderNames();
            populateComboBoxes();
            lstMedarbejdere.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    getTotalDestillaterAndLiter();
                } else {
                    distillatAntalTextField.setText("");
                    literTotalTextField.setText("");
                }
            });
        } else {
            System.err.println("Connection is null in Controller.initData()");
        }
    }

    @FXML
    private void createNewDistillat(ActionEvent event) {
        String malt_batch = maltTextField.getText();
        String kornsort = kornTextField.getText();
        String alkoholprocentText = alkoholprocentTextField.getText();
        String rygemateriale = rygeTextField.getText();
        String liter_tilbageText = tilbageTextField.getText();
        LocalDate dato_for_faerdig = datePicker.getValue();

        if (malt_batch.isEmpty() || kornsort.isEmpty() || alkoholprocentText.isEmpty() || rygemateriale.isEmpty() || liter_tilbageText.isEmpty() || dato_for_faerdig == null) {
            showAlert("Error", "Udfyld venligst alle felter.", Alert.AlertType.ERROR);
        } else {
            try {
                double alkoholprocent = Double.parseDouble(alkoholprocentText);
                double liter_tilbage = Double.parseDouble(liter_tilbageText);

                String sql = "INSERT INTO distillat (malt_batch, kornsort, alkoholprocent, rygemateriale, liter_tilbage, dato_for_faerdig) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, malt_batch);
                    pstmt.setString(2, kornsort);
                    pstmt.setDouble(3, alkoholprocent);
                    pstmt.setString(4, rygemateriale);
                    pstmt.setDouble(5, liter_tilbage);
                    pstmt.setDate(6, Date.valueOf(dato_for_faerdig));

                    pstmt.executeUpdate();
                    showAlert("Success", "Destillat oprettet!", Alert.AlertType.INFORMATION);
                } catch (SQLException e) {
                    showAlert("Error", "Fejl ved oprettelse af destillat: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Ugyldigt input i alkoholprocent eller liter tilbage felter.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void getTotalDestillaterAndLiter() {
        String selectedMedarbejder = lstMedarbejdere.getSelectionModel().getSelectedItem();
        int medarbejder_id = Integer.parseInt(selectedMedarbejder.substring(0, selectedMedarbejder.indexOf(' ')));

        String sql = "SELECT COUNT(d.id) AS antal_destillater, SUM(l.liter) AS total_liter FROM medarbejder m JOIN distillat d ON m.distillat_id = d.id JOIN DestillatKomponent dk ON dk.destillat_id = d.id JOIN lagret_vaeske l ON l.id = dk.lagretvaeske_id WHERE m.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, medarbejder_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                distillatAntalTextField.setText(String.valueOf(rs.getInt("antal_destillater")));
                literTotalTextField.setText(String.valueOf(rs.getDouble("total_liter")));
            } else {
                showAlert("Warning", "Ingen data fundet for medarbejderen.", Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            showAlert("Error", "Fejl ved hentning af destillater og liter: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void placeFadOnHylde(ActionEvent event) {
        String fad = String.valueOf(comboBoxFad.getValue());
        String hylde = String.valueOf(comboBoxHylde.getValue());
        if (fad == null || hylde == null) {
            showAlert("Warning", "Vælg både fad og hylde.", Alert.AlertType.WARNING);
            return;
        }
        String sql = "SELECT h.antal_fad, COUNT(f.id) as fad_count FROM Hylde h LEFT JOIN fad f ON h.id = f.hylde_id WHERE h.id = ? GROUP BY h.antal_fad";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(hylde));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int antal_fad = rs.getInt("antal_fad");
                int fad_count = rs.getInt("fad_count");
                if (fad_count >= antal_fad) {
                    showAlert("Error", "Der er ikke plads på hylden.", Alert.AlertType.ERROR);
                    return;
                }
            }
            PreparedStatement updateFad = connection.prepareStatement("UPDATE fad SET hylde_id = ? WHERE id = ?");
            updateFad.setInt(1, Integer.parseInt(hylde));
            updateFad.setInt(2, Integer.parseInt(fad));
            int rowsAffected = updateFad.executeUpdate();
            if (rowsAffected == 1) {
                showAlert("Success", "Fadet er blevet placeret på hylden.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Der opstod en fejl ved placering af fadet.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Error", "Fejl placering af fad på hylden: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        populateComboBoxes();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadMedarbejderNames() {
        String sql = "SELECT id, navn FROM medarbejder ORDER BY id, navn ASC";
        ObservableList<String> medarbejderNames = FXCollections.observableArrayList();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                medarbejderNames.add(rs.getInt("id") + " | " + rs.getString("navn"));
            }
        } catch (SQLException e) {
            showAlert("Error", "Fejl ved hentning af medarbejder navne: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        lstMedarbejdere.setItems(medarbejderNames);
    }

    private void populateComboBoxes() {
        String sqlHylde = "SELECT id FROM Hylde";
        String sqlFad = "SELECT id FROM fad WHERE hylde_id IS NULL";
        ObservableList<String> hylder = FXCollections.observableArrayList();
        ObservableList<String> fade = FXCollections.observableArrayList();
        try (PreparedStatement pstmtHylde = connection.prepareStatement(sqlHylde);
             PreparedStatement pstmtFad = connection.prepareStatement(sqlFad)) {

            ResultSet rsHylde = pstmtHylde.executeQuery();
            while (rsHylde.next()) {
                hylder.add(rsHylde.getString("id"));
            }
            comboBoxHylde.setItems(hylder);
            comboBoxHylde.getSelectionModel().selectFirst();

            ResultSet rsFad = pstmtFad.executeQuery();
            while (rsFad.next()) {
                fade.add(rsFad.getString("id"));
            }
            comboBoxFad.setItems(fade);
            comboBoxFad.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Fejl ved hentning af fad og hylde: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


}
