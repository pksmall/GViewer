package office.small.gviewer.model.api;

import retrofit2.http.Headers;
import rx.Observable;
import office.small.gviewer.model.entity.GithubUser;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
    @Headers({
            "Accept: application/vnd.github.v3+jsons",
            "User-Agent: GViewer v0.1"
    })
    @GET("users/{user}")
    Observable<GithubUser> getUser(@Path("user") String user);
}
