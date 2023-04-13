package application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Lager klassen repræsenterer et lager, hvor der er plads til et bestemt antal fade.
 */
public class Lager {
    private final String lokation;
    private static int count = 0;
    private final int id;

    private int antalPladser;
    private HashMap<Integer, Fad> fadeHashMap;

    /**
     * Konstruktor for Lager.
     *
     * @param lokation     Lagerets placering.
     * @param antalPladser Antallet af pladser i lageret.
     */
    public Lager(String lokation, int antalPladser) {
        this.lokation = lokation;
        count++;
        this.id = count;
        this.antalPladser = antalPladser;
        this.fadeHashMap = new HashMap<>();
        for (int i = 0; i < antalPladser; i++) {
            this.fadeHashMap.put(i + 1, null);
        }
    }

    // Getter for fadeHashMap
    public HashMap<Integer, Fad> getFadeHashMap() {
        return fadeHashMap;
    }

    /**
     * Tæller antallet af fade i lageret.
     *
     * @return Antallet af fade i lageret.
     */
    public int amountOfFade() {
        int count = 0;
        for (Fad fad : fadeHashMap.values()) {
            if (fad != null) {
                count++;
            }
        }

        return count;
    }

    // Getter for antalPladser
    public int getAntalPladser() {
        return antalPladser;
    }

    /**
     * Tilføjer et fad til lageret på en bestemt position.
     * Præ-kondition: Lager ikke fyldt.
     *
     * @param fad      Fadet, der skal tilføjes.
     * @param position Positionen i lageret, hvor fadet skal placeres.
     */
    public void addFad(Fad fad, int position) {
        if (!this.fadeHashMap.containsValue(fad)) {
            this.fadeHashMap.put(position, fad);
        }
    }

    /**
     * Fjerner et fad fra lageret fra en bestemt position.
     *
     * @param fad      Fadet, der skal fjernes.
     * @param position Positionen i lageret, hvor fadet skal fjernes fra.
     */
    public void removeFad(Fad fad, int position) {
        if (this.fadeHashMap.containsValue(fad)) {
            this.fadeHashMap.put(position, null);
        }
    }

    // Getter for id
    public int getId() {
        return this.id;
    }

    /**
     * toString-metode for Lager.
     *
     * @return En strengrepræsentation af Lager-objektet.
     */
    @Override
    public String toString() {
        return id + " | " + lokation + " | " + "Pladser: " + antalPladser;
    }
}