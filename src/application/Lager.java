package application;

import java.util.ArrayList;

/**
 * Lager klassen repræsenterer et lager, hvor der er plads til et bestemt antal fade.
 */
public class Lager {
    private String lokation;
    private static int count = 0;
    private int id;
    private ArrayList<Fad> fade;
    private int antalPladser;


    public Lager(String lokation, int id, int antalPladser) {
        this.lokation = lokation;
        count++;
        this.id = count;
        this.fade = new ArrayList<>();
        this.antalPladser = antalPladser;
    }


    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }


    public void addFad(Fad fad) {
        if (!(this.fade.contains(fad))) {
            this.fade.add(fad);
            fad.setLager(this);
        }
    }


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