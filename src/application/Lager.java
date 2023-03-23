package application;

import java.util.ArrayList;

/**
 * Lager klassen repræsenterer et lager, hvor der er plads til et bestemt antal fade.
 */
public class Lager {
    private String lokation;
    private int id;
    private ArrayList<Fad> fade;
    private int antalPladser;

    /**
     * Opretter et nyt Lager objekt.
     *
     * @param lokation     Lagerets geografiske placering.
     * @param id           Lagerets unikke ID. (Præ-condition: ID er gyldigt)
     * @param antalPladser Antallet af pladser til fade i lageret.
     */
    public Lager(String lokation, int id, int antalPladser) {
        this.lokation = lokation;
        this.id = id;
        this.fade = new ArrayList<>();
        this.antalPladser = antalPladser;
    }

    /**
     * Returnerer en liste af fade i lageret.
     *
     * @return En ArrayList af Fad objekter i lageret.
     */
    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    /**
     * Tilføjer et Fad objekt til lageret.
     *
     * @param fad Fad objektet, der skal tilføjes til lageret.
     */
    public void addFad(Fad fad) {
        if (!(this.fade.contains(fad))) {
            this.fade.add(fad);
            fad.setLager(this);
        }
    }

    /**
     * Fjerner et Fad objekt fra lageret.
     *
     * @param fad Fad objektet, der skal fjernes fra lageret.
     */
    public void removeFad(Fad fad) {
        if (this.fade.contains(fad)) {
            this.fade.remove(fad);
        }
        fad.setLager(null);
    }

    public int getId() {
        return this.id;
    }
}