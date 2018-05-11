package office.small.gviewer;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import office.small.gviewer.model.InfoModel;
import office.small.gviewer.model.entity.GithubUser;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.presenter.InfoPresenterImpl;
import office.small.gviewer.view.InfoView;
import rx.Observable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class InfoPresenterImplTest {

    InfoView view = mock(InfoView.class);
    InfoModel model = mock(InfoModel.class);
    InfoPresenter presenter = new InfoPresenterImpl(model);
    GithubUser githubUser = new GithubUser();

    @Test
    public void emptyViewAtAttach() {
//        emptyViewAtAttach(Observable.empty());
        emptyViewAtAttach(Observable.just(Collections.emptyList()));
        loadInformation(githubUser);
        loadInformation();
        contentViewAtAttach(githubUser);
    }

    private void emptyViewAtAttach(Observable<List<GithubUser>> emptyResult) {
        doReturn(emptyResult).when(model).lifecycle();
        presenter.attachView(view);
        Mockito.verifyZeroInteractions(view);
    }

    private void contentViewAtAttach(GithubUser githubUser){
        doReturn(Observable.just(Collections.singletonList(githubUser))).when(model).lifecycle();
        presenter.attachView(view);
        Mockito.verify(view).setData(githubUser);
        Mockito.verify(view).showContent();
    }

    private void loadInformation(GithubUser githubUser) {
        doReturn(Observable.just(Collections.singletonList(githubUser))).when(model).observeInfo();
        doReturn(Observable.just(Collections.singletonList(githubUser))).when(model).updateInfo();
        presenter.loadInformation(false);
    }

    private void loadInformation() {
        Mockito.when(model.observeInfo()).thenReturn(Observable.empty());
        Mockito.when(model.updateInfo()).thenReturn(Observable.empty());
        presenter.loadInformation(false);
    }
}