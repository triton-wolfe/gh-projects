import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class WorkPlanner extends Application {

    @Override
    public void start(Stage stage) {
        File saveJson = new File("./saved.item");
        ArrayList<ProjectItem> projects = loadFile(saveJson);
        System.out.println(projects);
        stage.setOnCloseRequest((e) -> saveFile(saveJson, projects));



        stage.show();
    }

    public ArrayList<TaskItem> getAllTaskItems(ArrayList<ProjectItem> projs) {
        ArrayList<TaskItem> toReturn = new ArrayList<>();
        for (ProjectItem p: projs) {
            ArrayList<WorkItem> wrk = p.getWorkItems();
            for (WorkItem w: wrk) {
                toReturn.addAll(w.getTasks());
            }
        }
        Collections.sort(toReturn, new Comparator() {
            public int compare(Object a, Object b) {
                LocalDateTime abegin = ((TaskItem) a).getStart();
                LocalDateTime bbegin = ((TaskItem) b).getStart();
                int comp = 0;
                if (abegin.isBefore(bbegin)) {
                    comp = 1;
                } else if (abegin.isAfter(bbegin)) {
                    comp = -1;
                }
                return comp;
            }
        });
        return toReturn;
    }

    public ArrayList<ProjectItem> loadFile(File file) {
        ArrayList<ProjectItem> toReturn = new ArrayList<>();
        try {
            FileReader testReader = new FileReader(file);
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
            for (String ps: projectStrings) {
                if (!ps.equals(" ")) {
                    toReturn.add(ProjectItem.fromJSON(ps));
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return toReturn;
    }

    public void saveFile(File file, ArrayList<ProjectItem> projItems) {
        String toSave = "";
        for (ProjectItem p: projItems) {
            toSave += p.toJSON();
        }
        try {
            FileWriter fh = new FileWriter(file, false);
            fh.write(toSave);
            fh.close();
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
