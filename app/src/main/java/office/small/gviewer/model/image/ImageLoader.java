package office.small.gviewer.model.image;

import android.support.annotation.NonNull;

public interface ImageLoader<T> {
    void downloadInto(@NonNull String url, @NonNull T placeHolder);
}
