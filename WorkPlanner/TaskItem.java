import java.time.LocalDateTime;

public class TaskItem {
    private String name;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean complete;

    public TaskItem(String name, String description,
                LocalDateTime start, LocalDateTime end, boolean complete) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.complete = complete;
    }

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public boolean getComplete() { return this.complete; }
    public LocalDateTime getStart() { return this.start; }
    public LocalDateTime getEnd() { return this.end; }
    public void setComplete(boolean complete) { this.complete = complete; }

    public String toJSON() {
        return String.format(
            "    TaskItem: {%n      name: %s%n      description: %s%n      start: %s%n"
            + "      end: %s%n      complete: %s%n  }%n",
            this.name, this.description, this.start.toString(),
            this.end.toString(), this.complete);
    }

    public static TaskItem fromJSON(String json) {
        String[] tokens = json.split(String.format("%n"));
        String nameParsed = ""; String descriptionParsed = "";
        LocalDateTime startParsed = LocalDateTime.now();
        LocalDateTime endParsed = LocalDateTime.now();
        boolean completeParsed = false;
        for (String token: tokens) {
            if (token.startsWith("      name:")) {
                nameParsed = token.substring(12);
            } else if (token.startsWith("      description:")) {
                descriptionParsed = token.substring(19);
            } else if (token.startsWith("      start:")) {
                startParsed = LocalDateTime.parse(token.substring(13));
            } else if (token.startsWith("      end:")) {
                endParsed = LocalDateTime.parse(token.substring(11));
            } else if (token.startsWith("      complete:")) {
                completeParsed = Boolean.parseBoolean(token.substring(16));
            }
        }
        return new TaskItem(nameParsed, descriptionParsed,
            startParsed, endParsed, completeParsed);
    }
}
