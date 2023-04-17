package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Controller {

    private Connection connection;

    @FXML
    private TextField literTextField;
    @FXML
    private Button createNewLagretVaeskeButton;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void initialize() {

    }

    @FXML
    private void createNewLagretVaeske(Connection connection, Scanner scanner) {
        System.out.println("Indtast liter:");
        double liter = scanner.nextDouble();

        System.out.println("Indtast alkoholprocent:");
        double alkoholprocent = scanner.nextDouble();

        System.out.println("Indtast paa_fyldnings_dato (yyyy-mm-dd):");
        String paa_fyldnings_dato = scanner.next();

        System.out.println("Indtast tomnings_dato (yyyy-mm-dd):");
        String tomnings_dato = scanner.next();

        System.out.println("Indtast fad_id:");
        int fad_id = scanner.nextInt();

        System.out.println("Indtast slutmaengde:");
        double slutmaengde = scanner.nextDouble();

        String sql = "INSERT INTO lagret_vaeske (liter, alkoholprocent, paa_fyldnings_dato, tomnings_dato, fad_id, slutmaengde) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, liter);
            pstmt.setDouble(2, alkoholprocent);
            pstmt.setString(3, paa_fyldnings_dato);
            pstmt.setString(4, tomnings_dato);
            pstmt.setInt(5, fad_id);
            pstmt.setDouble(6, slutmaengde);

            pstmt.executeUpdate();
            System.out.println("Lagret væske oprettet!");
        } catch (SQLException e) {
            System.out.println("Fejl ved oprettelse af lagret væske: " + e.getMessage());
        }
    }

    private void getTotalDestillaterAndLiter(Connection connection, Scanner scanner) {
        System.out.println("Indtast medarbejderens id:");
        int medarbejder_id = scanner.nextInt();

        String sql = "SELECT COUNT(d.id) AS antal_destillater, SUM(l.liter) AS total_liter FROM medarbejder m JOIN destillat d ON m.distillat_id = d.id JOIN destillatkomponent dk ON dk.destillat_id = d.id JOIN lagret_vaeske l ON l.id = dk.lagretvaeske_id WHERE m.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, medarbejder_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int antal_destillater = rs.getInt("antal_destillater");
                double total_liter = rs.getDouble("total_liter");
                System.out.println("Antal destillater: " + antal_destillater);
                System.out.println("Samlet antal liter destillat væske: " + total_liter);
            } else {
                System.out.println("Ingen data fundet for medarbejderen.");
            }
        } catch (SQLException e) {
            System.out.println("Fejl ved hentning af destillater og liter: " + e.getMessage());
        }
    }

    private void placeFadOnHylde(Connection connection, Scanner scanner) {
        System.out.println("Indtast fad_id:");
        int fad_id = scanner.nextInt();

        System.out.println("Indtast hylde_id:");
        int hylde_id = scanner.nextInt();

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

                            System.out.println("Fad placeret på hylde!");
                        }
                    } else {
                        System.out.println("Der er ikke plads på hylden.");
                    }
                } else {
                    System.out.println("Hylde ikke fundet.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Fejl ved placering af fad på hylde: " + e.getMessage());
        }
    }

}
