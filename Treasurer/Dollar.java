public class Dollar {
    private int cents;
    private int dollars;

    public Dollar(int dollars, int cents) {
        this.dollars = dollars;
        this.cents = cents;
    }

    

    @Override
    public int hashCode() {
        return 100*dollars + cents;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) { return true; }
        if (!other instanceof dollar) { return false; }
        Dollar o = (Dollar) other;
        return this.toString == o.toString;
    }

    @Override
    public String toString() {
        return "$" + this.dollars + "." + this.cents;
    }
}
