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
     * Constructor for FadsLagretVæskeHistorik-objektet.
     *
     * @param lagretVaeske LagretVæske-objektet, der skal knyttes til FadsLagretVæskeHistorik.
     * @param fillDate     LocalDate-objektet, der repræsenterer fyldningsdatoen for den lagrede væske.
     * @param emptyDate    LocalDate-objektet, der repræsenterer tømningsdatoen for den lagrede væske.
     *                     Præbetingelse:
     *                     lagretVaeske skal være et gyldigt LagretVæske-objekt.
     *                     fillDate skal være et gyldigt LocalDate-objekt.
     *                     emptyDate skal være et gyldigt LocalDate-objekt eller null.
     */
    public FadsLagretVæskeHistorik(LagretVæske lagretVaeske, LocalDate fillDate, LocalDate emptyDate) {
        this.lagretVaeske = lagretVaeske;
        this.fillDate = fillDate;
        this.emptyDate = emptyDate;
    }

    /**
     * @return LagretVæske-objektet, der repræsenterer den lagrede væske.
     */
    public LagretVæske getLagretVaeske() {
        return lagretVaeske;
    }

    /**
     * @return int-værdien, der repræsenterer den lagrede væskes ID.
     */
    public int getLagretVæskeId() {
        return this.lagretVaeske.getId();
    }

    /**
     * @return LocalDate-objektet, der repræsenterer fyldningsdatoen.
     */
    public LocalDate getFillDate() {
        return fillDate;
    }

    /**
     * @return LocalDate-objektet, der repræsenterer tømningsdatoen.
     */
    public LocalDate getEmptyDate() {
        return emptyDate;
    }

    /**
     * @return en ArrayList af Distillat-objekter, der er knyttet til den lagrede væske.
     */
    public ArrayList<Distillat> getDistillater() {
        return this.lagretVaeske.getDistillater();
    }

    /**
     * @return en ArrayList af LagretVæskesFadHistorik-objekter, der repræsenterer historikken for den lagrede væske.
     */
    public ArrayList<LagretVæskesFadHistorik> getHistorik() {
        return this.lagretVaeske.getFadehistorik();
    }


    /**
     * Angiver et nyt LagretVæske-objekt for denne historikpost.
     *
     * @param lagretVaeske LagretVæske-objektet, der skal sættes som den lagrede væske.
     *                     Præbetingelse: lagretVaeske skal være et gyldigt LagretVæske-objekt.
     */
    public void setLagretVaeske(LagretVæske lagretVaeske) {
        this.lagretVaeske = lagretVaeske;
    }


    /**
     * Angiver en ny påfyldningsdato for væsken i denne historikpost.
     *
     * @param fillDate LocalDate-objektet, der repræsenterer den nye fyldningsdato.
     *                 Præbetingelse: fillDate skal være et gyldigt LocalDate-objekt.
     */
    public void setFillDate(LocalDate fillDate) {
        this.fillDate = fillDate;
    }


    /**
     * Angiver tømningsdatoen for den lagrede væske.
     *
     * @param emptyDate LocalDate-objektet, der repræsenterer den nye tømningsdato.
     *                  Præbetingelse: emptyDate skal være et gyldigt LocalDate-objekt.
     */
    public void setEmptyDate(LocalDate emptyDate) {
        this.emptyDate = emptyDate;
    }

    /**
     * @return en streng, der repræsenterer objektet.
     */
    public String toString() {
        return lagretVaeske + " " + getEmptyDate();
    }
}