public class Task {
    private String name;
    private String description;
    private boolean complete;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.complete = false;
    }

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public boolean getComplete() { return this.complete; }
    public void setComplete(boolean complete) { this.complete = complete; }

    public String toJSON() {
        return String.format(
            "Task: {%nname: %s%ndescription: %s%ncomplete: %s%n}%n",
            this.name, this.description, this.complete);
    }
}
