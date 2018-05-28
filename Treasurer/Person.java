public class Person {
    private String nickname;
    private String fullname;
    private Dollar balance;

    public Person(String nickname, String fullname, double balance) {
        this(nickname, fullname, new Dollar(balance));
    }

    public Person(String nickname, String fullname, Dollar balance) {
        this.nickname = nickname;
        this.fullname = fullname;
        this.balance = balance;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getFullname() {
        return this.fullname;
    }

    public Dollar getBalance() {
        return this.balance;
    }

    @Override
    public int hashCode() {
        return 31 * this.balance.hashCode()
            + 31 * this.fullname.hashCode()
            + 31 * this.nickname.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) { return true; }
        if (!(other instanceof Person)) { return false; }
        Person o = (Person) other;
        return this.fullname.equals(o.getFullname())
            && this.nickname.equals(o.getNickname())
            && this.balance.equals(o.getBalance());
    }

    @Override
    public String toString() {
        return this.fullname + ": Balance " + this.balance.toString();
    }
}
