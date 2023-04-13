package gui.views.fadePane;


import application.*;
import controller.Controller;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FadePaneController {

    @FXML
    private ComboBox<Lager> comboBoxLager;
    @FXML
    private TextField txtxFadtype;
    @FXML
    private TextField txtfadStr;
    @FXML
    private Label lblActualPosition;
    @FXML
    private ListView<LagretVæske> lstNuværendeVæske;
    @FXML
    private ListView<Fad> listFade;
    @FXML
    private Canvas warehouseGrid;

    private int selectedPosition = 0;
    private Lager lagerChoice;
    private final Controller controller = Controller.getController();


    public void initialize() {
        // Add the listeners and event handlers from the original code
        comboBoxLager.getItems().addAll(controller.getAlleLagre());

        if (!comboBoxLager.getItems().isEmpty()) {
            comboBoxLager.setValue(comboBoxLager.getItems().get(0));
            handleLagerSelection();
        }

        comboBoxLager.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            handleLagerSelection();
        });
    }

    private void handleLagerSelection() {
        listFade.getSelectionModel().clearSelection();
        if (comboBoxLager.getValue() != null) {
            listFade.getItems().setAll(controller.getFadeIHashMap(comboBoxLager.getValue().getId()).values());
            this.lagerChoice = comboBoxLager.getValue();
            drawWarehouseGrid(warehouseGrid);
        } else {
            listFade.getItems().clear();
        }
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
    @FXML
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

            drawWarehouseGrid(warehouseGrid);

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

    private void drawWarehouseGrid(Canvas canvas) {
        int totalSpots = this.lagerChoice.getAntalPladser();
        final int COLUMNS = 10;
        final int cellSize = 20;
        final int padding = 5;
        int gridWidth = COLUMNS * (cellSize + padding) + padding;
        int gridHeight = (int) Math.ceil(1.0 * totalSpots / COLUMNS) * (cellSize + padding) + padding;

        canvas.setWidth(gridWidth);
        canvas.setHeight(gridHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(new Font("SansSerif", 10));

        int numRows = (int) Math.ceil(1.0 * totalSpots / COLUMNS);
        for (int row = 0; row < numRows; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                int position = row * COLUMNS + column + 1;

                if (position <= totalSpots) {
                    double x = column * (cellSize + padding) + padding;
                    double y = row * (cellSize + padding) + padding;

                    gc.setFill(isHashMapOccupied(this.lagerChoice, position) ? Color.valueOf("#FF6347") : Color.valueOf("#008000"));
                    gc.fillRoundRect(x, y, cellSize, cellSize, 10, 10);
                    gc.setFill(Color.WHITE);

                    // Draw position number in the middle of the cell
                    Text positionText = new Text(Integer.toString(position));
                    positionText.setFont(gc.getFont());
                    double textWidth = positionText.getLayoutBounds().getWidth();
                    gc.fillText(Integer.toString(position), x + (cellSize - textWidth) / 2, y + cellSize / 2 + 4);
                }
            }
        }
        canvas.setOnMouseClicked(event -> {
            int column = (int) (event.getX() / (cellSize + padding));
            int row = (int) (event.getY() / (cellSize + padding));
            int position = row * COLUMNS + column + 1;

            if (position <= totalSpots && !isHashMapOccupied(this.lagerChoice, position)) {
                lblActualPosition.setText(String.valueOf(position));
                selectedPosition = position;
            }
        });
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
        LocalDate date = (LocalDate) inputData.get("Date");

        controller.opretNyLagretVæskeOmhældning(fadMedVæske, fadDestination, Double.parseDouble(mængde), date);

        // Process the data as needed
    }

}

