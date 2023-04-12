package gui;

import application.*;
import controller.Controller;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;

/**
 * FadePane klassen repræsenterer en JavaFX-pane, der tillader brugeren at oprette
 * og se nye fade, vælge et lager og se de forskellige positioner i lageret.
 */
public class FadePane extends GridPane {
    private final Controller controller = Controller.getController();
private final Button btnNuværendeIndholdDetaljer = new Button("Detaljer");
    private int selectedPosition;
    private final Button btnIndholdsHistorikDetaljer = new Button("Detaljer LOL");
    private GridPane warehouseGrid;
    private final ListView<Fad> listFade = new ListView<>();
    // Add a new ListView for FadOmhældningsHistorik
    private final ListView<FadsOmhældningsHistorik> lstFadOmhældningsHistorik = new ListView<>();
    private final TextField txtxFadtype = new TextField();
    private final TextField txtfadStr = new TextField();
    private final ComboBox<Lager> comboBoxLager = new ComboBox<>();
    private Label lblActualPosition = new Label();

    private final ListView<Distillat> lstDistillat = new ListView<>();
    private final ListView<FadsLagretVæskeHistorik> lstIndholdshistorik = new ListView<>();
    private final ListView<String> lstOmhældningshistorik = new ListView<>();
    private final ListView<LagretVæske> lstNuværendeVæske = new ListView<>();

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

        this.add(lstFadOmhældningsHistorik, 3, 4);
        lstFadOmhældningsHistorik.setEditable(false);
        lstFadOmhældningsHistorik.setMaxHeight(50);

        // Tilføjer og konfigurerer fade-liste og andre lister
        this.add(listFade, 1, 3);
        listFade.setEditable(false);
        listFade.setMaxHeight(50);

        this.add(lstNuværendeVæske, 1, 4);
        lstNuværendeVæske.setEditable(false);
        lstNuværendeVæske.setMaxHeight(50);

        this.add(lstDistillat, 1, 6);
        lstDistillat.setEditable(false);
        lstDistillat.setMaxHeight(50);

        this.add(lstIndholdshistorik, 1, 5);
        lstIndholdshistorik.setEditable(false);
        lstIndholdshistorik.setMaxHeight(50);

        // Tilføjer og konfigurerer etiketter
        this.add(new Label("Fad størrelse"), 0, 0);
        this.add(new Label("Vælg position"), 3, 2);
        this.add(new Label("Fad Type"), 0, 1);
        this.add(new Label("Vælg lager:"), 2, 0);
        this.add(new Label("Total fade: " + controller.totalAntalFad()), 0, 7);
        this.add(new Label("Position"), 0, 2);
        this.add(new Label("------------Nuværende indhold"), 0, 4);
        this.add(new Label("Omhældningshistorik"), 2, 4);
        this.add(new Label("-----------Indholdshistorik"), 0, 5);
        this.add(new Label("-----------Distillat(er)"), 0, 6);

        this.add(lblActualPosition, 1, 2);

        // Opdaterer listerne baseret på det valgte fad
        listFade.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ArrayList<Distillat> distillatList = new ArrayList<>();
                for (LagretVæske lV : newSelection.getLagretVæsker()) {
                    distillatList.addAll(lV.getDistillater());
                }

                lstNuværendeVæske.getItems().setAll(newSelection.getLagretVæsker());

                lstDistillat.getItems().setAll(distillatList);



                // Update the FadOmhældningsHistorik ListView based on the chosen Fad
                lstFadOmhældningsHistorik.getItems().setAll(newSelection.getOmhældningsHistory());

