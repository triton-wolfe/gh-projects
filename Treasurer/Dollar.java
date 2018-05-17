public class Dollar {
    private int cents;
    private int dollars;

    public Dollar(int dollars) {
        this(dollars, 0);
    }
    public Dollar(int dollars, int cents) {
        this.cents = cents % 100;
        this.dollars = dollars + (cents / 100);
    }

    public void deposit(Dollar other) {
        this.cents = (this.cents + other.getCents) % 100;
        this.dollars += other.getDollars;
        this.dollars += (this.cents + other.getCents) / 100;
    }
a
    public void deposit(int dollars) {
        this.deposit(dollars, 0);
    }

    public void deposit(int dollars, int cents) {
        this.cents = (this.cents + cents) % 100;
        this.dollars += dollars;
        this.dollars += (this.cents + cents) / 100;
    }

    public void charge(Dollar other) {
        this.cents = (this.cents - other.getCents) % 100;
        this.dollars -= other.getDollars;
        this.dollars -= (this.cents - other.getCents) / 100;
    }

    public void charge(int dollars) {
        this.charge(dollars, 0);
    }

    public void charge(int dollars, int cents) {
        this.cents = (this.cents - cents) % 100;
        this.dollars -= dollars;
        this.dollars -= (this.cents - cents) / 100;
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
