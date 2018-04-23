package office.small.gviewer.model.entity;

import android.support.annotation.Nullable;

/**
 * github get user
 */
public class GithubUser {
    private String login;
    private String id;
    private String messsage;

    public String getMesssage() {
        return messsage;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public String getLogin() {
        return login;
    }
}
