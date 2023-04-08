package application;

import java.time.LocalDate;

public class FadHistoryEntry {
    private Fad fad;
    private LocalDate date;

    public FadHistoryEntry(Fad fad, LocalDate date) {
        this.fad = fad;
        this.date = date;
    }

    public Fad getFad() {
        return fad;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Fad: " + fad.toString() + ", Date: " + date.toString();
    }
}