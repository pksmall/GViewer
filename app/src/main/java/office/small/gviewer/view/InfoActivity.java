package office.small.gviewer.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import office.small.gviewer.R;
import office.small.gviewer.model.InfoModel;
import office.small.gviewer.model.InfoModelImpl;
import office.small.gviewer.presenter.InfoPresenter;
import office.small.gviewer.presenter.InfoPresenterImpl;

public class InfoActivity extends AppCompatActivity implements InfoView {
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = findViewById(R.id.info);
        InfoModel model = new InfoModelImpl();
        InfoPresenter presenter = new InfoPresenterImpl(model);
        presenter.loadInformation(this);
    }

    @Override
    public void displayInfo(String text) {
        info.setText(text);
    }
}
