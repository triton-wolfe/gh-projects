public class Dollar {
    private double dollars;

    public Dollar(double dollars) {
        this.dollars = dollars;
    }

    public void deposit(double dollars) {
        this.dollars += dollars;
    }

    public void charge(double dollars) {
        this.dollars -= dollars;
    }

    @Override
    public int hashCode() {
        return (int) (100 * this.dollars);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) { return true; }
        if (!(other instanceof Dollar)) { return false; }
        Dollar o = (Dollar) other;
        return this.toString().equals(o.toString());
    }

    @Override
    public String toString() {
        return String.format("$%.2f",this.dollars);
    }

}
