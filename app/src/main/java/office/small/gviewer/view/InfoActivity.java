package office.small.gviewer.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import office.small.gviewer.R;
import office.small.gviewer.model.InfoModel;
import office.small.gviewer.model.InfoModelImpl;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.presenter.InfoPresenterImpl;

public class InfoActivity extends MvpActivity<InfoView, InfoPresenter> implements InfoView {
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = findViewById(R.id.info);
        getPresenter().loadInformation();
    }

    @NonNull
    @Override
    public InfoPresenter createPresenter() {
        return new InfoPresenterImpl(new InfoModelImpl());
    }

    @Override
    public void displayInfo(String text) {
        info.setText(text);
    }
}
