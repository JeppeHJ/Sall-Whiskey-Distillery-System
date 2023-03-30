package gui;

import application.Distillat;
import application.Fad;
import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

//TODO

public class PaafyldningPane extends GridPane {
    private Controller controller = Controller.getController();

    private ListView<Fad> listFade = new ListView<>();
    private ListView<Distillat> listDistillater = new ListView<>();

    private RadioButton rbSingleFad = new RadioButton("Fyld på specifikt fad");
    private RadioButton rbMultipleFad = new RadioButton("Fyld på flere fade");

    private ToggleGroup toggleGroup = new ToggleGroup();
    private LocalDate selectedDate;

    private TextField txtxLiter = new TextField();
    private Lager valgtLager;
    private DatePicker datePicker = new DatePicker();
    private TextField txtfadStr= new TextField();
    private TextArea txtDistillatInfo = new TextArea();
    private Label lblLiter= new Label("Antal Liter");
    private Label lblVaelgDistillat = new Label("Vælg Distillat");
    private Label lblvealglager = new Label("Vælg lager:");
    private Label lblError = new Label();

    private ComboBox comboBoxLager = new ComboBox<>();
    private ComboBox comboBoxDistillat = new ComboBox<>();
    private Button btnopretfad = new Button("Opret fad");

    public PaafyldningPane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //----------------------------------------------list
        this.comboBoxLager.getItems().addAll(controller.getAlleLagre());
        this.add( comboBoxLager, 2, 1);

        comboBoxLager.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            listFade.getSelectionModel().clearSelection(); // Clear the ListView selection
            Lager lager = (Lager) newSelection;
            if (lager != null) {
                listFade.getItems().setAll(controller.getFadeILager(lager.getId()));
            } else {
                listFade.getItems().clear(); // Clear the ListView if no Lager is selected
            }
        });

        //-----TXT fields
        this.add(txtDistillatInfo,0,2);
        txtDistillatInfo.setEditable(false);
        Distillat distillat = (Distillat)comboBoxDistillat.getValue();
        txtDistillatInfo.setText("Her er info om distillatet");

        this.txtDistillatInfo.setMaxWidth(200);

        //----- RADIO BUTTONS
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

        //-----------------------------------------------Fade liste
        this.add(listFade, 2, 2);
        this.listFade.setEditable(false);
        this.valgtLager = (Lager)comboBoxLager.getValue();
        if (valgtLager != null) {
            System.out.println(valgtLager.getId());
            this.listFade.getItems().setAll(controller.getFadeILager(valgtLager.getId()));
        }
        this.listFade.setMaxHeight(100);

        this.comboBoxDistillat.getItems().addAll(controller.getDistillaterMedActualVaeske());
        this.add( comboBoxDistillat, 0, 1);

        //------------------------------------------------- Labels
        this.add(lblVaelgDistillat,0,0);
        this.add(lblLiter,1,0);
        this.add(lblvealglager,2,0);


        //-------------------------------------------------text

        this.add(txtxLiter, 1, 1);


        //-------------------------------------------------dato
        this.add(datePicker, 1, 4);
        datePicker.setOnAction(event -> {
            selectedDate = datePicker.getValue();
        });






    }
    //TODO lav input validering
    private void btnOpretAction() {
        lblError.setText("");
        int plads = 1;
        Lager lager = (Lager)comboBoxLager.getValue();
        String fadtype = txtxLiter.getText().trim();
        int fadStr = Integer.parseInt(txtfadStr.getText().trim());
        if (fadtype.length() == 0 || fadStr == 0 || txtfadStr.getText().trim().length() == 0) {
            lblError.setText("Udfyld venligst alle felter");
        } else {
            controller.opretFad(fadtype, fadStr, lager, plads);
            txtfadStr.clear();
            txtxLiter.clear();
            comboBoxLager.getSelectionModel().clearSelection();
            this.updateControls();
        }
    }

    public void updateControls() {
        comboBoxLager.getItems().clear();
        comboBoxLager.getItems().addAll(controller.getAlleLagre());
        if (this.valgtLager != null) {
            listFade.getItems().addAll(controller.getFadeILager(valgtLager.getId()));
        }


    }

}
