package main.java.com.sowatec.stackoverflowclient.panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.com.sowatec.stackoverflowclient.Controller;
import main.java.com.sowatec.stackoverflowclient.database.DatabaseExecutor;
import main.java.com.sowatec.stackoverflowclient.dbo.IssueDBO;
import main.java.com.sowatec.stackoverflowclient.dbo.TagDBO;
import main.java.com.sowatec.stackoverflowclient.dbo.UserDBO;

import java.util.Arrays;

public class AddPane extends BorderPane {

    private HBox hbox;
    private VBox vbox;
    private ScrollPane scrollPane;

    private Button save;
    private Button cancel;

    private Label title;
    private Label titleDesc;
    private Label body;
    private Label bodyDesc;
    private Label tags;
    private Label tagsDesc;

    private TextField tagsInput;
    private TextField titleInput;
    private TextArea bodyInput;

    private final int bigSize = 21;
    private final int smallSize = 17;

    private String font = "Arial";

    private Font bigFont;
    private Font smallFont;

    public AddPane() {
        setPrefSize(1190.0d, 630.0d);
        setPadding(new Insets(20, 20, 20, 20));
        save = new Button("Save");
        save.setOnAction(getSaveAction());
        cancel = new Button("Cancel");
        cancel.setOnAction(getCancelAction());
        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(save, cancel);
        setBottom(hbox);
        bigFont = new Font(font, bigSize);
        smallFont = new Font(font, smallSize);
        vbox = new VBox();
        title = new Label("Title");
        title.setFont(bigFont);
        titleDesc = new Label("Be specific and imagine you’re asking a question to another person");
        titleDesc.setFont(smallFont);
        titleInput = new TextField();
        titleInput.setPromptText("e.g. Is there an R function for finding the index of and element in a vector?");
        body = new Label("Body");
        body.setFont(bigFont);
        bodyDesc = new Label("Include all the information someone would need to answer your question");
        bodyDesc.setFont(smallFont);
        bodyInput = new TextArea();
        bodyInput.setWrapText(true);
        Label tips = new Label("Tools: <code>code</code> **bold** *italic*");
        tags = new Label("Tags");
        tags.setFont(bigFont);
        tagsDesc = new Label("Add up to 5 tags to describe what your question is about");
        tagsDesc.setFont(smallFont);
        tagsInput = new TextField();
        tagsInput.setPromptText("e.g. (java aso.net-mvc node.js)");
        vbox.getChildren().addAll(title, titleDesc, titleInput, body, bodyDesc, bodyInput, tips, tags, tagsDesc, tagsInput);
        setCenter(vbox);
    }

    private EventHandler<ActionEvent> getSaveAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sTitle = titleInput.getText();
                String sBody = bodyInput.getText();
                UserDBO userDBO = Controller.getInstance().getModel().getUserDBO();
                String[] sTags = tagsInput.getText().trim().split(",");

                IssueDBO issueDBO = new IssueDBO();
                issueDBO.setTitle(sTitle);
                issueDBO.setBody(sBody);
                issueDBO.setUserID(userDBO.getId());
                issueDBO.setUpvotes(0);
                issueDBO.setDownvotes(0);
                issueDBO.setResolved(false);

                for(String tag: sTags){
                    TagDBO tagDBO = new TagDBO();
                    tagDBO.setName(tag);
                    DatabaseExecutor.getExecutor().executeRegisterTag(tagDBO);
                }

                DatabaseExecutor.getExecutor().executeRegisterIssue(issueDBO);
                issueDBO = DatabaseExecutor.getExecutor().getIssue(issueDBO);

                for(String tag: sTags){
                    TagDBO tagDBO = new TagDBO();
                    tagDBO.setName(tag);
                    tagDBO = DatabaseExecutor.getExecutor().getTag(tagDBO);

                    DatabaseExecutor.getExecutor().executeRegisterIssueToTag(tagDBO,issueDBO);
                }

                Controller.getInstance().showMainPane();
            }
        };
    }

    private EventHandler<ActionEvent> getCancelAction() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

            }


        };
    }


}
