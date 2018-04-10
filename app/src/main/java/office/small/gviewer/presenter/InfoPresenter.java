package office.small.gviewer.presenter;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import office.small.gviewer.view.InfoView;

public interface InfoPresenter extends MvpPresenter<InfoView> {
    void loadInformation();
}
