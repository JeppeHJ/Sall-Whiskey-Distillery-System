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
    private TextField fadIdTextField;
    @FXML
    private TextField hyldeIdTextField;
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
    private ComboBox<Integer> comboBoxFad;
    @FXML
    private ComboBox<Integer> comboBoxHylde;

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

    @FXML
    private void createNewDistillat(ActionEvent event) {
        String malt_batch = maltTextField.getText();
        String kornsort = kornTextField.getText();
        double alkoholprocent = Double.parseDouble(alkoholprocentTextField.getText());
        String rygemateriale = rygeTextField.getText();
        double liter_tilbage = Double.parseDouble(tilbageTextField.getText());
        LocalDate dato_for_faerdig = datePicker.getValue();

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
        String fad = String.valueOf(comboBoxFad.getValue());
        String hylde = String.valueOf(comboBoxHylde.getValue());

        if (fad == null || hylde == null) {
            showAlert("Error", "Vælg både fad og hylde.", Alert.AlertType.WARNING);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:your_database_url", "username", "password")) {

            // Check if there's space on the shelf
            PreparedStatement checkSpace = connection.prepareStatement("SELECT antal_fad, COUNT(fad_id) as fad_count FROM Hylde h LEFT JOIN fad f ON h.id = f.hylde_id WHERE h.id = ? GROUP BY h.antal_fad");
            checkSpace.setInt(1, Integer.parseInt(hylde));
            ResultSet resultSet = checkSpace.executeQuery();

            if (resultSet.next()) {
                int antal_fad = resultSet.getInt("antal_fad");
                int fad_count = resultSet.getInt("fad_count");

                if (fad_count >= antal_fad) {
                    errorMessage.setText("Der er ikke plads på hylden.");
                    return;
                }
            }

            // Place the cask on the shelf
            PreparedStatement updateFad = connection.prepareStatement("UPDATE fad SET hylde_id = ? WHERE id = ?");
            updateFad.setInt(1, Integer.parseInt(hylde));
            updateFad.setInt(2, Integer.parseInt(fad));
            int rowsAffected = updateFad.executeUpdate();

            if (rowsAffected == 1) {
                errorMessage.setText("Fadet er blevet placeret på hylden.");
            } else {
                errorMessage.setText("Der opstod en fejl ved placering af fadet.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage.setText("Der opstod en fejl ved kommunikation med databasen.");
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
