package office.small.gviewer.model.image;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoImageLoad implements ImageLoader<ImageView>{
    @NonNull
    private final Picasso picasso;

    public PicassoImageLoad(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void downloadInto(String url, ImageView placeHolder) {
        picasso.load(url).into(placeHolder);
    }
}
