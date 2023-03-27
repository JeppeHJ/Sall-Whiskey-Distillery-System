package controller;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;
import storage.Storage;

import java.util.ArrayList;

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

    /**
     * Returnerer singleton-instansen af Controller-klassen.
     *
     * @return singleton-instansen af Controller-klassen.
     */
    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public Lager opretLager(String lokation, int antalPladser) {
        Lager lager = new Lager(lokation, antalPladser);
        storage.addLager(lager);
        return lager;
    }

    public Fad opretFad(String fadType, double fadStr, Lager lager) {
        Fad fad = new Fad(fadType, fadStr);
        storage.getLagerById(lager.getId()).addFad(fad);
        storage.addFad(fad);
        return fad;
    }

    //TODO Håndter oprettelse af lagretVæske (hvordan skal fad angives?)
    public LagretVæske opretLagretVæske(double liter, ArrayList<Distillat> distillater) {
        LagretVæske lagretVæske = new LagretVæske(liter, distillater);
        storage.addLagretVæske(lagretVæske);
        return lagretVæske;
    }

    public Distillat opretDistillat(double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale) {
        Distillat distillat = new Distillat(liter, maltBatch, kornsort, alkoholprocent, rygemateriale);
        storage.addDistillat(distillat);
        return distillat;
    }


    public ArrayList<Lager> getAlleLagre() {
        return storage.getLagre();
    }

    /**
     * Returnerer en liste over alle Fad objekter i det angivne Lager.
     *
     * @param lagerId ID'et for det Lager, hvis fade skal hentes.
     * @return en ArrayList af Fad objekter.
     */
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

    public int totalAntalFad() {
        int total = 0;
        for (Lager lager : storage.getLagre()) {
            total += lager.getFade().size();
        }
        return total;
    }

    public int totalAntalLager() {
        return storage.getLagre().size();
    }

    public void createSomeObjects() {
    }
}

