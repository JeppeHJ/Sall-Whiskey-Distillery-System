package gui;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * FadePane klassen repræsenterer en JavaFX-pane, der tillader brugeren at oprette
 * og se nye fade, vælge et lager og se de forskellige positioner i lageret.
 */
public class FadePane extends GridPane {
    private final Controller controller = Controller.getController();

    private int selectedPosition;
    private GridPane warehouseGrid;
    private final ListView<Fad> listFade = new ListView<>();
    private final TextField txtxFadtype = new TextField();
    private final TextField txtfadStr = new TextField();
    private final ComboBox<Lager> comboBoxLager = new ComboBox<>();
    private Label lblActualPosition = new Label();

    private final ListView<Distillat> lstDistillat = new ListView<>();
    private final ListView<String> lstIndholdshistorik = new ListView<>();
    private final ListView<String> lstOmhældningshistorik = new ListView<>();

    private Lager lagerChoice;

    public FadePane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // Opretter ComboBox med lagre
        comboBoxLager.getItems().addAll(controller.getAlleLagre());
        this.add(comboBoxLager, 3, 0);

        // Lytter til valg af lager i ComboBox
        comboBoxLager.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            listFade.getSelectionModel().clearSelection();
            if (newSelection != null) {
                listFade.getItems().setAll(controller.getFadeIHashMap(newSelection.getId()).values());
                this.lagerChoice = newSelection;
                updateWarehouseGrid();
            } else {
                listFade.getItems().clear();
            }
        });

        // Tilføjer og konfigurerer fade-liste og andre lister
        this.add(listFade, 1, 3);
        listFade.setEditable(false);
        listFade.setMinHeight(200);

        this.add(lstDistillat, 1, 4);
        lstDistillat.setEditable(false);
        lstDistillat.setMinHeight(200);
        this.add(lstIndholdshistorik, 1, 5);
        lstIndholdshistorik.setEditable(false);
        lstIndholdshistorik.setMinHeight(200);
        this.add(lstOmhældningshistorik, 1, 6);
        lstOmhældningshistorik.setEditable(false);
        lstOmhældningshistorik.setMinHeight(200);

        // Tilføjer og konfigurerer etiketter
        this.add(new Label("Fad størrelse"), 0, 0);
        this.add(new Label("Vælg position"), 3, 2);
        this.add(new Label("Fad Type"), 0, 1);
        this.add(new Label("Vælg lager:"), 2, 0);
        this.add(new Label("Total fade: " + controller.totalAntalFad()), 0, 7);
        this.add(new Label("Position"), 0, 2);
        this.add(new Label("Distillat"), 0, 4);
        this.add(new Label("Indholdshistorik"), 0, 5);
        this.add(new Label("Omhældningshistorik"), 0, 6);

        this.add(lblActualPosition, 1, 2);

        // Opdaterer listerne baseret på det valgte fad
        listFade.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ArrayList<Distillat> distillatList = new ArrayList<>();
                for (LagretVæske lV : newSelection.getLagretVæsker()) {
                    distillatList.addAll(lV.getDistillater());
                }
                lstDistillat.getItems().setAll(distillatList);
            } else {
                lstDistillat.getItems().clear();
                lstIndholdshistorik.getItems().clear();
                lstOmhældningshistorik.getItems().clear();
            }
        });

        // Tilføjer og konfigurerer knappen "Opret fad"
        Button btnopretfad = new Button("Opret fad");
        this.add(btnopretfad, 3, 1);
        btnopretfad.setOnAction(event -> btnOpretAction());

        // Tilføjer og konfigurerer tekstfelter
        this.add(txtfadStr, 1, 0);
        this.add(txtxFadtype, 1, 1);

        // Opretter og tilføjer warehouseGrid
        warehouseGrid = createWarehouseGrid();
        this.add(warehouseGrid, 3, 3);
    }

    /**
     * Håndterer logikken for at oprette et nyt fad baseret på de indtastede oplysninger og den valgte lagerposition.
     */
    private void btnOpretAction() {
        Lager lager = comboBoxLager.getValue();
        String fadtype = txtxFadtype.getText().trim();

        if (lager == null || fadtype.isEmpty() || txtfadStr.getText().trim().isEmpty() || selectedPosition == 0) {
            showErrorAlert("Udfyld venligst alle felter");
            return;
        }

        try {
            int fadStr = Integer.parseInt(txtfadStr.getText().trim());
            controller.opretFad(fadtype, fadStr, lager, selectedPosition);
            txtfadStr.clear();
            txtxFadtype.clear();
            comboBoxLager.getSelectionModel().clearSelection();

            updateWarehouseGrid();

        } catch (NumberFormatException e) {
            showErrorAlert("Ugyldig fad størrelse");
        }
    }

    /**
     * Viser en fejlmeddelelse i en Alert-dialog.
     *
     * @param message Fejlmeddelelsen der skal vises.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Opretter et GridPane, der repræsenterer lageret med fadpositioner.
     *
     * @return Et GridPane med knapper, der repræsenterer fadpositioner.
     */
    private GridPane createWarehouseGrid() {
        GridPane grid = new GridPane();

        // Tjekker, om der er valgt et lager
        if (!(this.lagerChoice == null)) {
            int totalSpots = this.lagerChoice.getAntalPladser();
            int COLUMNS = 10;

            // Opretter rækker og kolonner i GridPane
            for (int row = 0; row < Math.ceil(1.0 * totalSpots / COLUMNS); row++) {
                for (int column = 0; column < COLUMNS; column++) {
                    int position = row * COLUMNS + column + 1;

                    // Hvis positionen er inden for det valgte lagers kapacitet
                    if (position <= totalSpots) {
                        Button cell = new Button();
                        cell.setMinSize(20, 20);
                        cell.setMaxSize(20, 20);

                        cell.setText(String.valueOf(position));
                        cell.setFont(new Font(7));
                        cell.setStyle("-fx-base: #008000;");

                        // Opdaterer det valgte position og viser det i et label
                        cell.setOnAction(event -> {
                            lblActualPosition.setText(String.valueOf(position));
                            selectedPosition = position;
                        });

                        // Hvis positionen er optaget, deaktiveres knappen og farven ændres
                        if (isHashMapOccupied(this.lagerChoice, position)) {
                            cell.setDisable(true);
                            cell.setStyle("-fx-base: #FF6347;");
                        }

                        // Tilføjer knappen til GridPane
                        grid.add(cell, column, row);
                    }
                }
            }
        }
        grid.setHgap(5);
        grid.setVgap(5);
        return grid;
    }

    /**
     * Tjekker, om en bestemt lagerposition er optaget af et fad.
     *
     * @param lager    Lageret, der skal kontrolleres.
     * @param position Positionen, der skal kontrolleres.
     * @return True, hvis positionen er optaget, ellers false.
     */
    private boolean isHashMapOccupied(Lager lager, int position) {
        HashMap<Integer, Fad> valgtHashMap = controller.getFadeIHashMap(lager.getId());
        for (int i = 0; i < valgtHashMap.size(); i++) {
            if (valgtHashMap.get(i) != null && i == position) {
                return true;
            }
        }
        return false;
    }

    /**
     * Opdaterer kontrolelementerne med de nyeste lagerdata.
     */
    public void updateControls() {
        comboBoxLager.getItems().clear();
        comboBoxLager.getItems().addAll(controller.getAlleLagre());
    }

    /**
     * Opdaterer lagerets GridPane med de nyeste data.
     */
    private void updateWarehouseGrid() {
        this.getChildren().remove(warehouseGrid);
        this.warehouseGrid.getChildren().clear();
        GridPane newWarehouseGrid = createWarehouseGrid();
        this.add(newWarehouseGrid, 3, 3);
        this.warehouseGrid = newWarehouseGrid;
    }
}