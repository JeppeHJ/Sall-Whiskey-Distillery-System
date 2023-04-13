package application;

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
     * Konstruktør for Lager, der opretter et nyt lager med en given placering og et bestemt antal pladser.
     *
     * @param lokation Lagerets placering som en streng.
     * @param antalPladser  Antallet af pladser i lageret som en hel værdi.
     *
     * Præbetingelse:
     * lokation skal være en ikke-null streng.
     * antalPladser skal være en ikke-negativ hel værdi.
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

    /**
     * @return en HashMap, der repræsenterer lagerets fadpositioner og de tilsvarende fade, hvis de er til stede.
     */
    public HashMap<Integer, Fad> getFadeHashMap() {
        return fadeHashMap;
    }

    /**
     * @return antallet af fade i lageret som en hel værdi.
     */
    public int amountAfFade() {
        int count = 0;
        for (Fad fad : fadeHashMap.values()) {
            if (fad != null) {
                count++;
            }
        }

        return count;
    }

    /**
     * @return antallet af pladser i lageret som en hel værdi.
     */
    public int getAntalPladser() {
        return antalPladser;
    }

    /**
     * Tilføjer et fad til lageret på en bestemt position.
     *
     * @param fad Fadet, der skal tilføjes til lageret.
     * @param position Positionen i lageret, hvor fadet skal placeres.
     *
     * Præbetingelse:
     * Lageret må ikke være fyldt.
     * fad skal være et gyldigt Fad-objekt.
     * position skal være en gyldig position i lageret (inden for antallet af pladser).
     */
    public void addFad(Fad fad, int position) {
        if (!this.fadeHashMap.containsValue(fad)) {
            this.fadeHashMap.put(position, fad);
        }
    }

    /**
     * Fjerner et fad fra lageret fra en bestemt position.
     *
     * @param fad Fadet, der skal fjernes fra lageret
     * @param position Positionen i lageret, hvor fadet skal fjernes fra.
     *
     * Præbetingelse:
     * fad skal være et gyldigt Fad-objekt.
     * position skal være en gyldig position i lageret (inden for antallet af pladser).
     */
    public void removeFad(Fad fad, int position) {
        if (this.fadeHashMap.containsValue(fad)) {
            this.fadeHashMap.put(position, null);
        }
    }

    /**
     * @return lagerets unikke ID som en hel værdi.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return en strengrepræsentation af Lager-objektet, inklusive lagerets ID, placering og antal pladser.
     */
    @Override
    public String toString() {
        return id + " | " + lokation + " | " + "Pladser: " + antalPladser;
    }

    /**
     * @return en streng, der repræsenterer lagerets placering.
     */
    public String getLokation(){
        return this.lokation;
    }
}