package office.small.gviewer.model;

import android.support.annotation.NonNull;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import rx.Observable;
import rx.schedulers.Schedulers;

public class InfoModelImpl implements InfoModel{
    private String user;
    private OkHttpClient okHttpClient;

    public InfoModelImpl(@NonNull String user, @NonNull OkHttpClient client) {
        this.user = user;
        this.okHttpClient = client;
    }

    @Override
    public Observable<String> retrieveInfo() {
        return Observable.defer(() -> {
            Observable<String> result;
            try {
                result = Observable.just(okHttpClient
                        .newCall(new Request.Builder()
                                .url("https://api.github.com/users/" + user)
                                .build()).execute().body().string());
            } catch (IOException e) {
                result = Observable.error(e);
            }

            return result;
        }).subscribeOn(Schedulers.io());
    }
}
