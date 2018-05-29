import java.util.ArrayList;

public class WorkItem {
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
    public boolean isComplete() {
        boolean toReturn = true;
        for (TaskItem t: tasks) {
            toReturn = toReturn && t.getComplete();
        }
        return toReturn;
    }

    public String toJSON() {
        String taskJson = "";
        for (TaskItem t: tasks) {
            taskJson += t.toJSON();
        }
        return String.format(
            "  WorkItem: {%n    name: %s%n    description: %s%n    tasks: %s%n}%n",
            this.name, this.description, taskJson);
    }

    public static WorkItem fromJSON(String json) {
        Pattern taskItems = Pattern("TaskItem: \\{.*?\\}");
        Matcher taskItemsMatch = taskItems.matcher(json);
        ArrayList<TaskItem> tasks = new ArrayList<>();
        while (taskItemsMatch.find()) {
            String taskItem = taskItemsMatch.group();
            tasks.add(TaskItem.fromJson(taskItem));
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
