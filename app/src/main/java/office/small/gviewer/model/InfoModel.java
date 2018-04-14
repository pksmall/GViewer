package office.small.gviewer.model;

import rx.Observable;

public interface InfoModel {
    Observable<String> retrieveInfo();
}
