package office.small.gviewer.model;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import office.small.gviewer.model.api.GithubService;
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
    public Observable<? extends List<GithubUser>> lifecycle() {
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
    public Observable<? extends List<GithubUser>> observeInfo() {
        return Observable.defer(new Func0<Observable<RealmResults<GithubUser>>>() {
            @Override
            public Observable<RealmResults<GithubUser>> call() {
                if (!checkRealmIsValid()) {
                    throw new IllegalStateException("Realm is closed!!!");
                }
                return realm.where(GithubUser.class)
                        .findAllAsync()
                        .<RealmResults<GithubUser>>asObservable()
                        .filter(new Func1<RealmResults<GithubUser>, Boolean>() {
                            @Override
                            public Boolean call(RealmResults<GithubUser> githubUsers) {
                                return githubUsers.isLoaded();
                            }
                        });
            }
        }).subscribeOn(scheduler);
    }

    @NonNull
    @Override
    public Observable<? extends List<GithubUser>> updateInfo() {
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
        }).map(new Func1<GithubUser, List<GithubUser>>() {
            @Override
            public List<GithubUser> call(GithubUser githubUser) {
                return Collections.singletonList(githubUser);
            }
        });
    }
}
