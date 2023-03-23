package application;

/**
 * Fad klassen repræsenterer et fad, der kan opbevares i et lager.
 */
public class Fad {
    private int id;
    private String fadType;
    private double fadStr;
    private String newSpiritBatchNr;
    private double antalLiterPåFyldt;
    private double alkoholProcent;
    private String medarbejderintialer;
    private Lager lager;

    /**
     * Opretter et nyt Fad objekt med alle detaljer.
     *
     * @param id                  Fadets unikke ID.
     * @param fadType             Typen af fad.
     * @param fadStr              Fadets størrelse i liter.
     * @param newSpiritBatchNr    Batchnummeret for spiritussen i fadet.
     * @param antalLiterPåFyldt   Antallet af liter fyldt i fadet.
     * @param alkoholProcent      Alkoholprocenten for spiritussen i fadet.
     * @param medarbejderintialer Initialerne for medarbejderen, der håndterede fadet.
     * @param lager               Lageret, hvor fadet skal opbevares.
     */
    public Fad(int id, String fadType, double fadStr, String newSpiritBatchNr, double antalLiterPåFyldt, double alkoholProcent, String medarbejderintialer, Lager lager) {
        this.id = id;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.newSpiritBatchNr = newSpiritBatchNr;
        this.antalLiterPåFyldt = antalLiterPåFyldt;
        this.alkoholProcent = alkoholProcent;
        this.medarbejderintialer = medarbejderintialer;
        this.setLager(lager);
    }

    /**
     * Opretter et nyt Fad objekt med basale detaljer.
     *
     * @param id      Fadets unikke ID.
     * @param fadType Typen af fad.
     * @param fadStr  Fadets størrelse i liter.
     */
    public Fad(int id, String fadType, double fadStr) {
        this.id = id;
        this.fadType = fadType;
        this.fadStr = fadStr;
    }

    /**
     * Sætter lageret for Fad objektet og opdaterer referencer mellem Lager og Fad.
     *
     * @param lager Lageret, hvor fadet skal opbevares.
     */
    public void setLager(Lager lager) {
        if (!(this.lager == lager)) {
            this.lager = lager;
            Lager oldLager = this.lager;
            if (oldLager != null) {
                oldLager.removeFad(this);
            }
            this.lager = lager;
            if (lager != null) {
                lager.addFad(this);
            }
        }
    }
}