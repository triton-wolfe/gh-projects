import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AddScreen {

    private final ArrayList<ProjectItem> projects;
    private final ArrayList<WorkItem> workItems;
    private final ArrayList<TaskItem> tasks;
    private final Stage prjPop;
    private final Stage wrkPop;
    private final Stage tskPop;

    public AddScreen() {
        projects = new ArrayList<>();
        workItems = new ArrayList<>();
        tasks = new ArrayList<>();
        Label prjHeader = new Label("New Project Item");
        Label namePrjLabel = new Label("Project Name: ");
        TextField newPrjName = new TextField();
        Label classPrjLabel = new Label("Project Classification: ");
        TextField newPrjClass = new TextField();
        Button prjSave = new Button("Add new Project");
        VBox prjScene = new VBox(prjHeader, new HBox(namePrjLabel, newPrjName),
            new HBox(classPrjLabel, newPrjClass), prjSave);
        this.prjPop = new Stage();
        prjSave.setOnAction(e -> {
                projects.add(new ProjectItem(newPrjName.getText(), newPrjClass.getText()));
                prjPop.close();
            });
        this.prjPop.setScene(new Scene(prjScene));

        Label wrkHeader = new Label("New Work Item");
        Label nameWrkLabel = new Label("Work Item Name: ");
        TextField newWrkName = new TextField();
        Label classWrkLabel = new Label("Work Item Description: ");
        TextArea newWrkClass = new TextArea();
        newWrkClass.setPrefRowCount(3);
        newWrkClass.setWrapText(true);
        Button wrkSave = new Button("Add new Work Item");
        VBox wrkScene = new VBox(wrkHeader, new HBox(nameWrkLabel, newWrkName),
            new HBox(classWrkLabel, newWrkClass), wrkSave);
        this.wrkPop = new Stage();
        wrkSave.setOnAction(e -> {
                workItems.add(new WorkItem(newWrkName.getText(), newWrkClass.getText()));
                wrkPop.close();
            });
        this.wrkPop.setScene(new Scene(wrkScene));

        Label tskHeader = new Label("New Task Item");
        Label nameTskLabel = new Label("Task Name: ");
        TextField newTskName = new TextField();
        Label classTskLabel = new Label("Task Description: ");
        TextArea newTskClass = new TextArea();
        newTskClass.setPrefRowCount(3);
        newTskClass.setWrapText(true);
        Button tskSave = new Button("Add new Task");
        VBox tskScene = new VBox(tskHeader, new HBox(nameTskLabel, newTskName),
            new HBox(classTskLabel, newTskClass), tskSave);
        this.tskPop = new Stage();
        tskSave.setOnAction(e -> {
                // tasks.add(new TaskItem(newTskName.getText(), newTskClass.getText()));
                tskPop.close();
            });
        this.tskPop.setScene(new Scene(tskScene));

    }

    public void addProjectTo(ArrayList<ProjectItem> prjs) {
        this.projects.clear();
        this.projects.addAll(prjs);
        this.prjPop.showAndWait();
        prjs.clear();
        prjs.addAll(this.projects);
    }

    public void addWorkItemTo(ArrayList<WorkItem> wrks) {
        this.workItems.clear();
        this.workItems.addAll(wrks);
        this.wrkPop.showAndWait();
        wrks.clear();
        wrks.addAll(this.workItems);
    }

    public void addTaskTo(ArrayList<TaskItem> tsks) {
        this.tasks.clear();
        this.tasks.addAll(tsks);
        this.tskPop.showAndWait();
        tsks.clear();
        tsks.addAll(this.tasks);
    }
}
