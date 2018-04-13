package office.small.gviewer.view;

/**
 * TODO: DZ 1
 *
 * TODO: 1. Реализовать pull-to-refresh для обновления данных. После каждого обновления нужно возвращать новую строку, а не одну
 * TODO:    и ту же, как сейчас.
 * TODO: 2. С вероятностью 50% бросать исключение во время “загрузки” данных и, соответственно, обрабатывать его в presenter’е.
 */

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import office.small.gviewer.R;
import office.small.gviewer.adapters.InfoAdapter;
import office.small.gviewer.model.InfoModelImpl;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.presenter.InfoPresenterImpl;

public class InfoActivity extends MvpLceActivity<SwipeRefreshLayout, String, InfoView, InfoPresenter> implements InfoView,SwipeRefreshLayout.OnRefreshListener {

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
        return new InfoPresenterImpl(new InfoModelImpl());
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        String errorMessage = e.getMessage();
        return errorMessage == null ? "Unknown error" : errorMessage;
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
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }
}
