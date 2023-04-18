package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Controller {

    private Connection connection;

    @FXML
    private TextField maltTextField;
    @FXML
    private TextField kornTextField;
    @FXML
    private TextField alkoholprocentTextField;
    @FXML
    private TextField paaFyldningsDatoTextField;
    @FXML
    private TextField tomningsDatoTextField;
    @FXML
    private TextField fadIdTextField;
    @FXML
    private TextField slutmaengdeTextField;
    @FXML
    private TextField medarbejderIdTextField;
    @FXML
    private TextField tilbageTextField;
    @FXML
    private TextField distillatAntalTextField;
    @FXML
    private TextField literTotalTextField;
    @FXML
    private TextField hyldeIdTextField;
    @FXML
    private Button createNewLagretVaeskeButton;
    @FXML
    private Button getTotalDestillaterAndLiterButton;
    @FXML
    private Button placeFadOnHyldeButton;
    @FXML
    private ListView<String> lstMedarbejdere;
    @FXML
    private DatePicker datePicker;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void initData() {
        if (connection != null) {
            loadMedarbejderNames();
        } else {
            System.err.println("Connection is null in Controller.initData()");
        }
    }

    // TODO: update
    @FXML
    private void createNewDistillat(ActionEvent event) {
        String malt_batch = maltTextField.getText();
        String kornsort = kornTextField.getText();
        double alkoholprocent = Double.parseDouble(alkoholprocentTextField.getText());

        int fad_id = Integer.parseInt(fadIdTextField.getText());

        double liter_tilbage = Double.parseDouble(tilbageTextField.getText());
        LocalDate dato_for_faerdig = datePicker.getValue();

        String sql = "INSERT INTO lagret_vaeske (liter, alkoholprocent, paa_fyldnings_dato, tomnings_dato, fad_id, slutmaengde) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, malt_batch);
            pstmt.setDouble(2, alkoholprocent);
            pstmt.setString(3, paa_fyldnings_dato);
            pstmt.setString(4, tomnings_dato);
            pstmt.setInt(5, fad_id);
            pstmt.setDouble(6, slutmaengde);

            pstmt.executeUpdate();
            showAlert("Success", "Lagret væske oprettet!", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Error", "Fejl ved oprettelse af lagret væske: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void getTotalDestillaterAndLiter(ActionEvent event) {
        String selectedMedarbejder = lstMedarbejdere.getSelectionModel().getSelectedItem();
        if (selectedMedarbejder == null) {
            showAlert("Warning", "Vælg en medarbejder fra listen.", Alert.AlertType.WARNING);
            return;
        }
        int medarbejder_id = Integer.parseInt(selectedMedarbejder.substring(0, selectedMedarbejder.indexOf(' ')));

        String sql = "SELECT COUNT(d.id) AS antal_destillater, SUM(l.liter) AS total_liter FROM medarbejder m JOIN destillat d ON m.distillat_id = d.id JOIN destillatkomponent dk ON dk.destillat_id = d.id JOIN lagret_vaeske l ON l.id = dk.lagretvaeske_id WHERE m.id = ?";
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

    // TODO: update
    @FXML
    private void placeFadOnHylde(ActionEvent event) {
        int fad_id = Integer.parseInt(fadIdTextField.getText());
        int hylde_id = Integer.parseInt(hyldeIdTextField.getText());

        try {
            // Hent antal fad på hylden
            String checkSql = "SELECT antal_fad FROM hylde WHERE id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setInt(1, hylde_id);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int antal_fad = rs.getInt("antal_fad");

                    // Opdater hylde_id for fad, hvis der er plads på hylden
                    if (antal_fad < 5) { // Antager, at den maksimale kapacitet for en hylde er 5 fad
                        String updateSql = "UPDATE fad SET hylde_id = ? WHERE id = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, hylde_id);
                            updateStmt.setInt(2, fad_id);
                            updateStmt.executeUpdate();

                            // Opdater antal_fad for hylde
                            String updateHyldeSql = "UPDATE hylde SET antal_fad = antal_fad + 1 WHERE id = ?";
                            try (PreparedStatement updateHyldeStmt = connection.prepareStatement(updateHyldeSql)) {
                                updateHyldeStmt.setInt(1, hylde_id);
                                updateHyldeStmt.executeUpdate();
                            }

                            showAlert("Success", "Fad placeret på hylde!", Alert.AlertType.INFORMATION);
                        }
                    } else {
                        showAlert("Warning", "Der er ikke plads på hylden.", Alert.AlertType.WARNING);
                    }
                } else {
                    showAlert("Warning", "Hylde ikke fundet.", Alert.AlertType.WARNING);
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Fejl ved placering af fad på hylde: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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

}
