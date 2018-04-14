package office.small.gviewer.model;

import android.util.Log;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public class InfoModelImpl implements InfoModel{
    private ArrayList<String> aData = new ArrayList<String>(20);
    private Random randomGenerator = new Random();
    private boolean aRandomizer = true;

    public InfoModelImpl() {
        aData.add("Aaaa Bbbbb BBB 1");
        aData.add("Aaaa Bbbbb BBB 2");
        aData.add("Aaaa Bbbbb BBB 3");
        aData.add("Aaaa Bbbbb BBB 4");
        aData.add("Aaaa Bbbbb BBB 5");
        aData.add("Aaaa Bbbbb BBB 6");
        aData.add("Aaaa Bbbbb BBB 7");
        aData.add("Aaaa Bbbbb BBB 8");
        aData.add("Aaaa Bbbbb BBB 9");
        aData.add("Aaaa Bbbbb BBB 10");
        aData.add("Aaaa Bbbbb BBB 11");
        aData.add("Aaaa Bbbbb BBB 12");
        aData.add("Aaaa Bbbbb BBB 13");
        aData.add("Aaaa Bbbbb BBB 14");
        aData.add("Aaaa Bbbbb BBB 15");
        aData.add("Aaaa Bbbbb BBB 16");
        aData.add("Aaaa Bbbbb BBB 17");
        aData.add("Aaaa Bbbbb BBB 18");
        aData.add("Aaaa Bbbbb BBB 19");
        aData.add("Aaaa Bbbbb BBB 20");
    }

    @Override
    public Observable<String> retrieveInfo() {
        return Observable.timer(1L, TimeUnit.SECONDS)
                .flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long aLong) {
                        Observable<String> result;
                        double random = Math.random();
                        if (random > 0.5 && random < 0.75) {
                           result = Observable.just(aData.get(randomGenerator.nextInt(aData.size())) + " " + random);
                        } else if (random > 0.75) {
                            result = Observable.just(aData.get(randomGenerator.nextInt(aData.size()))  + " !!!!" + random);
                        } else {
                            result = Observable.error(new IllegalStateException("Something wrong. Checking..."));
                        }
                        return result;
                    }
                });
    }
}
