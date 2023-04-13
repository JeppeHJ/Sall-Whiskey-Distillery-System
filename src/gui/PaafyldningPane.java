package gui;

import application.Distillat;
import application.Fad;
import application.Lager;
import controller.Controller;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PaafyldningPane extends GridPane {
    private final Controller controller = Controller.getController();

    private final ListView<Fad> listFade = new ListView<>();
    private final RadioButton rbSingleFad = new RadioButton("Fyld på specifikt fad");
    private final RadioButton rbMultipleFad = new RadioButton("Fyld på flere fade");
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private LocalDate selectedDate;

    private final TextField txtxLiter = new TextField();
    private Lager valgtLager;
    private final DatePicker datePicker = new DatePicker();
    private final TextArea txtDistillatInfo = new TextArea();
    private final Label lblLiter = new Label("Antal Liter");
    private final Label lblVaelgDistillat = new Label("Vælg Distillat");
    private final Label lblvealglager = new Label("Vælg lager:");
    private final Label lblError = new Label();

    private final ComboBox<Lager> comboBoxLager = new ComboBox<>();
    private final ComboBox<Distillat> comboBoxDistillat = new ComboBox<>();
    private final Button btnPaafyld = new Button("Fyld på");

    public PaafyldningPane() {
        configurePane();
        configureComboBoxes();
        configureTextFields();
        configureRadioButtons();
        configureFadeList();
        configureLabels();
        configureDatePicker();
        configureButtons();
    }

    private void configurePane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
    }

    private void configureComboBoxes() {
        comboBoxLager.getItems().addAll(controller.getAlleLagre());
        this.add(comboBoxLager, 2, 1);

        comboBoxLager.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            listFade.getSelectionModel().clearSelection();
            if (newSelection != null) {
                HashMap<Integer, Fad> fadeIHashMap = controller.getFadeIHashMap(newSelection.getId());
                listFade.getItems().setAll(fadeIHashMap.values());
            } else {
                listFade.getItems().clear();
            }
        });

        comboBoxDistillat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        txtDistillatInfo.setText(
                                "Maltbatch: " + newValue.getMaltBatch() + "\n" +
                                        "Kornsort: " + newValue.getKornsort() + "\n" +
                                        "Alkoholprocent: " + newValue.getAlkoholprocent() + "\n" +
                                        "Rygemateriale: " + newValue.getRygemateriale() + "\n" +
                                        "Færdig Dato: " + newValue.getDatoForDone() + "\n" +
                                        "Medarbejder: " + newValue.getMedarbejder()
                        );
                    }
                }
        );

        comboBoxDistillat.getItems().addAll(controller.getDistillaterMedActualVaeske());
        this.add(comboBoxDistillat, 0, 1);
    }

    private void configureTextFields() {
        this.add(txtDistillatInfo, 0, 2);
        txtDistillatInfo.setEditable(false);
        txtDistillatInfo.setText("Her er info om distillatet");
        txtDistillatInfo.setMaxWidth(200);

        this.add(txtxLiter, 1, 1);
    }

    private void configureRadioButtons() {
        rbSingleFad.setToggleGroup(toggleGroup);
        rbMultipleFad.setToggleGroup(toggleGroup);

        this.add(rbSingleFad, 1, 2);
        this.add(rbMultipleFad, 1, 3);

        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == rbSingleFad) {
                listFade.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            } else if (newToggle == rbMultipleFad) {
                listFade.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            }
        });
    }

    private void configureFadeList() {
        listFade.setCellFactory(lv -> new ListCell<Fad>() {
            @Override
            protected void updateItem(Fad item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getPlads() + " | Fad-ID: " + item.getId() + " | Status: " + item.getFadfyldning() + " / " + item.getFadStr());
                }
            }
        });
        this.add(listFade, 2, 2);
        listFade.setEditable(false);
        valgtLager = comboBoxLager.getValue();
        if (valgtLager != null) {
            HashMap<Integer, Fad> fadeIHashMap = controller.getFadeIHashMap(valgtLager.getId());
            listFade.getItems().setAll(fadeIHashMap.values());
        }
        listFade.setMaxHeight(100);
    }

    private void configureLabels() {
        this.add(lblVaelgDistillat, 0, 0);
        this.add(lblLiter, 1, 0);
        this.add(lblvealglager, 2, 0);
        this.add(lblError, 1, 5);
    }

    private void configureDatePicker() {
        this.add(datePicker, 1, 4);
        datePicker.setOnAction(event -> selectedDate = datePicker.getValue());
    }

    private void configureButtons() {
        this.add(btnPaafyld, 2, 4);
        btnPaafyld.setOnAction(event -> btnPaafyld());
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void btnPaafyld() {
        Distillat distillat = comboBoxDistillat.getValue();
        if (distillat == null) {
            showErrorAlert("Vælg et distillat.");
            return;
        }

        if (toggleGroup.getSelectedToggle() == null) {
            showErrorAlert("Vælg en påfyldningsmetode (specifikt fad eller flere fade).");
            return;
        }

        String antalLiterText = txtxLiter.getText().trim();
        int antalLiter;
        try {
            antalLiter = Integer.parseInt(antalLiterText);
            if (antalLiter <= 0) {
                showErrorAlert("Antal liter skal være større end 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Indtast et gyldigt antal liter.");
            return;
        }

        LocalDate dato = selectedDate;
        if (dato == null) {
            showErrorAlert("Vælg en dato.");
            return;
        }

        if (listFade.getSelectionModel().getSelectionMode() == SelectionMode.SINGLE) {
            Fad selectedItem = listFade.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    controller.fyldPåSpecifiktFad(antalLiter, dato, selectedItem, distillat);
                } catch (RuntimeException e) {
                    showErrorAlert(e.getMessage());
                    return;
                }
            } else {
                showErrorAlert("Vælg et fad.");
                return;
            }
        } else if (listFade.getSelectionModel().getSelectionMode() == SelectionMode.MULTIPLE) {
            ObservableList<Fad> selectedItems = listFade.getSelectionModel().getSelectedItems();
            ArrayList<Fad> valgteFade = new ArrayList<>(selectedItems);
            if (!selectedItems.isEmpty()) {
                try {
                    controller.fyldPåFlereFade(antalLiter, dato, valgteFade, distillat);
                } catch (RuntimeException e) {
                    showErrorAlert(e.getMessage());
                    return;
                }
            } else {
                showErrorAlert("Vælg mindst et fad.");
                return;
            }
        }
        updateControls();
    }

    public void updateControls() {
        comboBoxLager.getItems().clear();
        comboBoxLager.getItems().addAll(controller.getAlleLagre());
        comboBoxDistillat.getItems().clear();
        comboBoxDistillat.getItems().addAll(controller.getDistillaterMedActualVaeske());
        if (this.valgtLager != null) {
            System.out.println("gej");
            listFade.getItems().setAll(controller.getFadeIHashMap(valgtLager.getId()).values());
        }


    }

}

