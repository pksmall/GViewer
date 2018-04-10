package office.small.gviewer.presenter;

import office.small.gviewer.model.InfoModel;
import office.small.gviewer.view.InfoView;

public class InfoPresenterImpl implements InfoPresenter {
    private final InfoModel model;

    public InfoPresenterImpl(InfoModel model) {
        this.model = model;
    }

    @Override
    public void loadInformation(InfoView view) {
        view.displayInfo(model.retrieveInfo());
    }
}
