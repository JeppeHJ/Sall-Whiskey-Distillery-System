package controller;

import application.Fad;
import application.Lager;
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

    /**
     * Privat konstruktør for at forhindre instantiering fra uden for klassen.
     */
    private Controller() {
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

    /**
     * Opretter et nyt Lager objekt og tilføjer det til Storage.
     *
     * @param lokation     Lagerets placering.
     * @param id           Lagerets ID.
     * @param antalPladser Antallet af ledige pladser i Lageret.
     */
    public void opretLager(String lokation, int id, int antalPladser) {
        Lager lager = new Lager(lokation, id, antalPladser);
        storage.addLager(lager);
    }

    /**
     * Opretter et nyt Fad objekt og tilføjer det til det angivne Lager.
     *
     * @param id                 Fadets ID.
     * @param fadType            Fadets type.
     * @param fadStr             Fadets styrke.
     * @param newSpiritBatchNr   Batchnummeret for den nye ånd.
     * @param antalLiterPåFyldt  Antallet af liter, når fadet er fyldt.
     * @param alkoholProcent     Alkoholprocenten i fadet.
     * @param medarbejderintialer Initialerne for den ansvarlige medarbejder.
     * @param lagerId            ID'et for det Lager, hvor fadet skal placeres.
     */
    public void opretFad(int id, String fadType, double fadStr, String newSpiritBatchNr, double antalLiterPåFyldt, double alkoholProcent, String medarbejderintialer, int lagerId) {
        Lager lager = storage.getLagerById(lagerId);
        if (lager != null) {
            Fad fad = new Fad(id, fadType, fadStr, newSpiritBatchNr, antalLiterPåFyldt, alkoholProcent, medarbejderintialer, lager);
            lager.addFad(fad);
        }
    }

    /**
     * Returnerer en liste over alle Lager objekter i Storage.
     *
     * @return en ArrayList af Lager objekter.
     */
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

}

