package main.java.com.sowatec.stackoverflowclient.dbo;

public class IssueDBO extends AbstractDBO {
    private int id;
    private String title;
    private String body;
    private int user_id;
    private int upvotes;
    private int downvotes;
    private boolean resolved;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
