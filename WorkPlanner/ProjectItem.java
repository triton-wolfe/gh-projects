import java.util.ArrayList;

public class ProjectItem extends SaveableItem {
    private String name;
    private String classification;
    private ArrayList<WorkItem> workItems;

    public ProjectItem(String name, String classification) {
        this(name, classification, new ArrayList<WorkItem>())
    }

    public ProjectItem(String name, String classification, ArrayList<WorkItem> workItems) {
        this.name = name;
        this.classification = classification;
        this.workItems = workItems;
    }

    public String getName() { return this.name; }
    public String getClassification() { return this.classification; }
    public ArrayList<WorkItem> getWorkItems() { return this.workItems; }

    public String toJSON() {
        String workItemJson = "";
        for (WorkItem w: workItems) {
            workItemJson += w.toJSON();
        }
        return String.format(
            "Project: {%n  name: %s%n  classification%s%n  workItems: %s%n}%n",
            this.name, this.classification, workItemJson);
    }

    public static ProjectItem fromJSON(String json) {
        Pattern workItems = Pattern("WorkItem: \\{.*?\\}");
        Matcher workItemsMatch = workItems.matcher(json);
        ArrayList<WorkItem> work = new ArrayList<>();
        while (workItemsMatch.find()) {
            String workItem = workItemsMatch.group();
            work.add(WorkItem.fromJson(workItem));
        }
        String[] tokens = json.split(String.format("%n"));
        String name = "";
        String classification = "";
        for (String token: tokens) {
            if (token.startsWith("  name:")) {
                name = token.substring(8);
            } else if (token.startsWith("  classification:")) {
                classification = token.substring(22);
            }
        }
    }
}
