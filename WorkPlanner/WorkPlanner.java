import javafx.application.Application;
import javafx.stage.stage;
import javafx.scene.scene;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Collections;
import java.util.ArrayList;

public class WorkPlanner extends Application {

    @Overide
    public void start(Stage stage) {
        File saveJson = new File("./saved.json");
        ArrayList<ProjectItem> projects = loadFile(saveJson);
        stage.setOnCloseRequest(() -> saveFile(saveJson, projects));



        stage.show();
    }

    public ArrayList<TaskItem> getAllTaskItems(ArrayList<ProjectItem> projs) {
        ArrayList<TaskItem> toReturn = new ArrayList<>();
        for (ProjectItem p: projs) {
            ArrayList<WorkItem> wrk = p.getWorkItems();
            for (WorkItem w: wrk) {
                toReturn.addAll(w.getTaskItems());
            }
        }
        Collections.sort(toReturn, new Comparator() {
            public int compare(WorkItem a, WorkItem b) {
                int comp = 0;
                if (a.isBefore(b)) {
                    comp = 1;
                } else if (a.isAfter(b)) {
                    comp = -1;
                }
                return toReturn;
            }
        });
        return toReturn
    }

    public ArrayList<ProjectItem> loadFile(File file) {
        File test = new File("./test.txt");
        try {
            FileReader testReader = new FileReader(test);

        StringBuilder jsonBuilder = new StringBuilder();
        int letter = ' ';
        while (letter != -1) {
            jsonBuilder.append((char) letter);
            try {
                letter = testReader.read();
            } catch(IOException e) {
                letter = ' ';
            }
        }
        String[] projectStrings = jsonBuilder.toString().split("Project");
        ArrayLsit<ProjectItem> toReturn = new ArrayList<>();
        for (String ps: projectStrings) {
            toReturn.add(ProjectItem.fromJson(ps));
        }
            return toReturn;
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveFile(File file, ArrayList<WorkItem> workItems) {
        String toSave = "";
        for (WorkItem wi: workItems) {
            toSave += wi.toJSON();
        }
        try {
            FileWriter fh = new FileWriter(file, false);
            fh.write();
            fh.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}