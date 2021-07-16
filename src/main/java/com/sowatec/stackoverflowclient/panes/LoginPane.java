package main.java.com.sowatec.stackoverflowclient.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.java.com.sowatec.stackoverflowclient.Controller;
import main.java.com.sowatec.stackoverflowclient.Util;
import main.java.com.sowatec.stackoverflowclient.database.DatabaseExecutor;
import main.java.com.sowatec.stackoverflowclient.dbo.UserDBO;

public class LoginPane extends BorderPane {

    private TextField usernameInput;
    private PasswordField passwordInput;

    private Label warning;

    public LoginPane() {
        warning = new Label("Login failed, check your input");
        warning.setVisible(false);
        usernameInput = new TextField();
        passwordInput = new PasswordField();
        Button submit = new Button("Submit");
        submit.setOnAction(e -> login());
        VBox vBox = new VBox();
        setCenter(vBox);
        GridPane gridPane = new GridPane();
        vBox.getChildren().add(new Label("Login"));
        vBox.getChildren().add(gridPane);
        gridPane.add(new Label("Username"), 0, 1);
        gridPane.add(usernameInput, 1, 1);
        gridPane.add(new Label("Password"), 0, 2);
        gridPane.add(passwordInput, 1, 2);
        gridPane.add(warning, 1, 3);
        gridPane.add(submit, 1, 4);
        gridPane.setPadding(new Insets(15, 15, 15, 15));
        gridPane.setHgap(15);
        gridPane.setVgap(15);
    }

    private void login() {
        String username = usernameInput.getText().trim();
        String password = Util.hash(passwordInput.getText().trim());

        boolean success = DatabaseExecutor.getExecutor().doesUserExist(username, password);

        if (success) {
            UserDBO userDBO = DatabaseExecutor.getExecutor().getUser(username, password);
            Controller.getInstance().getModel().setUser(userDBO);
            Controller.getInstance().showAccountPane();
        }

    }
}
