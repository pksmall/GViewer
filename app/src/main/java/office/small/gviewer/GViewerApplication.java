package office.small.gviewer;

import android.app.Application;

import io.realm.Realm;

public class GViewerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
