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


    public Fad(int id, String fadType, double fadStr) {
        this.id = id;
        this.fadType = fadType;
        this.fadStr = fadStr;
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