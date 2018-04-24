package office.small.gviewer.model.entity;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

/**
 * github get user
 * {
 "login": "pksmall",
 "id": 17724432,
 "avatar_url": "https://avatars0.githubusercontent.com/u/17724432?v=4",
 "gravatar_id": "",
 "url": "https://api.github.com/users/pksmall",
 "html_url": "https://github.com/pksmall",
 "followers_url": "https://api.github.com/users/pksmall/followers",
 "following_url": "https://api.github.com/users/pksmall/following{/other_user}",
 "gists_url": "https://api.github.com/users/pksmall/gists{/gist_id}",
 "starred_url": "https://api.github.com/users/pksmall/starred{/owner}{/repo}",
 "subscriptions_url": "https://api.github.com/users/pksmall/subscriptions",
 "organizations_url": "https://api.github.com/users/pksmall/orgs",
 "repos_url": "https://api.github.com/users/pksmall/repos",
 "events_url": "https://api.github.com/users/pksmall/events{/privacy}",
 "received_events_url": "https://api.github.com/users/pksmall/received_events",
 "type": "User",
 "site_admin": false,
 "name": "Pavel Korzhenko",
 "company": null,
 "blog": "http://office-xxi.com",
 "location": "Somewhere",
 "email": null,
 "hireable": true,
 "bio": "https://www.linkedin.com/in/pavel-korzhenko-17985155/",
 "public_repos": 15,
 "public_gists": 0,
 "followers": 1,
 "following": 1,
 "created_at": "2016-03-08T13:22:47Z",
 "updated_at": "2018-04-18T08:26:25Z"
 }
 */
public class GithubUser {
    private String id;
    private String login;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String bio;

    @SerializedName("avatar_url")
    private String avatar;


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

    public String getAvatar() {
        return avatar;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public String getLogin() {
        return login;
    }
}
