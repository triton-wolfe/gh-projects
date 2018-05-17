public class Dollar {
    private int cents;
    private int dollars;

    public Dollar(int dollars, int cents) {
        this.cents = cents % 100;
        this.dollars = dollars + (cents / 100);
    }

    public void add(Dollar other) {
        this.cents = (this.cents + other.getCents) % 100;
        this.dollars += other.getDollars;
        this.dollars += (this.cents + other.getCents) / 100;
    }

    public int getDollars() {
        return this.dollars;
    }

    public int getCents() {
        return this.dollars;
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
