import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ProjectViewer {

    private static int rows = 7;
    private ArrayList<ProjectItem> projects;
    private ArrayList<WorkItem> workItems;
    private ArrayList<TaskItem> tasks;
    private HBox view;


    public ProjectViewer(ArrayList<ProjectItem> items) {
        this.projects = items;
        this.workItems = (this.projects.size() == 0) ? new ArrayList<WorkItem>() : this.projects.get(0).getWorkItems();
        this.tasks = (this.workItems.size() == 0) ? new ArrayList<TaskItem>() : this.workItems.get(0).getTasks();
        Button addPrjButton = new Button("Add Project");
        AddScreen addScreen = new AddScreen();
        addPrjButton.setOnAction(e -> {
                addScreen.addProjectTo(this.projects);
                refresh();
            });
        VBox projectTable = new VBox(getProjectTable(), addPrjButton);
        Button addWrkButton = new Button("Add WorkItem");
        addWrkButton.setOnAction(e -> {
                addScreen.addWorkItemTo(this.workItems);
                refresh();
            });
        VBox workTable = new VBox(getWorkTable(), addWrkButton);
        Button addTskButton = new Button("Add Task");
        addTskButton.setOnAction(e -> {
                addScreen.addTaskTo(this.tasks);
                refresh();
            });
        VBox taskTable = new VBox(getTaskTable(), addTskButton);

        this.view = new HBox(projectTable, workTable, taskTable);
        this.view.setPrefHeight(200);

    }

    public void refresh() {
        ((VBox) this.view.getChildren().get(0)).getChildren().set(0, getProjectTable());
        ((VBox) this.view.getChildren().get(1)).getChildren().set(0, getWorkTable());
        ((VBox) this.view.getChildren().get(2)).getChildren().set(0, getTaskTable());
    }

    private void highlight(int ind, VBox collection) {
        for (int i = 1; i < collection.getChildren().size(); i++) {
            collection.getChildren().get(i).setStyle((ind == i)
            ? "-fx-border-width: 1; -fx-border-color: rgb(0,0,255,.5)"
            : "-fx-border-width: 0 0 1 0; -fx-border-color: black"
            );
        }
    }

    private VBox getProjectTable() {
        Label projectNamesHeader = new Label(" Name ");
        Label projectClassHeader = new Label(" Class ");
        Label projectNumOfHeader = new Label(" Num Child ");
        projectNamesHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        projectNamesHeader.setPrefWidth(80);
        projectNamesHeader.setAlignment(Pos.TOP_CENTER);
        projectClassHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        projectClassHeader.setPrefWidth(80);
        projectClassHeader.setAlignment(Pos.TOP_CENTER);
        projectNumOfHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        projectNumOfHeader.setPrefWidth(80);
        projectNumOfHeader.setAlignment(Pos.TOP_CENTER);
        VBox projectNames = new VBox();
        projectNames.setAlignment(Pos.TOP_CENTER);
        projectNames.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        VBox projectClass = new VBox();
        projectClass.setAlignment(Pos.TOP_CENTER);
        projectClass.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        VBox projectNumChildren = new VBox();
        projectNumChildren.setAlignment(Pos.TOP_CENTER);
        projectNumChildren.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        int i = 0;
        for (ProjectItem p: this.projects) {
            Label temp1 = new Label(" " + p.getName() + " ");
            temp1.setPrefWidth(80);
            temp1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp1.setOnMouseEntered(e -> {
                int ind = projectNames.getChildren().indexOf(temp1);
                highlight(ind, projectNames);
                highlight(ind, projectClass);
                highlight(ind, projectNumChildren);
            });
            temp1.setOnMouseClicked(e -> {
                int ind = projectNames.getChildren().indexOf(temp1);
                this.workItems = this.projects.get(ind).getWorkItems();
                this.tasks = (this.workItems.size() == 0) ? new ArrayList<TaskItem>() : this.workItems.get(0).getTasks();
                refresh();
            });
            projectNames.getChildren().add(temp1);
            Label temp2 = new Label(" " + p.getClassification() + " ");
            temp2.setPrefWidth(80);
            temp2.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp2.setOnMouseEntered(e -> {
                int ind = projectClass.getChildren().indexOf(temp2);
                highlight(ind, projectNames);
                highlight(ind, projectClass);
                highlight(ind, projectNumChildren);
            });
            temp2.setOnMouseClicked(e -> {
                int ind = projectClass.getChildren().indexOf(temp2);
                this.workItems = this.projects.get(ind).getWorkItems();
                this.tasks = (this.workItems.size() == 0) ? new ArrayList<TaskItem>() : this.workItems.get(0).getTasks();
                refresh();
            });
            projectClass.getChildren().add(temp2);
            Label temp3 = new Label(" " + Integer.toString(p.getNumberOfWorkItems()) + " ");
            temp3.setPrefWidth(80);
            temp3.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp3.setOnMouseEntered(e -> {
                int ind = projectNumChildren.getChildren().indexOf(temp3);
                highlight(ind, projectNames);
                highlight(ind, projectClass);
                highlight(ind, projectNumChildren);
            });
            temp3.setOnMouseClicked(e -> {
                int ind = projectNumChildren.getChildren().indexOf(temp3);
                this.workItems = this.projects.get(ind).getWorkItems();
                this.tasks = (this.workItems.size() == 0) ? new ArrayList<TaskItem>() : this.workItems.get(0).getTasks();
                refresh();
            });
            projectNumChildren.getChildren().add(temp3);
            i++;
        }
        while (i <= rows) {
            Label blank = new Label("");
            blank.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            projectNames.getChildren().add(blank);
            projectClass.getChildren().add(blank);
            projectNumChildren.getChildren().add(blank);
            i++;
        }

        HBox table = new HBox(projectNames, projectClass, projectNumChildren);
        ScrollPane scroll = new ScrollPane(table);
        scroll.setPrefHeight(300);
        HBox headers = new HBox(projectNamesHeader, projectClassHeader, projectNumOfHeader);
        return new VBox(headers, scroll);
    }

    private VBox getWorkTable() {
        Label workNamesHeader = new Label(" Name ");
        Label workDecripHeader = new Label(" Decrip ");
        Label workNumOfHeader = new Label(" Num Child ");
        workNamesHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        workNamesHeader.setPrefWidth(80);
        workNamesHeader.setAlignment(Pos.TOP_CENTER);
        workDecripHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        workDecripHeader.setPrefWidth(80);
        workDecripHeader.setAlignment(Pos.TOP_CENTER);
        workNumOfHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        workNumOfHeader.setPrefWidth(80);
        workNumOfHeader.setAlignment(Pos.TOP_CENTER);
        VBox workNames = new VBox(workNamesHeader);
        workNames.setAlignment(Pos.TOP_CENTER);
        workNames.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        VBox workDecrip = new VBox(workDecripHeader);
        workDecrip.setAlignment(Pos.TOP_CENTER);
        workDecrip.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        VBox workNumChildren = new VBox(workNumOfHeader);
        workNumChildren.setAlignment(Pos.TOP_CENTER);
        workNumChildren.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        int i = 0;
        for (WorkItem p: this.workItems) {
            Label temp1 = new Label(" " + p.getName() + " ");
            temp1.setPrefWidth(80);
            temp1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp1.setOnMouseEntered(e -> {
                int ind = workNames.getChildren().indexOf(temp1);
                highlight(ind, workNames);
                highlight(ind, workDecrip);
                highlight(ind, workNumChildren);
            });
            temp1.setOnMouseClicked(e -> {
                int ind = workNames.getChildren().indexOf(temp1) ;
                this.tasks = this.workItems.get(ind).getTasks();
                refresh();
            });
            workNames.getChildren().add(temp1);
            Label temp2 = new Label(" " + p.getDescription() + " ");
            temp2.setPrefWidth(80);
            temp2.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp2.setOnMouseEntered(e -> {
                int ind = workDecrip.getChildren().indexOf(temp2);
                highlight(ind, workNames);
                highlight(ind, workDecrip);
                highlight(ind, workNumChildren);
            });
            temp2.setOnMouseClicked(e -> {
                int ind = workDecrip.getChildren().indexOf(temp2);
                this.tasks = this.workItems.get(ind).getTasks();
                refresh();
            });
            workDecrip.getChildren().add(temp2);
            Label temp3 = new Label(" " + Integer.toString(p.getNumberOfTasks()) + " ");
            temp3.setPrefWidth(80);
            temp3.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp3.setOnMouseEntered(e -> {
                int ind = workNumChildren.getChildren().indexOf(temp3);
                highlight(ind, workNames);
                highlight(ind, workDecrip);
                highlight(ind, workNumChildren);
            });
            temp3.setOnMouseClicked(e -> {
                int ind = workNumChildren.getChildren().indexOf(temp3);
                this.tasks = this.workItems.get(ind).getTasks();
                refresh();
            });
            workNumChildren.getChildren().add(temp3);
            i++;
        }
        while (i <= rows) {
            Label blank = new Label("");
            blank.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            workNames.getChildren().add(blank);
            workDecrip.getChildren().add(blank);
            workNumChildren.getChildren().add(blank);
            i++;
        }
        HBox table = new HBox(workNames, workDecrip, workNumChildren);
        ScrollPane scroll = new ScrollPane(table);
        scroll.setPrefHeight(300);
        HBox headers = new HBox(workNamesHeader, workDecripHeader, workNumOfHeader);
        return new VBox(headers, scroll);
    }

    private VBox getTaskTable() {
        Label taskNamesHeader = new Label(" Name ");
        Label taskDecripHeader = new Label(" Decrip ");
        Label taskNumOfHeader = new Label(" Complete ");
        taskNamesHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        taskNamesHeader.setPrefWidth(80);
        taskNamesHeader.setAlignment(Pos.TOP_CENTER);
        taskDecripHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        taskDecripHeader.setPrefWidth(80);
        taskDecripHeader.setAlignment(Pos.TOP_CENTER);
        taskNumOfHeader.setStyle("-fx-font-weight: bold; -fx-border-color: black");
        taskNumOfHeader.setPrefWidth(80);
        taskNumOfHeader.setAlignment(Pos.TOP_CENTER);
        VBox taskNames = new VBox(taskNamesHeader);
        taskNames.setAlignment(Pos.TOP_CENTER);
        taskNames.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        VBox taskDecrip = new VBox(taskDecripHeader);
        taskDecrip.setAlignment(Pos.TOP_CENTER);
        taskDecrip.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        VBox taskNumChildren = new VBox(taskNumOfHeader);
        taskNumChildren.setAlignment(Pos.TOP_CENTER);
        taskNumChildren.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        int i = 0;
        for (TaskItem p: this.tasks) {
            Label temp1 = new Label(" " + p.getName() + " ");
            temp1.setPrefWidth(80);
            temp1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp1.setOnMouseEntered(e -> {
                int ind = taskNames.getChildren().indexOf(temp1);
                highlight(ind, taskNames);
                highlight(ind, taskDecrip);
                highlight(ind, taskNumChildren);
            });
            taskNames.getChildren().add(temp1);
            Label temp2 = new Label(" " + p.getDescription() + " ");
            temp2.setPrefWidth(80);
            temp2.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp2.setOnMouseEntered(e -> {
                int ind = taskDecrip.getChildren().indexOf(temp2);
                highlight(ind, taskNames);
                highlight(ind, taskDecrip);
                highlight(ind, taskNumChildren);
            });
            taskDecrip.getChildren().add(temp2);
            Label temp3 = new Label(" " + Boolean.toString(p.getComplete()) + " ");
            temp3.setPrefWidth(80);
            temp3.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp3.setOnMouseEntered(e -> {
                int ind = taskNumChildren.getChildren().indexOf(temp3);
                highlight(ind, taskNames);
                highlight(ind, taskDecrip);
                highlight(ind, taskNumChildren);
            });
            taskNumChildren.getChildren().add(temp3);
            i++;
        }
        while (i <= rows) {
            Label blank = new Label("");
            blank.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            taskNames.getChildren().add(blank);
            taskDecrip.getChildren().add(blank);
            taskNumChildren.getChildren().add(blank);
            i++;
        }
        HBox table = new HBox(taskNames, taskDecrip, taskNumChildren);
        ScrollPane scroll = new ScrollPane(table);
        scroll.setPrefHeight(300);
        HBox headers = new HBox(taskNamesHeader, taskDecripHeader, taskNumOfHeader);
        return new VBox(headers, scroll);
    }


    public HBox getView() { return this.view; }

}
