package main.java.com.sowatec.stackoverflowclient.dbo;

public class TagDBO extends AbstractDBO {
    private int id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
