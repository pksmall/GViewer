package office.small.gviewer.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import office.small.gviewer.view.InfoView;

public interface InfoPresenter extends MvpPresenter<InfoView> {
    void loadInformation(final boolean pullToRefresh);
}
