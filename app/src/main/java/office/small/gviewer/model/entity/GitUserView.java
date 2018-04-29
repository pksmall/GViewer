package office.small.gviewer.model.entity;

import android.support.annotation.Nullable;

public class GitUserView {
    private String id;
    private String login;
    private String avatar;

    /*private String name;
    private String company;
    private String blog;
    private String location;
    private String bio;


    public String getName() {
        if (name == null) {
            name = "-";
        }
        return name;
    }

    public String getCompany() {
        if (company == null) {
            company = "-";
        }
        return company;
    }

    public String getBlog() {
        if (blog == null) {
            blog = "-";
        }
        return blog;
    }

    public String getLocation() {
        if (location == null) {
            location = "-";
        }
        return location;
    }

    public String getBio() {
        if (bio == null) {
            bio = "-";
        }
        return bio;
    }

  */

    public String getAvatarURL() {
        return avatar;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public String getLogin() {
        return login;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAvatarURL(String avatar) {
        this.avatar = avatar;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
