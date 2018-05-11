package office.small.gviewer.view;

/**
 * TODO DZ 4
 * TODO + 1. Сделать доступными в объектах класса GithubUse имя пользователя, место его работы, город проживания, личный сайт и биографию. Отображать все данные в CardView. Используйте иконки, не нарушайте material guidelines и вообще сделайте это красиво, а не абы как.
 * TODO + 2. Добавить обработку null значений путём замены отсутствующих данных какими-либо заранее определенными (например, каким-либо уведомляющим текстом, либо, в случае отсутствия картинки - картинкой).
 * TODO + 3. Пока аватар загружается, отображать на его месте какое-либо изображение по умолчанию.
 * TODO + 4. Для каждого запроса присваивать заголовку User-Agent значение - имя приложения и его versionName, а заголовку Accept - application/vnd.github.v3+json, чтобы явно указать используемую версию API, как требует его разработчики.
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
import office.small.gviewer.di.InfoComponent;
import office.small.gviewer.model.InfoErrorMessage;
import office.small.gviewer.model.InfoModelImpl;
import office.small.gviewer.model.api.GithubService;
import office.small.gviewer.model.entity.GithubUser;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.presenter.InfoPresenterImpl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoActivity extends MvpLceActivity<SwipeRefreshLayout, GithubUser, InfoView, InfoPresenter> implements InfoView,SwipeRefreshLayout.OnRefreshListener {
    private static final String MYGITUSER = "pksmall";
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    InfoAdapter adapter;
    private InfoComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        component = createComponentBuilder().build();
//        component.inject(this);

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

//    protected DaggerInfoComponent.Builder createComponentBuilder() {
//        return DaggerInfoComponent.builder().activityModule(new ActivityModule(this));
//    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        Log.d("INFOACTIVITY-GERRMSG", " PTF: " + pullToRefresh);
        return InfoErrorMessage.get(e, pullToRefresh, this);
    }

    @Override
    public void setData(GithubUser data) {
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
