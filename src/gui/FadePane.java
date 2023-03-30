package gui;

import application.Fad;
import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class FadePane extends GridPane {
    private final Controller controller = Controller.getController();
    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private final GridPane warehouseGrid = createWarehouseGrid();
    private final ListView<Fad> listFade = new ListView<>();
    private final TextField txtxFadtype = new TextField();
    private final TextField txtfadStr = new TextField();
    private final ComboBox<Lager> comboBoxLager = new ComboBox<>();

    public FadePane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //----------------------------------------------ComboBox
        comboBoxLager.getItems().addAll(controller.getAlleLagre());
        this.add(comboBoxLager, 3, 0);

        comboBoxLager.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            listFade.getSelectionModel().clearSelection();
            if (newSelection != null) {
                listFade.getItems().setAll(controller.getFadeILager(newSelection.getId()));
            } else {
                listFade.getItems().clear();
            }
        });

        //-----------------------------------------------Fade liste
        this.add(listFade, 1, 3);
        listFade.setEditable(false);
        listFade.setMinHeight(200);

        //------------------------------------------------- Labels
        Label lblfadStr = new Label("Fad størrelse");
        this.add(lblfadStr, 0, 0);
        Label lblfadPosition = new Label("Vælg position");
        this.add(lblfadPosition, 3, 2);
        Label lblfadType = new Label("Fad Type");
        this.add(lblfadType, 0, 1);
        Label lblvealglager = new Label("Vælg lager:");
        this.add(lblvealglager, 2, 0);
        Label lbltotalfad = new Label("Total fade: " + controller.totalAntalFad());
        this.add(lbltotalfad, 0, 7);
        Label lblPosition = new Label("Position");
        this.add(lblPosition,0,2);


        //------------------------------------------------button
        Button btnopretfad = new Button("Opret fad");
        this.add(btnopretfad, 3, 1);
        btnopretfad.setOnAction(event -> btnOpretAction());

        //-------------------------------------------------text
        this.add(txtfadStr, 1, 0);
        this.add(txtxFadtype, 1, 1);

        this.add(warehouseGrid, 3,3);
    }

    private void btnOpretAction() {
        int plads = 1;
        Lager lager = comboBoxLager.getValue();
        String fadtype = txtxFadtype.getText().trim();

        if (lager == null || fadtype.isEmpty() || txtfadStr.getText().trim().isEmpty()) {
            showErrorAlert("Udfyld venligst alle felter");
            return;
        }

        try {
            int fadStr = Integer.parseInt(txtfadStr.getText().trim());
            controller.opretFad(fadtype, fadStr, lager, plads);
            txtfadStr.clear();
            txtxFadtype.clear();
            comboBoxLager.getSelectionModel().clearSelection();
            this.updateControls();
        } catch (NumberFormatException e) {
            showErrorAlert("Ugyldig fad størrelse");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private GridPane createWarehouseGrid() {
        GridPane grid = new GridPane();
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Button cell = new Button();
                cell.setMinSize(20, 20);
                cell.setMaxSize(20, 20);
                int position = row * COLUMNS + column + 1;
                cell.setText(String.valueOf(position));
                cell.setFont(new Font(7));
                cell.setOnAction(event -> {
                    txtfadStr.setText(String.valueOf(position));
                });
                grid.add(cell, column, row);
            }}
        grid.setHgap(5);
        grid.setVgap(5);
        return grid;
    }



    public void updateControls() {
        comboBoxLager.getItems().clear();
        comboBoxLager.getItems().addAll(controller.getAlleLagre());
    }
}

