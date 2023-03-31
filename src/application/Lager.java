package application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Lager klassen repræsenterer et lager, hvor der er plads til et bestemt antal fade.
 */
public class Lager {
    private String lokation;
    private static int count = 0;
    private int id;

    private int antalPladser;
    private HashMap<Integer, Fad> fadeHashMap;


    public Lager(String lokation, int antalPladser) {
        this.lokation = lokation;
        count++;
        this.id = count;
        this.antalPladser = antalPladser;
        this.fadeHashMap = new HashMap<>();
        for (int i = 0; i < antalPladser; i++) {
            this.fadeHashMap.put(i + 1,null);
        }
        System.out.println(this.fadeHashMap);
    }

    public HashMap<Integer, Fad> getFadeHashMap() {
        return fadeHashMap;
    }


    public int amountOfFade() {
        int count = 0;
        for (Fad fad: fadeHashMap.values()) {
            if (fad != null) {
                count++;
            }
        }

        return count;
    }

    public int getAntalPladser() {
        return antalPladser;
    }

    /**
     * Præ-kondition: Lager ikke fyldt
     * @param fad
     */
    public void addFad(Fad fad, int position) {
        if (!(this.fadeHashMap.containsValue(fad))) {
            this.fadeHashMap.put(position, fad);
        }
    }


    public void removeFad(Fad fad, int position) {
        if (this.fadeHashMap.containsValue(fad)) {
            this.fadeHashMap.put(position, null);
        }
    }

    public int getId() {
        return this.id;
    }
    @Override
    public String toString() {
        return
                 lokation + ' ' +
                " id: " + id + " antal fad: "+this.fadeHashMap.size()+
                " Pladser: " + antalPladser;
    }

}