package office.small.gviewer.model;

import android.content.Context;

import office.small.gviewer.R;

public class InfoErrorMessage {
    public static String get(Throwable e, boolean pullToRefresh, Context c) {
        if (pullToRefresh) {
            return c.getString(R.string.hasError);
        } else {
            return c.getString(R.string.hasErrorRetry);
        }
    }
}
