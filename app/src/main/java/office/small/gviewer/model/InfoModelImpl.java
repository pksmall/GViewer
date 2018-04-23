package office.small.gviewer.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import office.small.gviewer.model.entity.GithubUser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class InfoModelImpl implements InfoModel{
    private String user;
    private OkHttpClient okHttpClient;
    private Gson gson = new Gson();

    public InfoModelImpl(@NonNull String user, @NonNull OkHttpClient client) {
        this.user = user;
        this.okHttpClient = client;
    }

    @Override
    public Observable<GithubUser> retrieveInfo() {
        return Observable.defer(() -> {
            Observable<String> result;
            Request mRequest = new Request.Builder()
                    .url("https://api.github.com/users/" + user)
                    .build();
            try {
                Response mResponse = okHttpClient.newCall(mRequest).execute();
                if (mResponse.isSuccessful()) {
                    Log.d("INFMODIMP", " RESP CODE: " + mResponse.code());
                    result = Observable.just(mResponse.body().string());
                } else {
                    Log.d("INFMODIMP",
                            " RESP CODE: " + mResponse.code()
                                    + " RESP MESS:" + mResponse.message());
                    //result = Observable.just("{login: 'Get Error', id: 'Get Error', message: 'API rate limit exceeded'}");
                    result = Observable.just(mResponse.body().string());
                }
            } catch (IOException e) {
                Log.d("INFMODIMP", " EX MESS: " + e.getMessage());
                result = Observable.error(e);
            }

            return result;
        }).map(s -> {
            GithubUser gUser =  gson.fromJson(s, GithubUser.class);
            Log.d("INFMODIMP",
                    " Str: " + s
                    + " GLOG: " + gUser.getLogin()
                            + " ID: " + gUser.getId()
                            + " MSG: " + gUser.getMesssage());
            return  gson.fromJson(s, GithubUser.class);

        })
                .subscribeOn(Schedulers.io());
    }
}
