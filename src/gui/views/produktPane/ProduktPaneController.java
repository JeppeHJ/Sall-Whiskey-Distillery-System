package gui.views.produktPane;

import application.*;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class ProduktPaneController {
    private final Controller controller = Controller.getController();

    @FXML
    private ComboBox<Whisky> lstFaerdigeVaesker;
    @FXML
    private TextArea txtAreaInfo;
    @FXML
    private ListView<Distillat> listWhiskyDestillatListe;
    @FXML
    private TextArea txtAreaDistillatInfo;
    @FXML
    private ListView<LagretVæskesFadHistorik> listLagretsVæskesFadHistorik;
    @FXML
    private ListView<FadsLagretVæskeHistorik> listFadsLagretVæskeHistorik;

    public void initialize() {

        updateControls();

        lstFaerdigeVaesker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Display information about the selected
                txtAreaInfo.setText("Navn: " + newValue.getNavn() + "\n" +
                        "ID: " + newValue.getId() + "\n" +
                        "TappetDato: " + newValue.getTappetDato() + "\n" +
                        "Alkoholprocent: " + newValue.getAlkoholProcent() + "\n" +
                        "Liter: " + newValue.getLiter() + "\n" +
                        "Flasketype: " + newValue.getFlasketype() + "\n" +
                        "Fortyndelsesprocent: " + newValue.getFortyndelsesProcent() + "\n" +
                        "Vandkilde: " + newValue.getVandKilde()
                );

                List<LagretVæskesFadHistorik> fadHistory = controller.getFadHistoryForWhisky(newValue);
                listLagretsVæskesFadHistorik.getItems().setAll(fadHistory);

                ArrayList<Distillat> distillatList = new ArrayList<>();
                for (LagretVæske lV : newValue.getHistorik()) {
                    distillatList.addAll(lV.getDistillater());
                }

                listWhiskyDestillatListe.getItems().setAll(distillatList);

            } else {
                txtAreaInfo.clear();
                listLagretsVæskesFadHistorik.getItems().clear();
            }
        });

        // Add listener to the Distillat ListView
        listWhiskyDestillatListe.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtAreaDistillatInfo.setText(
                        "Maltbatch: " + newValue.getMaltBatch() + "\n" +
                                "Kornsort: " + newValue.getKornsort() + "\n" +
                                "Alkoholprocent: " + newValue.getAlkoholprocent() + "\n" +
                                "Rygemateriale: " + newValue.getRygemateriale() + "\n" +
                                "Færdig Dato: " + newValue.getDatoForDone() + "\n" +
                                "Medarbejder: " + newValue.getMedarbejder()
                );
            } else {
                txtAreaDistillatInfo.clear();
            }
        });

        listLagretsVæskesFadHistorik.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Fad fad = newValue.getFad();
                List<LagretVæskesFadHistorik> fadHistory = controller.getFadHistoryForWhisky(lstFaerdigeVaesker.getValue());
                StringBuilder historyText = new StringBuilder("History for Fad ID: " + fad.getId() + "\n\n");

                for (LagretVæskesFadHistorik entry : fadHistory) {
                    historyText.append("Påhældning: ").append(entry.getFraDato().toString()).append("\n");
                }
                // Populate lstIndholdshistorik with the selected Fad's history
                listFadsLagretVæskeHistorik.getItems().setAll(newValue.getFad().getFadsLagretVæskeHistorik());
            } else {
                listFadsLagretVæskeHistorik.getItems().clear(); // Clear the new ListView if no item is selected
            }
        });

        listLagretsVæskesFadHistorik.setCellFactory(new Callback<>() {
            @Override
            public ListCell<LagretVæskesFadHistorik> call(ListView<LagretVæskesFadHistorik> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(LagretVæskesFadHistorik item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText("Fad ID: " + item.getFad().getId() + " | " + item.getFraDato().toString() + " til " + item.getTilDato());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    public void updateControls() {
        lstFaerdigeVaesker.getItems().clear();
        lstFaerdigeVaesker.getItems().setAll(controller.getWhisky()); // Updated method
    }

}