                // Populate lstIndholdshistorik with FadsLagretVæskeHistorik objects associated with the selected Fad
                lstIndholdshistorik.getItems().setAll(newSelection.getFadsLagretVæskeHistorik());
                if (!(newSelection.getFadsLagretVæskeHistorik().isEmpty())) {
                    this.btnIndholdsHistorikDetaljer.setDisable(false);
                }
            } else {
                lstDistillat.getItems().clear();
                lstIndholdshistorik.getItems().clear();
                lstOmhældningshistorik.getItems().clear();
                lstFadOmhældningsHistorik.getItems().clear();
                btnNuværendeIndholdDetaljer.setDisable(true);
            }
        });

        lstNuværendeVæske.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    btnNuværendeIndholdDetaljer.setDisable(false);
                });


        // Tilføjer og konfigurerer knappen "Opret fad"
        Button btnopretfad = new Button("Opret fad");
        this.add(btnopretfad, 3, 1);
        btnopretfad.setOnAction(event -> btnOpretAction());

        // Tilføjer og konfigurerer knappen "Opret fad"

        this.add(btnNuværendeIndholdDetaljer, 0, 4);
        btnNuværendeIndholdDetaljer.setDisable(true);
        btnNuværendeIndholdDetaljer.setOnAction(event -> openNewWindowWithTableView());


        this.add(btnIndholdsHistorikDetaljer, 0, 5);
        btnIndholdsHistorikDetaljer.setDisable(true);
        btnIndholdsHistorikDetaljer.setOnAction(event -> openIndholdshistorikDetaljeWindow());

        Button btnDistillatDetaljer = new Button("Detaljer");
        this.add(btnDistillatDetaljer, 0, 6);
        btnDistillatDetaljer.setDisable(true);
        btnopretfad.setOnAction(event -> btnOpretAction());

        Button omhældningsDetaljer = new Button("Omhældningsdetaljer");
        Button btnInputExtraData = new Button("Opret Omhældning");
        this.add(omhældningsDetaljer, 3, 5);
        this.add(btnInputExtraData, 3, 6); // Adjust the position as needed
        btnInputExtraData.setOnAction(event -> showCustomDialog());

        // Tilføjer og konfigurerer tekstfelter
        this.add(txtfadStr, 1, 0);
        this.add(txtxFadtype, 1, 1);

        // Opretter og tilføjer warehouseGrid
        warehouseGrid = createWarehouseGrid();
        this.add(warehouseGrid, 3, 3);
    }

    private void openNewWindowWithTableView() {
        TableView<LagretVæske> tableView = new TableView<>();

        // Define columns
        TableColumn<LagretVæske, Integer> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<LagretVæske, Double> column2 = new TableColumn<>("Alkoholprocent");
        column2.setCellValueFactory(new PropertyValueFactory<>("alkoholProcent"));
        TableColumn<LagretVæske, LocalDate> column3 = new TableColumn<>("Påfyldningsdato");
        column3.setCellValueFactory(new PropertyValueFactory<>("påfyldningsDato"));
        TableColumn<LagretVæske, ArrayList<Distillat>> column4 = new TableColumn<>("Distillater");
        column4.setCellValueFactory(new PropertyValueFactory<>("distillater"));
        TableColumn<LagretVæske, HashMap<Fad, LocalDate>> column5 = new TableColumn<>("Fadhistorik");
        column5.setCellValueFactory(new PropertyValueFactory<>("fadehistorik"));
        TableColumn<LagretVæske, HashMap<Fad, LocalDate>> column6 = new TableColumn<>("Fad");
        column5.setCellValueFactory(new PropertyValueFactory<>("fadehistorik"));

        // Add columns to the TableView
        tableView.getColumns().addAll(column1, column2, column3, column4, column5);

        LagretVæske selectedLagretVæske = lstNuværendeVæske.getSelectionModel().getSelectedItem();

        // Add the selectedLagretVæske to the TableView
        tableView.getItems().add(selectedLagretVæske);

        Stage newWindow = new Stage();
        newWindow.setTitle("Detaljer");
        newWindow.initModality(Modality.WINDOW_MODAL);
        Scene newWindowScene = new Scene(tableView, 800, 600);
        newWindow.setScene(newWindowScene);
        newWindow.show();
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

    private void openIndholdshistorikDetaljeWindow() {
        TableView<FadsLagretVæskeHistorik> tableView = new TableView<>();
        Fad actualSelectedFad = listFade.getSelectionModel().getSelectedItem();

        // Define columns
        TableColumn<FadsLagretVæskeHistorik, Integer> column1 = new TableColumn<>("LagretVæske ID");
        column1.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getLagretVæskeId()).asObject());
        TableColumn<FadsLagretVæskeHistorik, LocalDate> column2 = new TableColumn<>("Påfyldningsdato");
        column2.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFillDate()));
        TableColumn<FadsLagretVæskeHistorik, LocalDate> column3 = new TableColumn<>("TømtDato");
        column3.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmptyDate()));
        TableColumn<FadsLagretVæskeHistorik, String> column4 = new TableColumn<>("LagretVæskesDestillater");
        column4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDistillater().toString()));
        TableColumn<FadsLagretVæskeHistorik, String> column5 = new TableColumn<>("LagretVæskesFadHistorik");
        column5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getHistorik().toString()));

        // Add columns to the TableView
        tableView.getColumns().addAll(column1, column2, column3, column4, column5);

        for (int i = 1; i < actualSelectedFad.getFadsLagretVæskeHistorik().size(); i++) {
            tableView.getItems().add(actualSelectedFad.getFadsLagretVæskeHistorik().get(i));
        }

        FadsLagretVæskeHistorik selectedFad = listFade.getSelectionModel().getSelectedItem().getFadsLagretVæskeHistorik().get(0);

        // Add the selectedLagretVæske to the TableView

        tableView.getItems().add(selectedFad);

        Stage newWindow = new Stage();
        newWindow.setTitle("Indholdshistorik Detalje");
        newWindow.initModality(Modality.WINDOW_MODAL);
        Scene newWindowScene = new Scene(tableView, 800, 600);
        newWindow.setScene(newWindowScene);
        newWindow.show();
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

    private void showCustomDialog() {
        // Create a custom dialog
        Dialog<Map<String, Object>> customDialog = new Dialog<>();
        customDialog.setTitle("Custom Dialog");
        customDialog.setHeaderText("Enter the data:");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        customDialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the input fields and labels
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();
        ComboBox<Fad> comboBoxFadMedVæske = new ComboBox<>();
        ComboBox<Fad> comboBoxFadDestination = new ComboBox<>();
        grid.add(comboBoxFadMedVæske, 1, 0);
        grid.add(comboBoxFadDestination, 1, 1);

        grid.add(new Label("Vælg Fad med væske:"), 0, 0);
        grid.add(new Label("Fad Destination:"), 0, 1);
        grid.add(new Label("Mængde:"), 2, 0);
        grid.add(textField3, 2, 1);

        // Populate the ComboBoxes
        comboBoxFadMedVæske.getItems().addAll(controller.getAlleFadDerKanOmhældesFra());

        // Add a listener to update comboBoxFadDestination whenever comboBoxFadMedVæske's selected item changes
        comboBoxFadMedVæske.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            comboBoxFadDestination.getItems().clear();
            if (newSelection != null) {
                comboBoxFadDestination.getItems().addAll(controller.getAlleFadDerKanOmhældesTil(newSelection));
            } else {
                comboBoxFadDestination.getItems().addAll(controller.getAlleFadDerKanOmhældesTil(newSelection));
            }
        });

        customDialog.getDialogPane().setContent(grid);

        // Request focus on the first field by default
        Platform.runLater(() -> textField1.requestFocus());

        // Convert the result to a map of input data when the Save button is clicked
        customDialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                HashMap<String, Object> inputData = new HashMap<>();
                inputData.put("FadMedVæske", comboBoxFadMedVæske.getValue());
                inputData.put("FadDestination", comboBoxFadDestination.getValue());
                inputData.put("Mængde", textField3.getText());
                return inputData;
            }
            return null;
        });

        // Show the dialog and process the result
        Optional<Map<String, Object>> result = customDialog.showAndWait();
        result.ifPresent(inputData -> processData(inputData));

        comboBoxFadMedVæske.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Save the object chosen in the comboBoxFadMedVæske
        });

    }

    private void processData(Map<String, Object> inputData) {
        Fad fadMedVæske = (Fad) inputData.get("FadMedVæske");
        Fad fadDestination = (Fad) inputData.get("FadDestination");
        String mængde = (String) inputData.get("Mængde");

        controller.opretNyLagretVæskeOmhældning(fadMedVæske, fadDestination, Double.parseDouble(mængde));

        // Process the data as needed
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