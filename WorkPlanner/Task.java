import java.time.LocalDateTime;

public class Task {
    private String name;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean complete;

    public Task(String name, String description,
                LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.complete = false;
    }

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public boolean getComplete() { return this.complete; }
    public LocalDateTime getStart() { return this.start; }
    public LocalDateTime getEnd() { return this.end; }
    public void setComplete(boolean complete) { this.complete = complete; }

    public String toJSON() {
        return String.format(
            "Task: {%nname: %s%ndescription: %s%nstart: %s%n"
            + "end: %s%ncomplete: %s%n}%n",
            this.name, this.description, this.start.toString(),
            this.end.toString(), this.complete);
    }

    public static Task fromJson(String json) {
        String[] tokens = json.split(String.format("%n"));
        for (String token: tokens) {
            if (token.startsWith("name:")) {
                String name = token.subString(6);
            } else if (token.startsWith("description:")) {
                String description = token.substring(13);
            } else if (token.startsWith("start:")) {
                LocalDateTime start = LocalDateTime.parse(token.substring(7));
            } else if (token.startsWith("end:")) {
                LocalDateTime end = LocalDateTime.parse(token.substring(5));
            } else if (token.startsWith("complete:")) {
                boolean complete = Boolean.parse(token.substring(10));
            }
        }
        return new Task(name, description, start, end, complete);
    }
}
