import java.util.ArrayList;

public class WorkItem {
    private String name;
    private String description;
    private ArrayList<TaskItem> tasks;

    public WorkItem(String name, String description) {
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
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

    public static WorkItem fromJson(String json) {
        Pattern taskItems = Pattern("TaskItem: \\{.*?\\}");
        Matcher taskItemsMatch = workItems.matcher(json);
        ArrayList<TaskItem> tasks = new ArrayList<>();
        while (taskItemsMatch.find()) {
            String taskItem = taskItemsMatch.group();
            tasks.add(TaskItem.fromJson(taskItem));
        }
    }
}
