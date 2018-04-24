package office.small.gviewer.model;

import android.support.annotation.NonNull;
import office.small.gviewer.model.api.GithubService;
import office.small.gviewer.model.entity.GithubUser;
import rx.Observable;
import rx.schedulers.Schedulers;

public class InfoModelImpl implements InfoModel{
    private String user;
    private GithubService api;


    public InfoModelImpl(@NonNull String user, @NonNull GithubService api) {
        this.user = user;
        this.api = api;
    }

    @Override
    public Observable<GithubUser> retrieveInfo() {
        return api.getUser(user).subscribeOn(Schedulers.io());
    }
}
