package office.small.gviewer.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import office.small.gviewer.model.InfoModel;
import office.small.gviewer.view.InfoView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (isViewAttached()) {
                            infoView.setData(s);
                            infoView.showContent();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (isViewAttached()) {
                            String s = "Error: empty string";
                            infoView.setData(s);
                            infoView.showContent();
                        }
                    }
                });
    }
}
