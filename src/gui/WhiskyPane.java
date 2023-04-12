package gui;

import application.Fad;
import application.LagretVæskesFadHistorik;
import application.Whisky;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.List;

public class WhiskyPane extends GridPane {
    private final Controller controller = Controller.getController();
    private final ComboBox<Whisky> lstFaerdigeVaesker = new ComboBox<>(); // Updated to Whisky
    private final TextArea txtAreaInfo = new TextArea();
    private final TextArea txtAreaDistillat = new TextArea();
    private final ListView<LagretVæskesFadHistorik> lstBarrels = new ListView<>();

    public WhiskyPane() {
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

        this.add(txtAreaDistillat,0,4);
        txtAreaDistillat.setEditable(false);
        txtAreaDistillat.setMinHeight(200);
        txtAreaDistillat.setMaxHeight(100);
        txtAreaDistillat.setMaxWidth(250);


        // labels
        Label lblDistillatList = new Label("Whiskyflasker:"); // Updated label text
        this.add(lblDistillatList, 0, 0);

        // ListView for barrels
        this.add(lstBarrels, 0, 3);
        lstBarrels.setEditable(false);
        lstBarrels.setMinHeight(200);
        lstBarrels.setMaxHeight(100);
        lstBarrels.setMaxWidth(250);

        lstFaerdigeVaesker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Display information about the selected
                txtAreaInfo.setText("Navn: " + newValue.getNavn() + "\n" +
                        "TappetDato: " + newValue.getTappetDato() + "\n" +
                        "Alkoholprocent: " + newValue.getAlkoholProcent() + "\n" +
                        "Liter: " + newValue.getLiter() + "\n" +
                        "Flasketype: " + newValue.getFlasketype()
                );

                List<LagretVæskesFadHistorik> fadHistory = controller.getFadHistoryForWhisky(newValue);
                lstBarrels.getItems().setAll(fadHistory);
            } else {
                txtAreaInfo.clear();
                lstBarrels.getItems().clear();
            }
        });

        lstBarrels.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Fad fad = newValue.getFad();
                List<LagretVæskesFadHistorik> fadHistory = controller.getFadHistoryForWhisky(lstFaerdigeVaesker.getValue());
                StringBuilder historyText = new StringBuilder("History for Fad ID: " + fad.getId() + "\n\n");

                for (LagretVæskesFadHistorik entry : fadHistory) {
                    historyText.append("Påhældning: ").append(entry.getFraDato().toString()).append("\n");
                }

                txtAreaDistillat.setText(historyText.toString());
            } else {
                txtAreaDistillat.clear();
            }
        });

        lstBarrels.setCellFactory(new Callback<>() {
            @Override
            public ListCell<LagretVæskesFadHistorik> call(ListView<LagretVæskesFadHistorik> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(LagretVæskesFadHistorik item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText("Fad ID: " + item.getFad().getId() + " | Påhældning: " + item.getFraDato().toString() + "-");
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