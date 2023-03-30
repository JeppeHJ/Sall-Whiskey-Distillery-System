package gui;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;
import controller.Controller;
import javafx.application.Application;

import java.time.LocalDate;
import java.util.ArrayList;

public class App {
    private static Controller controller = Controller.getController();

    public static void main(String[] args) {
        Application.launch(StartWindow.class);




    }
}
