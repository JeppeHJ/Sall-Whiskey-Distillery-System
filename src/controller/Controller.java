package controller;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller-klassen er ansvarlig for at administrere Lager og Fad objekter.
 * Den bruger Singleton designmønsteret for at sikre, at kun én instans
 * af Controller oprettes i løbet af programmets levetid.
 */
public class Controller {
    private static Controller controller;
    private Storage storage;


    public Controller() {
        storage = Storage.getStorage();
    }

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    /**-------------- LAGER METODER --------------**/
    public Lager opretLager(String lokation, int antalPladser) {
        Lager lager = new Lager(lokation, antalPladser);
        storage.addLager(lager);
        return lager;
    }

    public ArrayList<Lager> getAlleLagre() {
        return storage.getLagre();
    }

    public ArrayList<Fad> getFadeILager(int lagerId) {
        Lager lager = storage.getLagerById(lagerId);
        if (lager != null) {
            return lager.getFade();
        }
        return new ArrayList<>();
    }

    public Lager getLagerById(int id) {
        return storage.getLagerById(id);
    }

    public int totalAntalLager() {
        return storage.getLagre().size();
    }

    /**-------------- FAD METODER --------------**/

    public Fad opretFad(String fadType, double fadStr, Lager lager) {
        Fad fad = new Fad(fadType, fadStr);
        storage.getLagerById(lager.getId()).addFad(fad);
        storage.addFad(fad);
        return fad;
    }

    public int totalAntalFad() {
        int total = 0;
        for (Lager lager : storage.getLagre()) {
            total += lager.getFade().size();
        }
        return total;
    }

    //many testcases
    public void fyldPåSpecifiktFad(double liter, LocalDate påfyldningsDato, Fad fad, Distillat distillat, LagretVæske lagretVæske) {
        LagretVæske valgtLagretVæske = storage.getLagretVæskeById(lagretVæske.getId());
        Fad valgtFad = storage.getFadById(fad.getId());
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());

        valgtFad.påfyldning(valgtLagretVæske, påfyldningsDato);
        valgtDistillat.setLiter(valgtDistillat.getLiter() - liter);
    }

    public void autoFyldPåTommeFade(double liter, LocalDate påfyldningsDato, Lager lager, Distillat distillat) {
        LagretVæske lagretVæske = new LagretVæske(liter, påfyldningsDato);
        Lager valgtLager = storage.getLagerById(lager.getId());
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());
    }

    /**-------------- LagretVæske METODER --------------**/

    //TODO Håndter oprettelse af lagretVæske (hvordan skal fad angives?)
    public LagretVæske opretLagretVæske(double liter, LocalDate påfyldningsDato) {
        LagretVæske lagretVæske = new LagretVæske(liter, påfyldningsDato);
        storage.addLagretVæske(lagretVæske);
        return lagretVæske;
    }

    public HashMap<Fad, LocalDate> getFadehistorik(LagretVæske lagretVæske) {
        return storage.getLagretVæskeById(lagretVæske.getId()).getFadehistorik();
    }

    /**-------------- Distillat METODER --------------**/

    public Distillat opretDistillat(double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale) {
        Distillat distillat = new Distillat(liter, maltBatch, kornsort, alkoholprocent, rygemateriale);
        storage.addDistillat(distillat);
        return distillat;
    }




    public void createSomeObjects() {
    }
}

