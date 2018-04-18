package office.small.gviewer.view;

/**
 * TODO: DZ 2
 *
 * При выполнении заданий рекомендуется просматривать http://reactivex.io/documentation/operators.html
 *
 * 1.Изменить InfoPresenterImpl#loadInformation так, чтобы в случае возникновения какой-либо ошибки,
 *  данный observable генерировал пустую строку.
 *
 * + Создать два observable, которые «генерируют» какой-либо набор элементов. Например, набор чисел
 * + (на самом деле, это не суть важно). Изменить InfoModelImpl#retrieveInfo так, чтобы из этих двух
 * + потоков данных создавался один, который «генерирует» единственный объект - строку, состоящую из
 * + первого "сгенерированного" элемента из первого observable и аналогичного из второго.
 *
 * + Переписать view так, чтобы он отображал RecyclerView (вертикальным списком), строка которого есть
 * + один лишь TextView.
 *
 * + Переписать model так, чтобы он возвращал некоторый (любой, на ваше усмотрение)набор строк, а не
 * + только одну.
 *
 * + Переписать presenter так, чтобы каждая полученная из model строка добавлялась в список на экране.
 * + При повороте экрана состояние должно сохраняться.
 *
 * Убрать зависимость от android-sdk в presenter’е. То есть избавится от использования
 * AndroidSchedulers.mainThread().
 *
 * + 2. (методичка) Создать новый проект. Разместить на Activity два элемента - TextView и EditText.
 * + Нужно добиться, чтобы при вводе символов в EditText эта последовательность символов отображалась
 * + в TextView. Для этой задачи следует использовать метод EditText - addTextChangedListener с объектом
 * + TextWatcher. Ну а все остальное вы знаете из материалов сегодняшнего урока.
 * + https://github.com/pksmall/EditTextWatcher
 *
 * 3. * Создать EventBus, принимающий данные от двух Observable и передающий эти данные двум подписчикам.
 * При этом сама “шина” также должна уметь формировать свои собственные события.
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
