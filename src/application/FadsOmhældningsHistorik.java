package application;

import java.time.LocalDate;

public class FadsOmhældningsHistorik {
    private Fad kildeFad;
    private Fad destinationFad;
    private double mængde;
    private LocalDate datoForOmhældning;

      FadsOmhældningsHistorik(Fad fadKilde, Fad fadDestination, double mængde, LocalDate datoForOmhældning) {
        this.kildeFad = fadKilde;
        this.destinationFad = fadDestination;
        this.mængde = mængde;
        this.datoForOmhældning = datoForOmhældning;
    }

    public Fad getKildeFad() {
        return kildeFad;
    }

    public Fad getDestinationFad() {
        return destinationFad;
    }

    public double getMængde() {
        return mængde;
    }

    public LocalDate getDatoForOmhældning() {
        return datoForOmhældning;
    }

    public String toString() {
        return "Date: " + this.datoForOmhældning;
    }
}
