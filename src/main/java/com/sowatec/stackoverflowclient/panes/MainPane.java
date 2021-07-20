package main.java.com.sowatec.stackoverflowclient.panes;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.java.com.sowatec.stackoverflowclient.database.DatabaseExecutor;
import main.java.com.sowatec.stackoverflowclient.dbo.IssueDBO;
import main.java.com.sowatec.stackoverflowclient.panes.fragments.IssueFragment;

import java.util.List;

public class MainPane extends BorderPane {

    private VBox list;

    public MainPane() {
        ScrollPane scrollPane = new ScrollPane();
        BorderPane.setMargin(scrollPane, new Insets(50, 150, 50, 150));
        list = new VBox();
        list.setSpacing(5);
        scrollPane.setContent(list);
        setCenter(scrollPane);
        loadIssues();
    }

    private void loadIssues() {
        List<IssueDBO> issues = DatabaseExecutor.getExecutor().getAllIssues();
        issues.forEach(i -> {
            list.getChildren().add(new IssueFragment(i));
        });
    }

}
