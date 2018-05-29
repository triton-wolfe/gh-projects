import java.util.ArrayList;

public class WorkItem {
    private String name;
    private String description;
    private ArrrayList<Task> tasks;

    public WorkItem(String name, String description) {
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public ArrayList<Task> getTasks() { return this.tasks; }
    public boolean isComplete() {
        toReturn = true;
        for (Task t: tasks) {
            toReturn = toReturn && t.getComplete();
        }
        return toReturn;
    }

    public String toJSON() {
        String taskJson = "";
        for (Task t: tasks) {
            taskJson += t.toJSON();
        }
        return String.format(
            "WorkItem {%nname: %s%ndescription: %s%ntasks: %s%n}%n",
            this.name, this.description, taskJson);
    }

}
