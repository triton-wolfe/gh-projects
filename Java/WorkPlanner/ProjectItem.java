import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ProjectItem extends SaveableItem {
    private String name;
    private String classification;
    private ArrayList<WorkItem> workItems;

    public ProjectItem(String name, String classification) {
        this(name, classification, new ArrayList<WorkItem>());
    }

    public ProjectItem(String name, String classification, ArrayList<WorkItem> workItems) {
        this.name = name;
        this.classification = classification;
        this.workItems = workItems;
    }

    public String getName() { return this.name; }
    public String getClassification() { return this.classification; }
    public ArrayList<WorkItem> getWorkItems() { return this.workItems; }
    public int getNumberOfWorkItems() { return this.workItems.size(); }

    public String toXML() {
        String toReturn = String.format("  <ProjectItem>%n"
            + "    <Name>%s</Name>%n"
            + "    <Classification>%s</Classification>%n",
            this.name, this.classification);
        for (WorkItem t: this.workItems) {
            toReturn += t.toXML();
        }
        toReturn += String.format("  </ProjectItem>%n");
        return toReturn;
    }

    public String toJSON() {
        String workItemJson = "";
        for (WorkItem w: workItems) {
            workItemJson += w.toJSON();
        }
        return String.format(
            "Project: {%n  name: %s%n  classification: %s%n  workItems: %s%n}",
            this.name, this.classification, workItemJson);
    }

    public static ProjectItem fromXML(String xml) {
        String name = SaveableItem.getXMLTag(xml, "Name");
        String classification = SaveableItem.getXMLTag(xml, "Classification");
        ArrayList<String> workItemStr = SaveableItem.getXMLTags(xml, "WorkItem");
        ArrayList<WorkItem> workItems = new ArrayList<>();
        for (String wi: workItemStr) {
            workItems.add(WorkItem.fromXML(wi));
        }
        return new ProjectItem(name, classification, workItems);
    }

    public static ProjectItem fromJSON(String json) {
        ArrayList<String> workItemString = SaveableItem.getNestedJSON(json);
        ArrayList<WorkItem> workItems = new ArrayList<>();
        for (String wi: workItemString) {
            workItems.add(WorkItem.fromJSON(wi));
        }
        String[] tokens = json.split(String.format("%n"));
        String name = "";
        String classification = "";
        for (String token: tokens) {
            if (token.startsWith("  name:")) {
                name = token.substring(8);
            } else if (token.startsWith("  classification:")) {
                classification = token.substring(18);
            }
        }
        return new ProjectItem(name, classification, workItems);
    }
}
