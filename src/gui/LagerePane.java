package gui;

import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class LagerePane extends GridPane {
    private Controller controller = Controller.getController();
    private ListView<Lager> lstLager = new ListView<>();
    private Button btnOpretLager = new Button("Opret lager");
    private Label antalLagre = new Label("Antal lagre: " + controller.getAlleLagre().size());
    public LagerePane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        //----------------------------------------------


        this.add(lstLager, 0, 0);
        this.lstLager.getItems().setAll(controller.getAlleLagre());
        this.add(btnOpretLager, 15, 1);
        this.add(antalLagre, 0, 1);



    }
    public void updateControls() {
        lstLager.getSelectionModel().clearSelection();

    }


}
