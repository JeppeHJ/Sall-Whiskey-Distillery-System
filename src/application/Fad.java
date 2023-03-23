package application;

/**
 * Fad klassen repræsenterer et fad, der kan opbevares i et lager.
 */
public class Fad {
    private static int count;
    private int id;
    private String fadType;
    private double fadStr;
    private String newSpiritBatchNr;
    private double antalLiterPåFyldt;
    private double alkoholProcent;
    private String medarbejderintialer;
    private Lager lager;
/**
Pre-condition:
 - alkoholprocent < 100
 - antalLiterPåfyldt < fadStr
 */
    public Fad(String fadType, double fadStr, String newSpiritBatchNr, double antalLiterPåFyldt, double alkoholProcent, String medarbejderintialer, Lager lager) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.newSpiritBatchNr = newSpiritBatchNr;
        this.antalLiterPåFyldt = antalLiterPåFyldt;
        this.alkoholProcent = alkoholProcent;
        this.medarbejderintialer = medarbejderintialer;
        this.setLager(lager);
    }


    public Fad(String fadType, double fadStr, Lager lager) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.setLager(lager);
    }

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