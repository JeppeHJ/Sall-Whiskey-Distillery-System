package gui.views.paafyldningPane;

import application.Distillat;
import application.Fad;
import application.Lager;
import controller.Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PaafyldningController {
    @FXML
    private ComboBox<Distillat> comboBoxDistillat;
    @FXML
    private ComboBox<Lager> comboBoxLager;
    @FXML
    private TextField txtxLiter;
    @FXML
    private RadioButton rbSingleFad, rbMultipleFad;
    @FXML
    private ListView<Fad> listFade;
    @FXML
    private TextArea txtDistillatInfo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnPaafyld;

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final Controller controller = Controller.getController();
    private LocalDate selectedDate;

    public void initialize() {
        configureComboBoxes();
        configureTextFields();
        configureRadioButtons();
        configureFadeList();
        configureDatePicker();
        configureButtons();
    }

    private void configureComboBoxes() {
        comboBoxLager.getItems().addAll(controller.getAlleLagre());

        comboBoxLager.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            listFade.getSelectionModel().clearSelection();
            if (newSelection != null) {
                HashMap<Integer, Fad> fadeIHashMap = controller.getFadeIHashMap(newSelection.getId());
                listFade.getItems().setAll(fadeIHashMap.values());
            } else {
                listFade.getItems().clear();
            }
        });

        comboBoxDistillat.getItems().addAll(controller.getDistillaterMedFaktiskVæske());
    }

    private void configureTextFields() {
        txtDistillatInfo.setEditable(false);
        txtDistillatInfo.setText("Her er info om distillatet");
        txtDistillatInfo.setMaxWidth(200);
    }

    private void configureRadioButtons() {
        rbSingleFad.setToggleGroup(toggleGroup);
        rbMultipleFad.setToggleGroup(toggleGroup);

        rbSingleFad.setSelected(true); // Select rbSingleFad by default

        rbSingleFad.setOnAction(event -> listFade.getSelectionModel().setSelectionMode(SelectionMode.SINGLE));
        rbMultipleFad.setOnAction(event -> listFade.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE));
    }


    private void configureFadeList() {
        listFade.setEditable(false);
        Lager valgtLager = comboBoxLager.getValue();
        if (valgtLager != null) {
            HashMap<Integer, Fad> fadeIHashMap = controller.getFadeIHashMap(valgtLager.getId());
            listFade.getItems().setAll(fadeIHashMap.values());
        }
        listFade.setMaxHeight(100);
    }

    private void configureDatePicker() {
        datePicker.setOnAction(event -> selectedDate = datePicker.getValue());
    }

    private void configureButtons() {
        btnPaafyld.setOnAction(event -> btnPaafyld());
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void btnPaafyld() {
        Distillat distillat = comboBoxDistillat.getValue();
        if (distillat == null) {
            showErrorAlert("Vælg et distillat.");
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
        configureTextFields();
        configureFadeList();
    }
}


