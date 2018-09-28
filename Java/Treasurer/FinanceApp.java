import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class FinanceApp extends Application {

    @Override
    public void start(Stage stage) {



        HBox btns = new HBox();
        btns.getChildren().setAll(new Button("Hello"), new Button("there"),
            new Button("general"), new Button("kenobi"));
        Tab tab1 = new Tab("Buttons", btns);
        tab1.setClosable(false);
        TabPane tabs = new TabPane(tab1);
        Scene scene = new Scene(tabs);
        stage.setScene(scene);
        stage.show();
    }
}
