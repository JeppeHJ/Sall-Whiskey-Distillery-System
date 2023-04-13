package gui;

import application.*;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class ProduktPane extends GridPane {
    private final Controller controller = Controller.getController();
    private final ComboBox<Whisky> lstFaerdigeVaesker = new ComboBox<>(); // Updated to Whisky
    private final TextArea txtAreaInfo = new TextArea();
    private final TextArea txtAreaDistillat = new TextArea();

    private final ListView<Distillat> listWhiskyDestillatListe = new ListView<>();
    private final TextArea txtAreaDistillatInfo = new TextArea(); // Add the new TextArea
    private final ListView<LagretVæskesFadHistorik> listLagretsVæskesFadHistorik = new ListView<>();
    private final ListView<FadsLagretVæskeHistorik> listFadsLagretVæskeHistorik = new ListView<>();

    public ProduktPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // list
        this.add(lstFaerdigeVaesker, 0, 1);
        this.lstFaerdigeVaesker.setEditable(false);
        this.lstFaerdigeVaesker.getItems().setAll(controller.getWhisky()); // Updated method

        // TextArea
        this.add(txtAreaInfo, 0, 2); // Added TextArea to the layout
        txtAreaInfo.setMinHeight(200);
        txtAreaInfo.setMaxHeight(100);
        txtAreaInfo.setMaxWidth(250);
        txtAreaInfo.setEditable(false); // Make TextArea non-editable
        txtAreaInfo.setPrefRowCount(6); // Set preferred row count (adjust as needed)

        this.add(listFadsLagretVæskeHistorik, 1, 4);
        listFadsLagretVæskeHistorik.setEditable(false);
        listFadsLagretVæskeHistorik.setMinHeight(200);
        listFadsLagretVæskeHistorik.setMaxHeight(100);
        listFadsLagretVæskeHistorik.setMaxWidth(250);

        // New TextArea for Distillat Info
        this.add(txtAreaDistillatInfo, 2, 4);
        txtAreaDistillatInfo.setEditable(false);
        txtAreaDistillatInfo.setMinHeight(200);
        txtAreaDistillatInfo.setMaxHeight(100);
        txtAreaDistillatInfo.setMaxWidth(250);


        // labels
        Label lblDistillatList = new Label("Whiskyflasker:"); // Updated label text
        this.add(lblDistillatList, 0, 0);
        Label lblFadHistorik = new Label("Fad Historik:");
        this.add(lblFadHistorik, 1, 0);
        Label lblDestillatListe = new Label("Destillat Liste");
        this.add(lblDestillatListe, 2, 0);

        this.add(listWhiskyDestillatListe, 2, 2);
        listWhiskyDestillatListe.setEditable(false);
        listWhiskyDestillatListe.setMinHeight(200);
        listWhiskyDestillatListe.setMaxHeight(100);
        listWhiskyDestillatListe.setMaxWidth(250);

        // ListView for barrels
        this.add(listLagretsVæskesFadHistorik, 1, 2);
        listLagretsVæskesFadHistorik.setEditable(false);
        listLagretsVæskesFadHistorik.setMinHeight(200);
        listLagretsVæskesFadHistorik.setMaxHeight(100);
        listLagretsVæskesFadHistorik.setMaxWidth(250);

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

                txtAreaDistillat.setText(historyText.toString());

                // Populate lstIndholdshistorik with the selected Fad's history
                listFadsLagretVæskeHistorik.getItems().setAll(newValue.getFad().getFadsLagretVæskeHistorik());
            } else {
                txtAreaDistillat.clear();
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