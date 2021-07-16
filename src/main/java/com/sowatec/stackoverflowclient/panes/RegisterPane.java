package main.java.com.sowatec.stackoverflowclient.panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.java.com.sowatec.stackoverflowclient.Controller;
import main.java.com.sowatec.stackoverflowclient.Util;
import main.java.com.sowatec.stackoverflowclient.database.DatabaseExecutor;
import main.java.com.sowatec.stackoverflowclient.dbo.UserDBO;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterPane extends BorderPane {

    private TextField usernameInput;
    private TextField emailInput;
    private PasswordField passwordInput;
    private PasswordField passwordInputRepeat;
    private Label warning;

    public RegisterPane() {
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Registration"));
        GridPane gridPane = new GridPane();
        vBox.getChildren().add(gridPane);

        warning = new Label("Passwords do not match!");
        warning.setVisible(false);
        usernameInput = new TextField();
        emailInput = new TextField();
        passwordInput = new PasswordField();
        passwordInputRepeat = new PasswordField();

        Button register = new Button("Register");
        ChangeListener<String> passwordCheck = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            warning.setVisible(!passwordInput.getText().equals(passwordInputRepeat.getText()));
            warning.setText("Passwords do not match!");
            register.setVisible(passwordInput.getText().equals(passwordInputRepeat.getText()));
        };

        passwordInput.textProperty().addListener(passwordCheck);
        passwordInputRepeat.textProperty().addListener(passwordCheck);

        gridPane.add(usernameInput, 1, 0);
        gridPane.add(emailInput, 1, 1);
        gridPane.add(passwordInput, 1, 2);
        gridPane.add(passwordInputRepeat, 1, 3);

        gridPane.add(new Label("Username"), 0, 0);
        gridPane.add(warning, 1, 4);
        gridPane.add(new Label("Email"), 0, 1);
        gridPane.add(new Label("Password"), 0, 2);
        gridPane.add(new Label("Repeat Password"), 0, 3);
        register.setVisible(false);
        register.setOnAction(event -> register());
        gridPane.add(register, 1, 5);

        gridPane.setPadding(new Insets(15, 15, 15, 15));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        setCenter(vBox);
    }

    private void register() {
        UserDBO userDBO = new UserDBO();
        userDBO.setUsername(usernameInput.getText().trim());
        userDBO.setEmail(emailInput.getText().trim());
        String password = Util.hash(passwordInput.getText());
        userDBO.setPassword(password);
        String msg = DatabaseExecutor.getExecutor().executeRegisterUser(userDBO);
        if (msg.equals("")) {
            System.out.println("success");
            Controller.getInstance().showLoginPane();
        } else {
            Logger.getLogger("SOClient").log(Level.WARNING,msg);
            warning.setText(msg);
            warning.setVisible(true);
        }
    }
}
