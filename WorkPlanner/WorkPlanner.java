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

    private TableView<TaskItem> taskTableView;

    @Override
    public void start(Stage stage) {
        File saveJson = new File("./saved.item");
        ArrayList<ProjectItem> projects = loadFile(saveJson);
        stage.setOnCloseRequest(e -> saveFile(saveJson, projects));
        NavigatorScreen navScreen = new NavigatorScreen(projects);

        stage.setScene(navScreen.getView());


        // Button addProject = new Button("Add new Project");
        // Button addWork = new Button("Add new Work Item");
        // Button addTask = new Button("Add new Task Item");
        // HBox addButtons = new HBox(addProject, addWork, addTask);
        //
        //
        // ArrayList<TaskItem> tasks = getAllTaskItems(projects);
        // VBox taskTableScreen = new VBox(getTaskTable(tasks), addButtons);
        // Scene taskTable = new Scene(taskTableScreen);
        // addProject.setOnAction(e -> {
        //         addNewProjectScreen(projects);
        //         this.taskTableView.refresh();
        //     });
        // stage.setScene(taskTable);
        stage.show();
    }

    public void addNewProjectScreen(ArrayList<ProjectItem> projects) {
        TableView<ProjectItem> table =
            new TableView<ProjectItem>(FXCollections.observableArrayList(projects));
        TableColumn<ProjectItem, String> names = new TableColumn<>("Project");
        names.setCellValueFactory(new PropertyValueFactory<ProjectItem, String>("name"));
        TableColumn<ProjectItem, String> classification = new TableColumn<>("Classification");
        classification.setCellValueFactory(new PropertyValueFactory<ProjectItem, String>("classification"));
        TableColumn<ProjectItem, String> numWorkItems = new TableColumn<>("Number of Work Items");
        numWorkItems.setCellValueFactory(new PropertyValueFactory<ProjectItem, String>("numberOfWorkItems"));
        TableColumn<ProjectItem, String> attributes = new TableColumn<>("Attributes");
        attributes.getColumns().setAll(classification, numWorkItems);
        table.getColumns().setAll(names,attributes);

        Label header = new Label("New Project Item");
        Label nameLabel = new Label("Project Name: ");
        TextField newName = new TextField();
        Label classLabel = new Label("Project Classification: ");
        TextField newClass = new TextField();
        Button save = new Button("Add new Project");
        VBox scene = new VBox(table, header, new HBox(nameLabel, newName),
            new HBox(classLabel, newClass), save);
        Stage popup = new Stage();
        save.setOnAction(e -> {
                projects.add(new ProjectItem(newName.getText(), newClass.getText()));
                popup.close();
            });
        popup.setScene(new Scene(scene));
        popup.showAndWait();
    }

    public TableView<TaskItem> getTaskTable(ArrayList<TaskItem> tasks) {
        this.taskTableView = new TableView<TaskItem>(FXCollections.observableArrayList(tasks));
        TableColumn<TaskItem, String> names = new TableColumn<>("Task");
        names.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("name"));
        TableColumn<TaskItem, String> descriptions = new TableColumn<>("Description");
        descriptions.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("description"));
        TableColumn<TaskItem, String> start = new TableColumn<>("Start Time");
        start.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("startShort"));
        TableColumn<TaskItem, String> complete = new TableColumn<>("Finished");
        complete.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("complete"));
        TableColumn<TaskItem, String> attributes = new TableColumn<>("Attributes");
        attributes.getColumns().setAll(descriptions,start,complete);
        this.taskTableView.getColumns().setAll(names,attributes);
        return this.taskTableView;
    }

    public ArrayList<TaskItem> getAllTaskItems(ArrayList<ProjectItem> projs) {
        ArrayList<TaskItem> toReturn = new ArrayList<>();
        for (ProjectItem p: projs) {
            ArrayList<WorkItem> wrk = p.getWorkItems();
            for (WorkItem w: wrk) {
                ArrayList<TaskItem> tsk = w.getTasks();
                for (TaskItem t: tsk) {
                    toReturn.add(t);
                }
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
