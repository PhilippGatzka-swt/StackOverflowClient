package main.java.com.sowatec.stackoverflowclient;

import main.java.com.sowatec.stackoverflowclient.dbo.UserDBO;

public class Model {

    private UserDBO userDBO;

    public void setUser(UserDBO userDBO) {
        this.userDBO = userDBO;
    }


    public boolean loggedId() {
        return userDBO != null;
    }

    public UserDBO getUserDBO() {
        return userDBO;
    }
}
