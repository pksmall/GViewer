package office.small.gviewer.model;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import office.small.gviewer.model.api.GithubService;
import office.small.gviewer.model.entity.GitUserView;
import office.small.gviewer.model.entity.GithubUser;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public class InfoModelImpl implements InfoModel{
    private String user;
    private GithubService api;
    private final RealmConfiguration configuration;
    private final Scheduler scheduler;
    private Realm realm;


    public InfoModelImpl(@NonNull String user,
                         @NonNull GithubService api,
                         @NonNull RealmConfiguration configuration,
                         @NonNull Scheduler scheduler) {
        this.user = user;
        this.api = api;
        this.configuration = configuration;
        this.scheduler = scheduler;
    }

    @NonNull
    @Override
    public Observable<? extends List<GitUserView>> lifecycle() {
        return Observable.defer(() -> {
            if (checkRealmIsValid()) {
                throw new IllegalStateException("Subscribe on lifecycle!!!");
            }
            realm = Realm.getInstance(configuration);
            return observeInfo();
        }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                if (!checkRealmIsValid()) {
                    throw new IllegalStateException("Realm is closed!!!");
                }
                realm.close();
                realm = null;
            }
        });
    }

    private boolean checkRealmIsValid() {
        return ((realm != null) && (!realm.isClosed()));
    }

    @NonNull
    @Override
    public Observable<? extends List<GitUserView>> observeInfo() {
        if (!checkRealmIsValid()) {
            throw new IllegalStateException("Realm is closed!!!");
        }
        RealmResults<GithubUser> githubUsers =  realm.where(GithubUser.class).findAllAsync();

        return Observable.defer(new Func0<Observable<List<GitUserView>>>() {
            @Override
            public Observable<List<GitUserView>> call() {
                GitUserView gitUserView = new GitUserView();
                GithubUser mGithubUser = githubUsers.get(0);
                List<GitUserView> lGitUserView = null;

                gitUserView.setId(mGithubUser.getId());
                gitUserView.setAvatarURL(mGithubUser.getAvatarURL());
                gitUserView.setLogin(mGithubUser.getLogin());

                lGitUserView.add(gitUserView);

                return Observable.just(lGitUserView);
            }
        }).subscribeOn(scheduler);
    }

    @NonNull
    @Override
    public Observable<? extends List<GitUserView>> updateInfo() {
        return api.getUser(user).observeOn(scheduler).doOnNext(new Action1<GithubUser>() {
            @Override
            public void call(GithubUser githubUser) {
                if (!checkRealmIsValid()) {
                    throw new IllegalStateException("Realm is invalid!!!");
                }
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        GithubUser realmobject = realm.where(GithubUser.class).findFirst();
                        if (realmobject == null) {
                            realm.copyToRealmOrUpdate(githubUser);
                        } else {
                            String userAvatarURL = githubUser.getAvatarURL();
                            if (userAvatarURL != null && userAvatarURL.equals(realmobject.getAvatarURL())) {
                                realmobject.setAvatarURL(userAvatarURL);
                            }
                            String userId = githubUser.getId();
                            if (userId != null && userId.equals(realmobject.getId())) {
                                realmobject.setId(userId);
                            }
                        }
                    }
                });
            }
        }).map(new Func1<GithubUser, List<GitUserView>>() {
            @Override
            public List<GitUserView> call(GithubUser githubUser) {
                GitUserView gitUserView = new GitUserView();
                List<GitUserView> lGitUserView = null;

                gitUserView.setId(githubUser.getId());
                gitUserView.setAvatarURL(githubUser.getAvatarURL());
                gitUserView.setLogin(githubUser.getLogin());

                lGitUserView.add(gitUserView);

                return lGitUserView;
            }
        });
    }
}
