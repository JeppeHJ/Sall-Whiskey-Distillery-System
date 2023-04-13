package application;

import java.time.LocalDate;

public class LagretVæskesFadHistorik {
    private Fad fad;
    private LocalDate fraDato;
    private LocalDate tilDato;

    /**
     * Constructor for LagretVæskesFadHistorik klassen.
     *
     * @param fad     Fad objektet, der repræsenterer det fad, som historikken er tilknyttet.
     * @param fraDato LocalDate objektet, der repræsenterer datoen, hvor væsken er blevet lagt på fadet.
     * @param tilDato LocalDate objektet, der repræsenterer datoen, hvor væsken er blevet fjernet fra fadet.
     *                Præbetingelse:
     *                fad må ikke være null.
     *                fraDato må ikke være null og skal være før eller lig med tilDato.
     *                tilDato kan være null, men hvis det er angivet, skal det være efter eller lig med fraDato.
     */
    public LagretVæskesFadHistorik(Fad fad, LocalDate fraDato, LocalDate tilDato) {
        this.fad = fad;
        this.fraDato = fraDato;
        this.tilDato = tilDato;
    }

    /**
     * Returnerer det Fad objekt, der er forbundet med denne historik.
     *
     * @return Det Fad objekt, der er forbundet med denne historik.
     */
    public Fad getFad() {
        return fad;
    }

    /**
     * Returnerer fra-datoen for denne historik.
     *
     * @return Fra-datoen som en LocalDate værdi.
     */
    public LocalDate getFraDato() {
        return fraDato;
    }

    /**
     * Returnerer til-datoen for denne historik.
     *
     * @return Til-datoen som en LocalDate værdi.
     */
    public LocalDate getTilDato() {
        return tilDato;
    }

    /**
     * Angiver det Fad objekt, der er forbundet med denne historik.
     *
     * @param fad Det Fad objekt, der skal være forbundet med denne historik.
     *            Præbetingelse: fad skal være et gyldigt Fad objekt.
     */
    public void setFad(Fad fad) {
        this.fad = fad;
    }

    /**
     * Angiver fra-datoen for denne historik.
     *
     * @param fraDato Fra-datoen som en LocalDate værdi.
     *                Præbetingelse: fraDato skal være en gyldig LocalDate objekt og bør ikke være en fremtidig dato.
     */
    public void setFraDato(LocalDate fraDato) {
        this.fraDato = fraDato;
    }

    /**
     * Angiver til-datoen for denne historik.
     *
     * @param tilDato Til-datoen som en LocalDate værdi.
     *                Præbetingelse: tilDato skal være en gyldig LocalDate objekt og bør ikke være en fremtidig dato.
     */
    public void setTilDato(LocalDate tilDato) {
        this.tilDato = tilDato;
    }

    /**
     * Returnerer en strengrepræsentation af denne historik.
     *
     * @return En strengrepræsentation af denne historik, der indeholder fad, fra dato og til dato.
     */
    @Override
    public String toString() {
        return "Fad: " + fad + ", Fra dato: " + fraDato + ", Til dato: " + (tilDato == null ? "nu" : tilDato);
    }
}