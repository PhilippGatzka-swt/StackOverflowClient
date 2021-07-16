package main.java.com.sowatec.stackoverflowclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.java.com.sowatec.stackoverflowclient.panes.AccountPane;
import main.java.com.sowatec.stackoverflowclient.panes.AddPane;
import main.java.com.sowatec.stackoverflowclient.panes.LoginPane;
import main.java.com.sowatec.stackoverflowclient.panes.RegisterPane;

public class Controller {

    public static Controller instance;
    private Model model;

    @FXML
    private BorderPane parent;
    @FXML
    private Pane contentPane;

    public Controller() {
        model = new Model();
        instance = this;
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public void actionAdd(ActionEvent actionEvent) {
        showAddPane();
    }

    public void showAddPane() {
        if (!model.loggedId())
            showLoginPane();
        else
            parent.setCenter(new AddPane());
    }

    public Model getModel() {
        return model;
    }

    public void actionAccount(ActionEvent actionEvent) {
        showRegisterPane();
    }

    public void showLoginPane() {
        parent.setCenter(new LoginPane());
    }

    public void showRegisterPane() {
        parent.setCenter(new RegisterPane());
    }

    public void showAccountPane() {
        if (model.loggedId()) {
            parent.setCenter(new AccountPane());
        } else {
            HBox hBox = new HBox();
            Button login = new Button("Login");
            Button register = new Button("register");
            login.setOnAction(e -> parent.setCenter(new LoginPane()));
            register.setOnAction(e -> parent.setCenter(new RegisterPane()));
            hBox.getChildren().addAll(login, register);
            parent.setCenter(hBox);
        }


    }

    public void showMainPane() {
        if (!model.loggedId()) {
            showLoginPane();
            return;
        }
        parent.setCenter(new MainPane());



    }

    public void actionMain(ActionEvent actionEvent) {
        showMainPane();
    }
}
