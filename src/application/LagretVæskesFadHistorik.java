package application;

import java.time.LocalDate;

public class LagretVæskesFadHistorik {
    private Fad fad;
    private LocalDate fraDato;
    private LocalDate tilDato;

     LagretVæskesFadHistorik(Fad fad, LocalDate fraDato, LocalDate tilDato) {
        this.fad = fad;
        this.fraDato = fraDato;
        this.tilDato = tilDato;
    }

    // Getters
    public Fad getFad() {
        return fad;
    }

    public LocalDate getFraDato() {
        return fraDato;
    }

    public LocalDate getTilDato() {
        return tilDato;
    }

    // Setters
    public void setFad(Fad fad) {
        this.fad = fad;
    }

    public void setFraDato(LocalDate fraDato) {
        this.fraDato = fraDato;
    }

    public void setTilDato(LocalDate tilDato) {
        this.tilDato = tilDato;
    }

    @Override
    public String toString() {
        return "Fad: " + fad + ", Fra dato: " + fraDato + ", Til dato: " + (tilDato == null ? "nu" : tilDato);
    }
}