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
    private ArrayList<Distillat> distillater;
    private ArrayList<LagretVæskesFadHistorik> fadHistorik;

    /**
     * Constructor for LagretVæske.
     *
     * @param liter           Den mængde væske i liter, der er på fadet.
     * @param påfyldningsDato Datoen for, hvornår væsken blev påfyldt fadet.
     *                        Præbetingelse: liter skal være en positiv double-værdi.
     *                        Præbetingelse: påfyldningsDato skal være en gyldig LocalDate objekt, og bør ikke være en fremtidig dato.
     */

    public LagretVæske(double liter, LocalDate påfyldningsDato) {
        this.påfyldningsDato = påfyldningsDato;
        this.liter = liter;
        this.id = count++;
        this.distillater = new ArrayList<>();
        this.fadHistorik = new ArrayList<>();
        this.alkoholProcent = this.calculateAlkoholprocent();
    }

    /**
     * Tilføjer en liste af destillater til denne LagretVæske, hvis de ikke allerede er tilføjet.
     *
     * @param distillater En ArrayList af Distillat objekter, der skal tilføjes. Præbetingelse: distillater skal være en gyldig ArrayList af Distillat objekter.
     */
    public void addDestillater(ArrayList<Distillat> distillater) {
        for (Distillat distillat : distillater) {
            if (!this.distillater.contains(distillat)) {
                this.distillater.add(distillat);
            }
        }
    }

    /**
     * Beregner alkoholprocenten for denne LagretVæske baseret på de tilknyttede destillater.
     *
     * @return Alkoholprocenten som en double-værdi.
     */
    public double calculateAlkoholprocent() {
        if (distillater.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (Distillat distillat : distillater) {
            sum += distillat.getAlkoholprocent();
        }
        return sum / distillater.size();
    }

    /**
     * Tilføjer en liste af LagretVæskesFadHistorik objekter til denne LagretVæskes historik, hvis de ikke allerede er tilføjet.
     *
     * @param fadHistorikker En ArrayList af LagretVæskesFadHistorik objekter, der skal tilføjes. Præbetingelse: fadHistorikker skal være en gyldig ArrayList af LagretVæskesFadHistorik objekter.
     */
    public void addFadHistorikker(ArrayList<LagretVæskesFadHistorik> fadHistorikker) {
        for (LagretVæskesFadHistorik fadHistorik : fadHistorikker) {
            // Only add the fadHistorik if it's not already present in the list
            if (!this.fadHistorik.contains(fadHistorik)) {
                this.fadHistorik.add(fadHistorik);
            }
        }
    }

    /**
     * Opdaterer historikken for denne LagretVæske, når der sker en omhældning.
     *
     * @param lagretVæske Det LagretVæske objekt, der skal redigeres. Præbetingelse: lagretVæske skal være et gyldigt LagretVæske objekt.
     * @param emptyDate   Datoen for, hvornår fadet blev tømt. Præbetingelse: emptyDate skal være en gyldig LocalDate objekt og bør ikke være en fremtidig dato.
     */
    public void editHistoryWhenOmhældning(LagretVæske lagretVæske, LocalDate emptyDate) {
        for (LagretVæskesFadHistorik lV : fadHistorik) {
            if (lV.getFad().getLagretVæsker() != null && !lV.getFad().getLagretVæsker().isEmpty()) {
                if (lV.getFad().getLagretVæsker().get(0) == lagretVæske) {
                    lV.setTilDato(emptyDate);
                }
            }
        }
    }


    public int getId() {
        return id;
    }

    /**
     * Henter påfyldningsdatoen for denne LagretVæske.
     *
     * @return Påfyldningsdatoen som en LocalDate værdi.
     */
    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }

    /**
     * Henter antallet af liter for denne LagretVæske.
     *
     * @return Antallet af liter som en double værdi.
     */
    public double getLiter() {
        return liter;
    }

    /**
     * Tilføjer et destillat til denne LagretVæske, hvis det ikke allerede er tilføjet.
     *
     * @param distillat Det Distillat objekt, der skal tilføjes. Præbetingelse: distillat skal være et gyldigt Distillat objekt.
     */
    public void addDistillat(Distillat distillat) {
        if (!this.distillater.contains(distillat)) {
            this.distillater.add(distillat);
        }
    }

    /**
     * Sætter antallet af liter for denne LagretVæske.
     *
     * @param liter Den nye mængde liter som en double værdi. Præbetingelse: liter skal være en positiv double værdi.
     */
    public void setLiter(double liter) {
        if (liter == 0) {

        }
        this.liter = liter;
    }


    /**
     * Henter alkoholprocenten for denne LagretVæske.
     *
     * @return Alkoholprocenten som en double værdi.
     */
    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    /**
     * Henter en liste af destillater tilknyttet denne LagretVæske.
     *
     * @return En ArrayList af Distillat objekter.
     */
    public ArrayList<Distillat> getDistillater() {
        return new ArrayList<>(distillater);
    }

    /**
     * Beregner den nye alkoholprocent ved at blande destillater og de tilsvarende volumener.
     *
     * @param distillater En ArrayList af Distillat objekter.
     * @param volumes     Et double array med volumener, der svarer til destillater i distillater ArrayList. Præbetingelse: volumes skal have samme længde som distillater ArrayList.
     * @return Den nye alkoholprocent som en double værdi.
     */
    public static double calculateNewAlkoholProcent(ArrayList<Distillat> distillater, double[] volumes) {
        double totalAlkohol = 0;
        double totalVolumen = 0;
        for (int i = 0; i < distillater.size(); i++) {
            totalAlkohol += distillater.get(i).getAlkoholprocent() * volumes[i];
            totalVolumen += volumes[i];
        }
        System.out.println(totalAlkohol);
        System.out.println(totalVolumen);
        return totalAlkohol / totalVolumen;
    }

    /**
     * Henter fadhistorikken for denne LagretVæske.
     *
     * @return En ArrayList af LagretVæskesFadHistorik objekter.
     */
    public ArrayList<LagretVæskesFadHistorik> getFadehistorik() {
        return this.fadHistorik;
    }

    /**
     * Tilføjer et fad til denne LagretVæskes historik sammen med påfyldnings- og tømningsdatoerne.
     *
     * @param fad       Det Fad objekt, der skal tilføjes til historikken. Præbetingelse: fad skal være et gyldigt Fad objekt.
     * @param fillDate  fillDate Påfyldningsdatoen som en LocalDate værdi. Præbetingelse: fillDate skal være en gyldig LocalDate objekt og bør ikke være en fremtidig dato.
     * @param emptyDate Tømningsdatoen som en LocalDate værdi. Præbetingelse: emptyDate skal være en gyldig LocalDate objekt og bør ikke være en fremtidig dato.
     */
    public void addFadTilHistorik(Fad fad, LocalDate fillDate, LocalDate emptyDate) {
        LagretVæskesFadHistorik entry = new LagretVæskesFadHistorik(fad, fillDate, emptyDate);
        fadHistorik.add(entry);
    }

    /**
     * Returnerer en strengrepræsentation af denne LagretVæske.
     *
     * @return En strengrepræsentation af denne LagretVæske, der indeholder væske-ID og påfyldningsdato.
     */
    @Override
    public String toString() {
        return "VæskeID: " + this.id + " | " + påfyldningsDato + " -";
    }
}