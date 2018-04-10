package office.small.gviewer.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import office.small.gviewer.model.InfoModel;
import office.small.gviewer.view.InfoView;

public class InfoPresenterImpl extends MvpBasePresenter<InfoView> implements InfoPresenter {
    private final InfoModel model;

    public InfoPresenterImpl(InfoModel model) {
        this.model = model;
    }

    @Override
    public void loadInformation() {
        getView().displayInfo(model.retrieveInfo());
    }
}
