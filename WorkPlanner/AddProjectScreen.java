import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AddProjectScreen {

    private final ArrayList<ProjectItem> projects;
    private final TableView<ProjectItem> table;
    private final Stage popup;

    public AddProjectScreen() {
        projects = new ArrayList<>();
        table = new TableView<ProjectItem>(FXCollections.observableArrayList(projects));
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
        this.popup = new Stage();
        save.setOnAction(e -> {
                projects.add(new ProjectItem(newName.getText(), newClass.getText()));
                popup.close();
            });
        this.popup.setScene(new Scene(scene));
    }

    public void addTo(ArrayList<ProjectItem> prjs) {
        this.projects.clear();
        this.projects.addAll(prjs);
        System.out.println(this.projects);
        this.table.refresh();
        this.popup.showAndWait();
        prjs.clear();
        prjs.addAll(this.projects);
    }
}
