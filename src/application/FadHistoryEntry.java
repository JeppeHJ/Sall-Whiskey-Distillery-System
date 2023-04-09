package application;

import java.time.LocalDate;

/**
 * FadHistoryEntry-klassen bruges til at repræsentere en historisk post om et fad.
 * Den indeholder et Fad-objekt og en dato, der repræsenterer, hvornår en begivenhed
 * fandt sted i forbindelse med det pågældende fad.
 */
public class FadHistoryEntry {
    private Fad fad;
    private LocalDate date;

    /**
     * Konstruktor for FadHistoryEntry.
     *
     * @param fad  Fad-objektet, som historikposten refererer til.
     * @param date Datoen, hvor en begivenhed fandt sted for det pågældende fad.
     */
    public FadHistoryEntry(Fad fad, LocalDate date) {
        this.fad = fad;
        this.date = date;
    }

    // Getter for Fad
    public Fad getFad() {
        return fad;
    }

    // Getter for Date
    public LocalDate getDate() {
        return date;
    }

    /**
     * toString-metode for FadHistoryEntry.
     *
     * @return En strengrepræsentation af FadHistoryEntry-objektet.
     */
    @Override
    public String toString() {
        return "Fad: " + fad.toString() + ", Date: " + date.toString();
    }
}