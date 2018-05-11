package office.small.gviewer.di;

import dagger.Component;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.view.InfoActivity;

@Component(modules = { InfoModelModule.class })
public interface InfoComponent {

    void inject(InfoActivity activity);

    InfoPresenter presenter();

}