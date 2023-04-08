package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class Whisky {
    private String navn;
    private static int count;
    private int id;
    private LocalDate tappetDato;
    private double alder;
    private double alkoholProcent;
    private double liter;
    private Flasketype flasketype;

    private ArrayList<LagretVæske> historik = new ArrayList<>();
    private LagretVæske væske;
    private double fortyndelsesProcent;
    private String vandKilde;

    public Whisky(String navn, LocalDate tappetDato, double alkoholProcent, double liter, Flasketype flasketype, LagretVæske væske, String vandKilde, double fortyndelsesProcent) {
        this.navn = navn;
        this.tappetDato = tappetDato;
        this.alkoholProcent = alkoholProcent;
        this.liter = liter;
        this.flasketype = flasketype;
        this.historik.add(væske);
        this.væske = væske;
        this.id = count++;
        this.vandKilde = vandKilde;
        this.fortyndelsesProcent = fortyndelsesProcent;
    }

    public LagretVæske getVæske() {
        return væske;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getTappetDato() {
        return tappetDato;
    }

    public double getAlder() {
        return alder;
    }

    public ArrayList<LagretVæske> getHistorik() {
        return historik;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public double getLiter() {
        return liter;
    }

    public Flasketype getFlasketype() {
        return flasketype;
    }

    public String toString() {
        return this.navn;
    }
}
