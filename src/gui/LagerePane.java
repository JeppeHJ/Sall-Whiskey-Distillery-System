package gui;

import application.Lager;
import controller.Controller;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class LagerePane extends GridPane {
    private Controller controller = Controller.getController();
    private ListView<Lager> lstLager = new ListView<>();


}
