package office.small.gviewer.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;
import com.squareup.picasso.Picasso;

import office.small.gviewer.R;
import office.small.gviewer.model.entity.GithubUser;
import office.small.gviewer.model.image.ImageLoader;
import office.small.gviewer.model.image.PicassoImageLoad;

public class InfoAdapter extends SupportAnnotatedAdapter implements InfoAdapterBinder {
    @ViewType(
        layout = R.layout.row_item,
        views = {
            @ViewField(
                id = R.id.txtLogin,
                name = "login",
                type = TextView.class),
            @ViewField(
                id = R.id.txtName,
                name = "name",
                type = TextView.class),
            @ViewField(
                id = R.id.txtLocation,
                name = "location",
                type = TextView.class),
            @ViewField(
                id = R.id.txtCompany,
                name = "company",
                type = TextView.class),
            @ViewField(
                id = R.id.txtBlog,
                name = "blog",
                type = TextView.class),
            @ViewField(
                id = R.id.txtBio,
                name = "bio",
                type = TextView.class),
            @ViewField(
                id = R.id.imgAvatar,
                name = "imgAvatar",
                type = ImageView.class),
        }) public final int VIEWTYPE_INFO = 0;

    GithubUser data;
    private ImageLoader<ImageView> imageLoader;

    public InfoAdapter(Context context) {
        super(context);
        imageLoader = new PicassoImageLoad(Picasso.with(context));
    }

    public void setData(GithubUser data) {
        this.data = data;
    }

    public GithubUser getData() {
        return data;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void bindViewHolder(InfoAdapterHolders.VIEWTYPE_INFOViewHolder vh, int position) {
        if (data != null) {
            vh.login.setText(data.getLogin());
/*            vh.name.setText(data.getName());
            vh.location.setText(data.getLocation());
            vh.company.setText(data.getCompany());
            vh.blog.setText(data.getBlog());
            vh.bio.setText(data.getBio()); */
            imageLoader.downloadInto(data.getAvatarURL(), vh.imgAvatar);
        }
    }
}
