package application;


/**
 * Enum, der repræsenterer de forskellige flasketypers kapacitet.
 */
public enum Flasketype {
    LILLE(0.3),
    MEDIUM(0.5),
    STOR(1),
    JUBILÆUMSFLASKE(0.75);

    private double capacity;

    /**
     * Constructor for Flasketype-enum.
     *
     * @param capacity Kapacitet for flasketypen i liter. Præbetingelse: capacity skal være en positiv double-værdi.
     */
    Flasketype(double capacity) {
        this.capacity = capacity;
    }

    /**
     * Getter-metode for kapacitet.
     *
     * @return Kapaciteten for flasketypen i liter.
     */
    public double getCapacity() {
        return capacity;
    }


}