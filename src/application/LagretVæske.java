package application;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * LagretVæske klassen repræsenterer en væske, der er lagret på et fad og indeholder en historik over fade og påfyldningsdatoer.
 */
public class LagretVæske {
    private static int count = 0;
    private int id;
    private double liter;
    private double alkoholProcent;
    private LocalDate påfyldningsDato;
    private HashMap<Fad, LocalDate> fadehistorik;
    private ArrayList<Distillat> distillater;
    private ArrayList<LagretVæskesFadHistorik> fadHistorik;


    /**
     * Konstruktor for LagretVæske.
     *
     * @param liter           Mængde af væske i liter.
     * @param påfyldningsDato Datoen for påfyldning af væsken.
     */
    public LagretVæske(double liter, LocalDate påfyldningsDato) {
        this.påfyldningsDato = påfyldningsDato;
        this.liter = liter;
        this.id = count++;
        this.distillater = new ArrayList<>();
        this.fadehistorik = new HashMap<>();
        this.fadHistorik = new ArrayList<>();
    }

    public void addDestillater(ArrayList<Distillat> distillater) {
        for (Distillat distillat : distillater) {
            if (!this.distillater.contains(distillat)) {
                this.distillater.add(distillat);
            }
        }
    }

    public void addFadHistorikker(ArrayList<LagretVæskesFadHistorik> fadHistorikker) {
        for (LagretVæskesFadHistorik fadHistorik : fadHistorikker) {
            // Only add the fadHistorik if it's not already present in the list
            if (!this.fadHistorik.contains(fadHistorik)) {
                this.fadHistorik.add(fadHistorik);
            }
        }
    }

    public void editHistoryWhenOmhældning(LagretVæske lagretVæske, LocalDate emptyDate) {
        for (LagretVæskesFadHistorik lV: fadHistorik) {
            if (lV.getFad().getLagretVæsker() != null && !lV.getFad().getLagretVæsker().isEmpty()) {
                if (lV.getFad().getLagretVæsker().get(0) == lagretVæske) {
                    lV.setTilDato(emptyDate);
                }
            }
        }
    }

    // Getters og setters
    public int getId() {
        return id;
    }

    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }

    public double getLiter() {
        return liter;
    }

    public void addDistillat(Distillat distillat) {
        if (!this.distillater.contains(distillat)) {
            this.distillater.add(distillat);
        }
    }

    public void setLiter(double liter) {
        if (liter == 0) {

        }
        this.liter = liter;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public ArrayList<Distillat> getDistillater() {
        return new ArrayList<>(distillater);
    }

    /**
     * Beregner den nye alkoholprocent baseret på en liste af distillater og deres respektive volumener.
     *
     * @param distillater Liste af distillater.
     * @param volumes     Array af volumener for hver distillat.
     * @return Den nye alkoholprocent.
     */
    public static double calculateNewAlkoholProcent(ArrayList<Distillat> distillater, double[] volumes) {
        double totalAlkohol = 0;
        double totalVolumen = 0;
        for (int i = 0; i < distillater.size(); i++) {
            totalAlkohol += distillater.get(i).getAlkoholprocent() * volumes[i];
            totalVolumen += volumes[i];
        }
        return totalAlkohol / totalVolumen;
    }

    // Getter for fadehistorik
    public ArrayList<LagretVæskesFadHistorik> getFadehistorik() {
        return this.fadHistorik;
    }


    /**
     * Tilføjer et fad og påfyldningsdato til væskens historik.
     *
     * @param fad             Fadet, der skal tilføjes.
     */
    public void addFadTilHistorik(Fad fad, LocalDate fillDate, LocalDate emptyDate) {
        LagretVæskesFadHistorik entry = new LagretVæskesFadHistorik(fad, fillDate, emptyDate);
        fadHistorik.add(entry);
    }

    /**
     * toString-metode for LagretVæske*
     *
     * @return En strengrepræsentation af LagretVæske, inklusive fadehistorik og distillater.
     */
    @Override
    public String toString() {
        return "VæskeID: " + this.id +  " | " + påfyldningsDato + " -";
    }
}