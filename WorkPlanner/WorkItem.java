import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WorkItem extends SaveableItem {
    private String name;
    private String description;
    private ArrayList<TaskItem> tasks;

    public WorkItem(String name, String description) {
        this(name, description, new ArrayList<TaskItem>());
    }

    public WorkItem(String name, String description, ArrayList<TaskItem> tasks) {
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public ArrayList<TaskItem> getTasks() { return this.tasks; }
    public int getNumberOfTasks() { return this.tasks.size(); }
    public boolean isComplete() {
        boolean toReturn = true;
        for (TaskItem t: tasks) {
            toReturn = toReturn && t.getComplete();
        }
        return toReturn;
    }

    public String toXML() {
        String toReturn = String.format("    <WorkItem>%n"
            + "      <Name>%s</Name>%n"
            + "      <Description>%s</Description>%n",
            this.name, this.description);
        for (TaskItem t: this.tasks) {
            toReturn += t.toXML();
        }
        toReturn += String.format("    </WorkItem>%n");
        return toReturn;
    }

    public String toJSON() {
        String taskJson = "";
        for (TaskItem t: this.tasks) {
            taskJson += t.toJSON();
        }
        return String.format(
            "  WorkItem: {%n    name: %s%n    description: %s%n    tasks: %s%n  }%n",
            this.name, this.description, taskJson);
    }

    public static WorkItem fromXML(String xml) {
        String name = SaveableItem.getXMLTag(xml, "Name");
        String description = SaveableItem.getXMLTag(xml, "Description");
        ArrayList<String> taskStr = SaveableItem.getXMLTags(xml, "TaskItem");
        ArrayList<TaskItem> tasks = new ArrayList<>();
        for (String t: taskStr) {
            tasks.add(TaskItem.fromXML(t));
        }
        return new WorkItem(name, description, tasks);
    }

    public static WorkItem fromJSON(String json) {
        ArrayList<String> taskString = SaveableItem.getNestedJSON(json);
        ArrayList<TaskItem> tasks = new ArrayList<>();
        for (String t: taskString) {
            tasks.add(TaskItem.fromJSON(t));
        }
        String[] tokens = json.split(String.format("%n"));
        String name = "";
        String description = "";
        for (String token: tokens) {
            if (token.startsWith("    name:")) {
                name = token.substring(10);
            } else if (token.startsWith("    description:")) {
                description = token.substring(17);
            }
        }
        return new WorkItem(name, description, tasks);
    }
}
