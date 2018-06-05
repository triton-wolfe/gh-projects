import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ProjectViewer {

    private ArrayList<ProjectItem> projects;
    private ArrayList<WorkItem> workItems;
    private ArrayList<TaskItem> tasks;
    private HBox view;


    public ProjectViewer(ArrayList<ProjectItem> items) {
        this.projects = items;
        this.workItems = this.projects.get(0).getWorkItems();
        this.tasks = (this.workItems.size() == 0) ? new ArrayList<TaskItem>() : this.workItems.get(0).getTasks();
        this.view = new HBox(getProjectTable(), getWorkTable(), getTaskTable());
        this.view.setPrefHeight(200);

    }

    public void refresh() {
        this.view.getChildren().setAll(getProjectTable(), getWorkTable(), getTaskTable());
    }

    private HBox getProjectTable() {
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
        VBox projectNames = new VBox(projectNamesHeader);
        projectNames.setAlignment(Pos.TOP_CENTER);
        projectNames.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        VBox projectClass = new VBox(projectClassHeader);
        projectClass.setAlignment(Pos.TOP_CENTER);
        projectClass.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");
        VBox projectNumChildren = new VBox(projectNumOfHeader);
        projectNumChildren.setAlignment(Pos.TOP_CENTER);
        projectNumChildren.setStyle("-fx-border-width: 2 1; -fx-border-style: solid");

        for (ProjectItem p: this.projects) {
            Label temp1 = new Label(" " + p.getName() + " ");
            temp1.setPrefWidth(80);
            temp1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp1.setOnMouseEntered(e -> {
                int ind = projectNames.getChildren().indexOf(temp1);
                projectNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                projectClass.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                projectNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp1.setOnMouseExited(e -> {
                int ind = projectNames.getChildren().indexOf(temp1);
                projectNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                projectClass.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                projectNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            temp1.setOnMouseClicked(e -> {
                int ind = projectNames.getChildren().indexOf(temp1) - 1;
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
                projectNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                projectClass.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                projectNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp2.setOnMouseExited(e -> {
                int ind = projectClass.getChildren().indexOf(temp2);
                projectNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                projectClass.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                projectNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            temp2.setOnMouseClicked(e -> {
                int ind = projectClass.getChildren().indexOf(temp2) - 1;
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
                projectNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                projectClass.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                projectNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp3.setOnMouseExited(e -> {
                int ind = projectNumChildren.getChildren().indexOf(temp3);
                projectNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                projectClass.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                projectNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            temp3.setOnMouseClicked(e -> {
                int ind = projectNumChildren.getChildren().indexOf(temp3) - 1;
                this.workItems = this.projects.get(ind).getWorkItems();
                this.tasks = (this.workItems.size() == 0) ? new ArrayList<TaskItem>() : this.workItems.get(0).getTasks();
                refresh();
            });
            projectNumChildren.getChildren().add(temp3);
        }
        return new HBox(projectNames, projectClass, projectNumChildren);
    }

    private HBox getWorkTable() {
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

        for (WorkItem p: this.workItems) {
            Label temp1 = new Label(" " + p.getName() + " ");
            temp1.setPrefWidth(80);
            temp1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp1.setOnMouseEntered(e -> {
                int ind = workNames.getChildren().indexOf(temp1);
                workNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                workDecrip.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                workNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp1.setOnMouseExited(e -> {
                int ind = workNames.getChildren().indexOf(temp1);
                workNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                workDecrip.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                workNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            temp1.setOnMouseClicked(e -> {
                int ind = workNames.getChildren().indexOf(temp1) - 1;
                this.tasks = this.workItems.get(ind).getTasks();
                refresh();
            });
            workNames.getChildren().add(temp1);
            Label temp2 = new Label(" " + p.getDescription() + " ");
            temp2.setPrefWidth(80);
            temp2.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp2.setOnMouseEntered(e -> {
                int ind = workDecrip.getChildren().indexOf(temp2);
                workNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                workDecrip.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                workNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp2.setOnMouseExited(e -> {
                int ind = workDecrip.getChildren().indexOf(temp2);
                workNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                workDecrip.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                workNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            temp2.setOnMouseClicked(e -> {
                int ind = workDecrip.getChildren().indexOf(temp2) - 1;
                this.tasks = this.workItems.get(ind).getTasks();
                refresh();
            });
            workDecrip.getChildren().add(temp2);
            Label temp3 = new Label(" " + Integer.toString(p.getNumberOfTasks()) + " ");
            temp3.setPrefWidth(80);
            temp3.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp3.setOnMouseEntered(e -> {
                int ind = workNumChildren.getChildren().indexOf(temp3);
                workNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                workDecrip.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                workNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp3.setOnMouseExited(e -> {
                int ind = workNumChildren.getChildren().indexOf(temp3);
                workNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                workDecrip.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                workNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            temp3.setOnMouseClicked(e -> {
                int ind = workNumChildren.getChildren().indexOf(temp3) - 1;
                this.tasks = this.workItems.get(ind).getTasks();
                refresh();
            });
            workNumChildren.getChildren().add(temp3);
        }
        return new HBox(workNames, workDecrip, workNumChildren);
    }

    private HBox getTaskTable() {
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

        for (TaskItem p: this.tasks) {
            Label temp1 = new Label(" " + p.getName() + " ");
            temp1.setPrefWidth(80);
            temp1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp1.setOnMouseEntered(e -> {
                int ind = taskNames.getChildren().indexOf(temp1);
                taskNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                taskDecrip.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                taskNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp1.setOnMouseExited(e -> {
                int ind = taskNames.getChildren().indexOf(temp1);
                taskNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                taskDecrip.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                taskNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            taskNames.getChildren().add(temp1);
            Label temp2 = new Label(" " + p.getDescription() + " ");
            temp2.setPrefWidth(80);
            temp2.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp2.setOnMouseEntered(e -> {
                int ind = taskDecrip.getChildren().indexOf(temp2);
                taskNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                taskDecrip.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                taskNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp2.setOnMouseExited(e -> {
                int ind = taskDecrip.getChildren().indexOf(temp2);
                taskNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                taskDecrip.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                taskNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            taskDecrip.getChildren().add(temp2);
            Label temp3 = new Label(" " + Boolean.toString(p.getComplete()) + " ");
            temp3.setPrefWidth(80);
            temp3.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            temp3.setOnMouseEntered(e -> {
                int ind = taskNumChildren.getChildren().indexOf(temp3);
                taskNames.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                taskDecrip.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
                taskNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 1; -fx-border-color: blue");
            });
            temp3.setOnMouseExited(e -> {
                int ind = taskNumChildren.getChildren().indexOf(temp3);
                taskNames.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                taskDecrip.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
                taskNumChildren.getChildren().get(ind).setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black");
            });
            taskNumChildren.getChildren().add(temp3);
        }
        return new HBox(taskNames, taskDecrip, taskNumChildren);
    }


    public HBox getView() { return this.view; }

}
