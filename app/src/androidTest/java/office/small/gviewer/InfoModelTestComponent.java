package office.small.gviewer;

import dagger.Component;
import office.small.gviewer.di.InfoModelModule;

@Component(modules = InfoModelModule.class)
public interface InfoModelTestComponent {

    void inject(InfoModelImplTest test);

}