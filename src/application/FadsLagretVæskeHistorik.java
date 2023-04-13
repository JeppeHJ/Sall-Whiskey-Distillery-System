package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * FadsLagretVæskeHistorik klassen repræsenterer en historik over lagret væske, der er blevet fyldt og tømt fra et fad.
 */
public class FadsLagretVæskeHistorik {
    private LagretVæske lagretVaeske;

    private LocalDate fillDate;
    private LocalDate emptyDate;

    /**
     * Konstruktør for FadsLagretVæskeHistorik
     * @param lagretVaeske Det lagrede væskeobjekt
     * @param fillDate Påfyldningsdatoen for væsken i fadet
     * @param emptyDate Tømningsdatoen for væsken fra fadet
     */
     FadsLagretVæskeHistorik(LagretVæske lagretVaeske, LocalDate fillDate, LocalDate emptyDate) {
        this.lagretVaeske = lagretVaeske;
        this.fillDate = fillDate;
        this.emptyDate = emptyDate;
    }

    // Getters and setters

    public LagretVæske getLagretVaeske() {
        return lagretVaeske;
    }

    public int getLagretVæskeId() {
        return this.lagretVaeske.getId();
    }

    public LocalDate getFillDate() {
        return fillDate;
    }
    public LocalDate getEmptyDate() {
        return emptyDate;
    }

    public ArrayList<Distillat> getDistillater() {
        return this.lagretVaeske.getDistillater();
    }

    public ArrayList<LagretVæskesFadHistorik> getHistorik() {
        return this.lagretVaeske.getFadehistorik();
    }



    public void setLagretVaeske(LagretVæske lagretVaeske) {
        this.lagretVaeske = lagretVaeske;
    }



    public void setFillDate(LocalDate fillDate) {
        this.fillDate = fillDate;
    }



    public void setEmptyDate(LocalDate emptyDate) {
        this.emptyDate = emptyDate;
    }

    public String toString() {
        return lagretVaeske + " " + getEmptyDate();
    }
}