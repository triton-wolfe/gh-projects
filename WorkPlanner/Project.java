import java.util.ArrayList;

public class Project {
    private String name;
    private String classification;
    private ArrayList<WorkItem> workItems;

    public Project(String name, String classification) {
        this.name = name
        this.classification = classification;
        this.workItems = new ArrayList<>();
    }

    public String getName() { return this.name; }
    public String getClassification() { return this.classification; }
    public ArrayList<WorkItems> getWorkItems() { return this.workItems; }

    public String toJSON() {
        String workItemJson = "";
        for (WorkItem w: workItems) {
            workItemJson += w.toJSON();
        }
        return String.format(
            "Project {%nname: %s%nclassification: %s%nworkItems: %s%n}%n",
            this.name, this.classification, workItemJson);
    }
}
