package application;

import java.util.ArrayList;

/**
 * Lager klassen repræsenterer et lager, hvor der er plads til et bestemt antal fade.
 */
public class Lager {
    private String lokation;
    private static int count = 0;
    private int id;
    private ArrayList<Boolean> fadPlads;
    private ArrayList<Fad> fade;
    private int antalPladser;


    public Lager(String lokation, int antalPladser) {
        this.lokation = lokation;
        count++;
        this.id = count;
        this.fade = new ArrayList<>();
        this.antalPladser = antalPladser;
        this.fadPlads = new ArrayList<>();
        for (int i = 0; i < antalPladser; i++) {
            this.fadPlads.add(i,false);
        }
    }

    public int getAntalPladser() {
        return antalPladser;
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    /**
     * Præ-kondition: Lager ikke fyldt
     * @param fad
     */
    public void addFad(Fad fad) {
        if (!(this.fade.contains(fad))) {
            this.fade.add(fad);
        }
    }


    public void removeFad(Fad fad) {
        if (this.fade.contains(fad)) {
            this.fade.remove(fad);
        }
    }

    public int getId() {
        return this.id;
    }
    @Override
    public String toString() {
        return
                 lokation + ' ' +
                " id: " + id + " antal fad: "+this.fade.size()+
                " Pladser: " + antalPladser;
    }
    public int friePladser(){
        return antalPladser-fade.size();
    }
}