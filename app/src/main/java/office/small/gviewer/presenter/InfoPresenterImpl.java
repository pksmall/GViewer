package office.small.gviewer.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import office.small.gviewer.model.InfoModel;
import office.small.gviewer.model.MyAction;
import office.small.gviewer.view.InfoView;

public class InfoPresenterImpl extends MvpNullObjectBasePresenter<InfoView> implements InfoPresenter {
    private final InfoModel model;
    private InfoView infoView;

    public InfoPresenterImpl(InfoModel model) {
        this.model = model;
    }

    @Override
    public void loadInformation(final boolean pullToRefresh) {
        infoView = getView();
        model.retrieveInfo(new MyAction<String>() {
              @Override
              public void onDownloadCallback(String s) {
                  if (s.length() == 0) {
                      Exception e = new Exception();
                      infoView.showError(e, pullToRefresh);
                  } else {
                      //Log.d("MRET-LOADINFO", " S: " + s);
                      infoView.setData(s);
                      infoView.showContent();
                  }
              }
        });
    }
}
