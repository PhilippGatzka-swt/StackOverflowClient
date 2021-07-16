package main.java.com.sowatec.stackoverflowclient.database;

import main.java.com.sowatec.stackoverflowclient.dbo.IssueDBO;
import main.java.com.sowatec.stackoverflowclient.dbo.TagDBO;
import main.java.com.sowatec.stackoverflowclient.dbo.UserDBO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseExecutor {
    private static DatabaseExecutor executor;

    private DatabaseExecutor() {

    }

    public static DatabaseExecutor getExecutor() {
        if (executor == null)
            executor = new DatabaseExecutor();
        return executor;
    }

    public String executeRegisterUser(UserDBO userDBO) {
        String success = "";
        try {
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_user");
            ResultSet users = select.executeQuery();
            boolean valid = true;
            while (users.next() && valid) {
                if (users.getString("username").equals(userDBO.getUsername()) || users.getString("email").equals(userDBO.getEmail()))
                    valid = false;
            }

            if (valid) {
                PreparedStatement insert = DatabaseConnector.getConnection().prepareStatement("insert into t_user (username, email, password) values (?,?,?)");
                insert.setString(1, userDBO.getUsername());
                insert.setString(2, userDBO.getEmail());
                insert.setString(3, userDBO.getPassword());
                insert.execute();
            } else {
                success = "Email or Username already registered";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public UserDBO getUser(String username, String password) {
        try {
            UserDBO userDBO = new UserDBO();
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_user where username = ? and password = ?");
            select.setString(1, username);
            select.setString(2, password);
            ResultSet set = select.executeQuery();
            set.next();
            userDBO.setUsername(set.getString("username"));
            userDBO.setEmail(set.getString("email"));
            userDBO.setId(set.getInt("id"));
            return userDBO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean doesUserExist(String username, String password) {
        try {
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_user where username = ? and password = ?");
            select.setString(1, username);
            select.setString(2, password);
            ResultSet set = select.executeQuery();
            return set.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void executeRegisterTag(TagDBO tagDBO) {
        try {
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_tag");
            ResultSet tags = select.executeQuery();
            boolean valid = true;
            while (tags.next() && valid) {
                if (tags.getString("name").equals(tagDBO.getName()))
                    valid = false;
            }

            if (valid) {
                PreparedStatement insert = DatabaseConnector.getConnection().prepareStatement("insert into t_tag (name) values (?)");
                insert.setString(1, tagDBO.getName());
                insert.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeRegisterIssue(IssueDBO issueDBO) {
        try {
            PreparedStatement insert = DatabaseConnector.getConnection().prepareStatement("insert into t_issue (title, body, user_id, upvotes, downvotes, resolved) values (?,?,?,?,?,?)");
            insert.setString(1, issueDBO.getTitle());
            insert.setString(2, issueDBO.getBody());
            insert.setInt(3, issueDBO.getUser_id());
            insert.setInt(4, issueDBO.getUpvotes());
            insert.setInt(5, issueDBO.getDownvotes());
            insert.setBoolean(6, issueDBO.isResolved());
            insert.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IssueDBO getIssue(IssueDBO issueDBO) {
        try {
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_issue where title like ?");
            select.setString(1, issueDBO.getTitle());
            ResultSet set = select.executeQuery();
            if (!set.next())
                return null;
            IssueDBO result = new IssueDBO();
            result.setId(set.getInt("id"));
            result.setTitle(set.getString("title"));
            result.setBody(set.getString("body"));
            result.setUser_id(set.getInt("user_id"));
            result.setUpvotes(set.getInt("upvotes"));
            result.setDownvotes(set.getInt("downvotes"));
            result.setResolved(set.getBoolean("resolved"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TagDBO getTag(TagDBO tagDBO) {
        try {
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_tag where name like ?");
            select.setString(1, tagDBO.getName());
            ResultSet set = select.executeQuery();
            if (!set.next())
                return null;
            TagDBO result = new TagDBO();
            result.setId(set.getInt("id"));
            result.setName(set.getString("name"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeRegisterIssueToTag(TagDBO tagDBO, IssueDBO issueDBO) {
        try {
            int tag_id = tagDBO.getId();
            int issue_id = issueDBO.getId();

            PreparedStatement insert = DatabaseConnector.getConnection().prepareStatement("insert into t_issue_tag (issue_id, tag_id) values (?,?)");
            insert.setInt(1, issue_id);
            insert.setInt(2, tag_id);
            insert.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<IssueDBO> getAllIssues() {
        try {
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_issue");
            ResultSet set = select.executeQuery();

            List<IssueDBO> issues = new ArrayList<>();
            while (set.next()) {
                IssueDBO result = new IssueDBO();
                result.setId(set.getInt("id"));
                result.setTitle(set.getString("title"));
                result.setBody(set.getString("body"));
                result.setUser_id(set.getInt("user_id"));
                result.setUpvotes(set.getInt("upvotes"));
                result.setDownvotes(set.getInt("downvotes"));
                result.setResolved(set.getBoolean("resolved"));
                issues.add(result);
            }
            return issues;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TagDBO> getTagByIssueId(int id) {

        try {
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_issue_tag where issue_id = ?");
            select.setInt(1, id);
            ResultSet set = select.executeQuery();

            List<TagDBO> tagDBOList = new ArrayList<>();
            PreparedStatement get = DatabaseConnector.getConnection().prepareStatement("select * from t_tag where id = ?");
            while (set.next()) {
                get.setInt(1, set.getInt("tag_id"));
                ResultSet tag = get.executeQuery();
                if (tag.next()) {
                    TagDBO tagDBO = new TagDBO();
                    tagDBO.setId(tag.getInt("id"));
                    tagDBO.setName(tag.getString("name"));
                    tagDBOList.add(tagDBO);
                }
            }
            return tagDBOList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDBO getUserByID(int user_id) {
        try {
            PreparedStatement select = DatabaseConnector.getConnection().prepareStatement("select * from t_user where id = ?");
            select.setInt(1,user_id);
            ResultSet set = select.executeQuery();

            if (!set.next())
                return null;
            UserDBO userDBO = new UserDBO();
            userDBO.setId(set.getInt("id"));
            userDBO.setUsername(set.getString("username"));
            userDBO.setEmail(set.getString("email"));
            return userDBO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
