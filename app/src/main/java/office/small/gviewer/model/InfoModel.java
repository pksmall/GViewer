package office.small.gviewer.model;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;

import java.util.List;

import office.small.gviewer.model.entity.GitUserView;
import office.small.gviewer.model.entity.GithubUser;
import rx.Observable;

public interface InfoModel {
    @NonNull
    @AnyThread
    Observable<? extends List<GitUserView>> observeInfo();

    @NonNull
    @AnyThread
    Observable<? extends List<GitUserView>> lifecycle();

    @NonNull
    @AnyThread
    Observable<? extends List<GitUserView>> updateInfo();
}
