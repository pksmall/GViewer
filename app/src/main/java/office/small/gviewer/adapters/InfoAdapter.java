package office.small.gviewer.adapters;

import android.content.Context;
import android.widget.TextView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import office.small.gviewer.R;

public class InfoAdapter extends SupportAnnotatedAdapter implements InfoAdapterBinder {

    @ViewType(
        layout = R.layout.row_item,
        views = {
            @ViewField(
                id = R.id.textView,
                name = "name",
                type = TextView.class)
        }) public final int VIEWTYPE_COUNTRY = 0;

    String data;


    public InfoAdapter(Context context) {
        super(context);
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void bindViewHolder(InfoAdapterHolders.VIEWTYPE_COUNTRYViewHolder vh, int position) {
        vh.name.setText(data);
    }
}
