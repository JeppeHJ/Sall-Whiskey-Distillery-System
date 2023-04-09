package application;

import java.time.LocalDate;

/**
 * LagretVæskeHistoryEntry klassen repræsenterer en historik over lagret væske, der er blevet fyldt og tømt fra et fad.
 */
public class LagretVæskeHistoryEntry {
    private LagretVæske lagretVaeske;
    private LocalDate fillDate;
    private LocalDate emptyDate;

    /**
     * Konstruktør for LagretVæskeHistoryEntry
     * @param lagretVaeske Det lagrede væskeobjekt
     * @param fillDate Påfyldningsdatoen for væsken i fadet
     * @param emptyDate Tømningsdatoen for væsken fra fadet
     */
    public LagretVæskeHistoryEntry(LagretVæske lagretVaeske, LocalDate fillDate, LocalDate emptyDate) {
        this.lagretVaeske = lagretVaeske;
        this.fillDate = fillDate;
        this.emptyDate = emptyDate;
    }

    // Getters and setters

    public LagretVæske getLagretVaeske() {
        return lagretVaeske;
    }

    public void setLagretVaeske(LagretVæske lagretVaeske) {
        this.lagretVaeske = lagretVaeske;
    }

    public LocalDate getFillDate() {
        return fillDate;
    }

    public void setFillDate(LocalDate fillDate) {
        this.fillDate = fillDate;
    }

    public LocalDate getEmptyDate() {
        return emptyDate;
    }

    public void setEmptyDate(LocalDate emptyDate) {
        this.emptyDate = emptyDate;
    }
}