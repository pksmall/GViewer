package office.small.gviewer.presenter;


import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import office.small.gviewer.model.InfoModel;
import office.small.gviewer.model.MyAction;
import office.small.gviewer.view.InfoView;

public class InfoPresenterImpl extends MvpNullObjectBasePresenter<InfoView> implements InfoPresenter {
    private final InfoModel model;

    public InfoPresenterImpl(InfoModel model) {
        this.model = model;
    }

    @Override
    public void loadInformation(final boolean pullToRefresh) {
        getView().showLoading(false);
        model.retrieveInfo(new MyAction<String>() {
            @Override
            public void onDownloadCallback(String s) {
                InfoView infoView = getView();
                infoView.setData(s);
                infoView.showContent();
            }
        });
    }
}
