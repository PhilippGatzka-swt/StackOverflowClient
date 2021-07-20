package main.java.com.sowatec.stackoverflowclient.panes.fragments;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.com.sowatec.stackoverflowclient.dbo.IssueDBO;

import java.io.File;

public class IssueFragment extends Pane {
    private IssueDBO issueDBO;

    private BorderPane borderPane;

    private VBox left;
    private VBox center;
    private VBox right;
    private HBox tags;
    private Label votesCount;
    private Label votes;
    private Label answerCount;
    private Label answers;
    private Label title;
    private Label body;
    private Label username;
    private ImageView imageView;

    public IssueFragment(IssueDBO issueDBO) {
        this.issueDBO = issueDBO;
        setPrefSize(770, 180);
        setStyle("-fx-border-color: black");
        init();
    }

    private void init() {
        borderPane = new BorderPane();
        left = new VBox();
        center = new VBox();
        right = new VBox();
        tags = new HBox();

        votesCount = new Label(issueDBO.getDownvotes() + issueDBO.getUpvotes() + "");
        votes = new Label("Votes");
        answerCount = new Label("0");
        answers = new Label("Answers");
        title = new Label(issueDBO.getTitle());
        body = new Label(issueDBO.getBody());
        username = new Label(issueDBO.getUserDBO().getUsername());
        imageView = new ImageView();

        title.setStyle("-fx-text-fill: #5d5dff");

        borderPane.setLeft(left);
        borderPane.setCenter(center);
        borderPane.setRight(right);

        left.setPrefSize(100, 180);
        left.getChildren().addAll(votesCount, votes, answerCount, answers);
        left.setAlignment(Pos.CENTER);

        center.setPrefSize(540, 180);
        BorderPane.setMargin(center, new Insets(0, 5, 0, 5));
        VBox.setMargin(title, new Insets(10, 0, 0, 0));
        title.setFont(new Font("Arial", 18));
        title.setAccessibleRole(AccessibleRole.BUTTON);
        title.setCursor(Cursor.HAND);
        title.setOnMouseEntered(event -> {
            title.setStyle("-fx-text-fill: #5d5dbb");
            title.setUnderline(true);
        });
        title.setOnMouseExited(event -> {
            title.setStyle("-fx-text-fill: #5d5dff");
            title.setUnderline(false);

        });
        body.setAlignment(Pos.TOP_CENTER);
        body.setMaxHeight(100);
        body.setPrefHeight(100);
        body.setWrapText(true);
        body.setFont(new Font("Arial", 14));
        VBox.setMargin(body, new Insets(5, 0, 5, 0));

        tags.setPrefWidth(200);
        tags.setSpacing(5);
        center.getChildren().addAll(title, body, tags);
        Image image = new Image(new File("src/main/resources/image/placeholder.png").toURI().toString());
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setImage(image);
        right.setPrefSize(100, 180);
        right.setAlignment(Pos.CENTER);
        BorderPane.setMargin(right, new Insets(0, 10, 0, 10));
        right.getChildren().addAll(imageView, username);
        getChildren().add(borderPane);
    }
}
