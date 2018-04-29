package office.small.gviewer.view;

/**
 * TODO DZ 5
 * TODO 1. Наши presenter и view не должны знать хоть что-либо про базу данных, поэтому model должна возвращать везде не GithubUser, а некий объект, который с базой не связан и одновременно содержит в себе все нужные для отображения на экране поля.
 * TODO 2. Вместо какого-либо константного сообщения об ошибке во время обновления данных, отображать какое-либо соответственно тому, что произошло в действительности.
 * TODO 3. (методичка) Доработать пример из урока так, чтобы вместо SugarORM, запись, чтение и удаление производились, в стандартную sqlite. Сравнить скорость работы sqlite с realm.
 * TODO 4. *Переделать пример, используя асинхронные методы Realm (executeTransactionAsync()).
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
import io.realm.RealmConfiguration;
import office.small.gviewer.R;
import office.small.gviewer.adapters.InfoAdapter;
import office.small.gviewer.model.InfoErrorMessage;
import office.small.gviewer.model.InfoModelImpl;
import office.small.gviewer.model.api.GithubService;
import office.small.gviewer.model.entity.GitUserView;
import office.small.gviewer.model.entity.GithubUser;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.presenter.InfoPresenterImpl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoActivity extends MvpLceActivity<SwipeRefreshLayout, GitUserView, InfoView, InfoPresenter> implements InfoView,SwipeRefreshLayout.OnRefreshListener {
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
        GithubService api = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.class);
        return new InfoPresenterImpl(new InfoModelImpl(MYGITUSER,
                api, new RealmConfiguration.Builder().build(), AndroidSchedulers.mainThread()));
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        Log.d("INFOACTIVITY-GERRMSG", " PTF: " + pullToRefresh);
        return InfoErrorMessage.get(e, pullToRefresh, this);
    }

    @Override
    public void setData(GitUserView data) {
        runOnUiThread(() -> {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        });
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
