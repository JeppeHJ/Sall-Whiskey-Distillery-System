package application;

public enum Flasketype {
    LILLE(0.3),
    MEDIUM(0.5),
    STOR(1),
    JUBILÆUMSFLASKE(0.75);

    private double capacity;

    Flasketype(double capacity) {
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }


}