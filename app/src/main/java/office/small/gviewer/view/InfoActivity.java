package office.small.gviewer.view;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import office.small.gviewer.R;
import office.small.gviewer.model.InfoModelImpl;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.presenter.InfoPresenterImpl;

public class InfoActivity extends MvpLceActivity<TextView, String, InfoView, InfoPresenter> implements InfoView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData(false);
    }

    @NonNull
    @Override
    public InfoPresenter createPresenter() {
        return new InfoPresenterImpl(new InfoModelImpl());
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        String errorMessage = e.getMessage();
        return errorMessage == null ? "Unknown error" : errorMessage;
    }

    @Override
    public void setData(String data) {
        contentView.setText(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadInformation();
    }
}
