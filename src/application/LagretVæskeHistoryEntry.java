package application;

import java.time.LocalDate;

public class LagretVæskeHistoryEntry {
    private LagretVæske lagretVaeske;
    private LocalDate fillDate;
    private LocalDate emptyDate;

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
