package office.small.gviewer.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import office.small.gviewer.model.InfoModel;
import office.small.gviewer.view.InfoView;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class InfoPresenterImpl extends MvpBasePresenter<InfoView> implements InfoPresenter {
    private final InfoModel model;
    private InfoView infoView;
    private Subscription subscription;

    public InfoPresenterImpl(InfoModel model) {
        this.model = model;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (!retainInstance && subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
        super.detachView(retainInstance);
    }

    @Override
    public void loadInformation(final boolean pullToRefresh) {
        infoView = getView();
        subscription = model.retrieveInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (isViewAttached()
                            && (s.getLogin() != null || s.getMesssage() != null)) {
                        if (s.getMesssage() != null) {
                            infoView.setData(s.getMesssage());
                        } else {
                            infoView.setData("Login: "
                                    + s.getLogin()
                                    + "\nID:"
                                    + s.getId());
                        }
                        infoView.showContent();
                    }
                }, throwable -> {
                    if (isViewAttached()) {
                        infoView.showError(throwable, pullToRefresh);
                    }
                });
    }
}
