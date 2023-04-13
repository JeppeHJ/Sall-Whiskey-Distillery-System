package application;

import java.time.LocalDate;

public class FadsOmhældningsHistorik {
    private Fad kildeFad;
    private Fad destinationFad;
    private double mængde;
    private LocalDate datoForOmhældning;

    /**
     * Constructor for FadsOmhældningsHistorik-objektet.
     *
     * @param fadKilde          Fad-objektet, hvorfra væsken hældes ud.
     * @param fadDestination    Fad-objektet, hvortil væsken hældes.
     * @param mængde            Den mængde væske, der skal omhældes.
     * @param datoForOmhældning LocalDate-objektet, der repræsenterer datoen for omhældning.
     *                          Præbetingelse:
     *                          fadKilde skal være et gyldigt Fad-objekt.
     *                          fadDestination skal være et gyldigt Fad-objekt.
     *                          mængde skal være en positiv double-værdi.
     *                          datoForOmhældning skal være et gyldigt LocalDate-objekt.
     */

    FadsOmhældningsHistorik(Fad fadKilde, Fad fadDestination, double mængde, LocalDate datoForOmhældning) {
        this.kildeFad = fadKilde;
        this.destinationFad = fadDestination;
        this.mængde = mængde;
        this.datoForOmhældning = datoForOmhældning;
    }


    /**
     * Getter-metode for kildeFad.
     *
     * @return KildeFad Fad-objektet, hvorfra væsken hældes ud.
     */
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
