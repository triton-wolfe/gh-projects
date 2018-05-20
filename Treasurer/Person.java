public class Person {
    private String nickname;
    private String fullname;
    private Dollar balance;

    public Person(String name) {
        this(name, name, new Dollar(0))
    }

    public Person(String name, double balance) {
        this(name, name, new Dollar(balance));
    }

    public Person(String nickname, String fullname) {
        this(nickname, fullname, new Dollar(0));
    }

    public Person(String nickname, String fullname, double balance) {
        this(nickname, fullname, new Dollar(balance))
    }

    public Person(String nickname, String fullname, Dollar balance) {
        this.nickname = nickname;
        this.fullname = fullname;
        this.balance = balance;
    }

    public String getNickname() {
        return this.name;
    }

    public Dollar getBalance() {
        return this.balance;
    }

    @Override
    public int hashCode() {
        return 31 * this.balance.hashCode()
            + 31 * this.name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) { return true; }
        if (!(other instanceof Person)) { return false; }
        Person o = (Person) other;
        return this.name.equals(o.getName())
            && this.balance.equals(o.getBalance());
    }

    @Override
    public String toString() {
        return this.name + ": Balance " + this.balance.toString();
    }
}
