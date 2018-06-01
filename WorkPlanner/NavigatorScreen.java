import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class NavigatorScreen {

    private Scene view;
    private final TableView<ProjectItem> prjs;
    private final ObservableList<ProjectItem> projectsObs;
    private final TableView<WorkItem> wrks;
    private final ObservableList<WorkItem> workItemsObs;
    private final ArrayList<WorkItem> workItems;
    private final TableView<TaskItem> tsks;
    private final ObservableList<TaskItem> taskItemsObs;
    private final ArrayList<TaskItem> taskItems;

    public NavigatorScreen(ArrayList<ProjectItem> projects) {
        this.projectsObs = FXCollections.observableArrayList(projects);
        this.prjs = new TableView<ProjectItem>(this.projectsObs);
        TableColumn<ProjectItem, String> namesPI = new TableColumn<>("Project");
        namesPI.setCellValueFactory(new PropertyValueFactory<ProjectItem, String>("name"));
        TableColumn<ProjectItem, String> classificationPI = new TableColumn<>("Classification");
        classificationPI.setCellValueFactory(new PropertyValueFactory<ProjectItem, String>("classification"));
        TableColumn<ProjectItem, String> numWorkItems = new TableColumn<>("Number of Work Items");
        numWorkItems.setCellValueFactory(new PropertyValueFactory<ProjectItem, String>("numberOfWorkItems"));
        TableColumn<ProjectItem, String> attributesPI = new TableColumn<>("Attributes");
        attributesPI.getColumns().setAll(classificationPI, numWorkItems);
        this.prjs.getColumns().setAll(namesPI,attributesPI);

        this.workItems = (projects.size() == 0) ? new ArrayList<>()
            : new ArrayList<>(projects.get(prjs.getSelectionModel().getFocusedIndex()).getWorkItems());
        this.workItemsObs = FXCollections.observableArrayList(workItems);
        this.wrks = new TableView<WorkItem>(this.workItemsObs);
        TableColumn<WorkItem, String> namesWI = new TableColumn<>("Work Item");
        namesWI.setCellValueFactory(new PropertyValueFactory<WorkItem, String>("name"));
        TableColumn<WorkItem, String> descriptionWI = new TableColumn<>("Description");
        descriptionWI.setCellValueFactory(new PropertyValueFactory<WorkItem, String>("description"));
        TableColumn<WorkItem, String> numTasks = new TableColumn<>("Number of Task");
        numTasks.setCellValueFactory(new PropertyValueFactory<WorkItem, String>("numberOfTasks"));
        TableColumn<WorkItem, String> attributesWI = new TableColumn<>("Attributes");
        attributesWI.getColumns().setAll(descriptionWI, numTasks);
        this.wrks.getColumns().setAll(namesWI, attributesWI);

        this.taskItems = (workItems.size() == 0) ? new ArrayList<>()
            : new ArrayList<>(workItems.get(wrks.getSelectionModel().getFocusedIndex()).getTasks());
        this.taskItemsObs = FXCollections.observableArrayList(taskItems);
        this.tsks = new TableView<TaskItem>(this.taskItemsObs);
        TableColumn<TaskItem, String> namesTI = new TableColumn<>("Task");
        namesTI.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("name"));
        TableColumn<TaskItem, String> descriptionsTI = new TableColumn<>("Description");
        descriptionsTI.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("description"));
        TableColumn<TaskItem, String> start = new TableColumn<>("Start Time");
        start.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("startShort"));
        TableColumn<TaskItem, String> complete = new TableColumn<>("Finished");
        complete.setCellValueFactory(new PropertyValueFactory<TaskItem, String>("complete"));
        TableColumn<TaskItem, String> attributesTI = new TableColumn<>("Attributes");
        attributesTI.getColumns().setAll(descriptionsTI,start,complete);
        this.tsks.getColumns().setAll(namesTI,attributesTI);

        Button addNewProject = new Button("Add Project");
        AddProjectScreen addPrj = new AddProjectScreen();
        addNewProject.setOnAction(e -> {
            addPrj.addTo(projects);
        });
        Button addNewWorkItem = new Button("Add Work Item");
        Button addNewTask = new Button("Add Task");

        this.prjs.getSelectionModel().selectedIndexProperty().addListener(e -> {
                this.workItems.clear();
                this.workItems.addAll(projects.get(projects.size() - this.prjs.getSelectionModel().getFocusedIndex() - 1).getWorkItems());
                this.workItemsObs.setAll(this.workItems);
                this.wrks.refresh();
                this.taskItems.clear();
                this.taskItems.addAll(this.workItems.get(workItems.size() - this.wrks.getSelectionModel().getFocusedIndex() - 1).getTasks());
                this.taskItemsObs.setAll(this.taskItems);
                this.tsks.refresh();
            });
        this.wrks.getSelectionModel().selectedIndexProperty().addListener(e -> {
                this.taskItems.clear();
                this.taskItems.addAll(this.workItems.get(workItems.size() - this.wrks.getSelectionModel().getFocusedIndex() - 1).getTasks());
                this.taskItemsObs.setAll(this.taskItems);
                this.tsks.refresh();
            });
        VBox prjView = new VBox(prjs, addNewProject);
        VBox wrkView = new VBox(wrks, addNewWorkItem);
        VBox tskView = new VBox(tsks, addNewTask);
        prjView.setAlignment(Pos.TOP_CENTER);
        wrkView.setAlignment(Pos.TOP_CENTER);
        tskView.setAlignment(Pos.TOP_CENTER);

        this.view = new Scene(new HBox(prjView, wrkView, tskView));
    }

    public Scene getView() { return this.view; }
}
