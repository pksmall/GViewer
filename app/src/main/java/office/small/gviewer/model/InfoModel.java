package office.small.gviewer.model;

import office.small.gviewer.model.entity.GithubUser;
import rx.Observable;

public interface InfoModel {
    Observable<GithubUser> retrieveInfo();
}
