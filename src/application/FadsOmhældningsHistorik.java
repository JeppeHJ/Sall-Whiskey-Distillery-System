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

    public FadsOmhældningsHistorik(Fad fadKilde, Fad fadDestination, double mængde, LocalDate datoForOmhældning) {
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

    /**
     * Getter-metode for destinationFad.
     *
     * @return DestinationFad Fad-objektet, hvortil væsken hældes.
     */
    public Fad getDestinationFad() {
        return destinationFad;
    }

    /**
     * Getter-metode for mængde.
     *
     * @return Mængde Den mængde væske, der skal omhældes.
     */
    public double getMængde() {
        return mængde;
    }

    /**
     * Getter-metode for datoForOmhældning.
     *
     * @return DatoForOmhældning LocalDate-objektet, der repræsenterer datoen for omhældning.
     */
    public LocalDate getDatoForOmhældning() {
        return datoForOmhældning;
    }

    /**
     * toString-metoden for FadsOmhældningsHistorik.
     *
     * @return En strengrepræsentation af FadsOmhældningsHistorik-objektet, der indeholder datoen for omhældning.
     */
    public String toString() {
        return "Date: " + this.datoForOmhældning;
    }
}
