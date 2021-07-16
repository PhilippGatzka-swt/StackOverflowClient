package main.java.com.sowatec.stackoverflowclient;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.com.sowatec.stackoverflowclient.database.DatabaseExecutor;
import main.java.com.sowatec.stackoverflowclient.dbo.IssueDBO;
import main.java.com.sowatec.stackoverflowclient.dbo.TagDBO;

import java.io.File;
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
            BorderPane issue = new BorderPane();
            issue.setStyle("-fx-border-color: #000000");


            issue.setAccessibleRole(AccessibleRole.BUTTON);
            issue.setOnMouseClicked(e -> showIssue());
            issue.setPrefSize(905, 90);

            Label title = new Label(i.getTitle());
            BorderPane.setMargin(title, new Insets(0, 25, 0, 25));
            issue.setLeft(title);

            HBox tags = new HBox();
            tags.setSpacing(5);
            BorderPane.setAlignment(tags, Pos.CENTER);
            BorderPane.setMargin(tags, new Insets(5, 5, 5, 5));
            issue.setBottom(tags);

            List<TagDBO> tagList = DatabaseExecutor.getExecutor().getTagByIssueId(i.getId());
            tagList.forEach(t -> {
                Label tag = new Label(t.getName());
                tags.getChildren().add(tag);
            });

            HBox center = new HBox();
            center.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            BorderPane.setMargin(center, new Insets(0, 20, 0, 0));
            VBox votes = new VBox();
            center.getChildren().add(votes);

            File fUp = new File("src/main/resources/image/upvote.png");
            System.out.println(fUp.getAbsolutePath());
            Image u = new Image(fUp.toURI().toString());
            ImageView up = new ImageView();
            up.setImage(u);
            up.setFitWidth(25);
            up.setFitHeight(25);
            votes.getChildren().add(up);

            Label vote = new Label();
            int v = i.getUpvotes() - i.getDownvotes();
            vote.setText(v + "");
            votes.getChildren().add(vote);

            File fDown = new File("src/main/resources/image/downvote.png");
            Image d = new Image(fDown.toURI().toString());
            ImageView down = new ImageView();
            down.setImage(d);
            down.setFitWidth(25);
            down.setFitHeight(25);

            votes.getChildren().add(down);

            issue.setCenter(center);

            Label username = new Label(DatabaseExecutor.getExecutor().getUserByID(i.getUser_id()).getUsername());
            BorderPane.setMargin(username, new Insets(0, 25, 0, 25));
            issue.setRight(username);
            list.getChildren().add(issue);
        });
    }

    private void showIssue() {
    }

}
