import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

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
        stage.setOnCloseRequest(e -> saveFile(saveJson, projects));
        ProjectViewer viewer = new ProjectViewer(projects);

        stage.setScene(new Scene(viewer.getView()));

        stage.show();
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
            testReader.close();
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
