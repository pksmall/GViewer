package office.small.gviewer.view;

/**
 *
 */

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import office.small.gviewer.R;
import office.small.gviewer.adapters.InfoAdapter;
import office.small.gviewer.model.InfoErrorMessage;
import office.small.gviewer.model.InfoModelImpl;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.presenter.InfoPresenterImpl;
import okhttp3.OkHttpClient;

public class InfoActivity extends MvpLceActivity<SwipeRefreshLayout, String, InfoView, InfoPresenter> implements InfoView,SwipeRefreshLayout.OnRefreshListener {
    private static final String MYGITUSER = "pksmall";
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    InfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // setup SwipeRefreshLayout
        contentView.setOnRefreshListener(this);

        // Setup recycler view
        adapter = new InfoAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadData(false);
    }

    @NonNull
    @Override
    public InfoPresenter createPresenter() {
        return new InfoPresenterImpl(new InfoModelImpl(MYGITUSER, new OkHttpClient()));
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        Log.d("INFOACTIVITY-GERRMSG", " PTF: " + pullToRefresh);
        return InfoErrorMessage.get(e, pullToRefresh, this);
    }

    @Override
    public void setData(String data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadInformation(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        Log.d("INFOACTIVITY-SHOWERROR", " PTF: " + pullToRefresh);
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }
}
