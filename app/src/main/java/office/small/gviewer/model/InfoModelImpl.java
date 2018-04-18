package office.small.gviewer.model;

import java.util.ArrayList;
import java.util.Random;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class InfoModelImpl implements InfoModel{
    private ArrayList<String> aData = new ArrayList<String>(20);
    private ArrayList<String> bData = new ArrayList<String>(20);
    private Random randomGenerator = new Random();
    String rString = "";

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

        bData.add("BCC DDD 1");
        bData.add("BCC DDD 2");
        bData.add("BCC DDD 3");
        bData.add("BCC DDD 4");
        bData.add("BCC DDD 5");
        bData.add("BCC DDD 6");
        bData.add("BCC DDD 7");
        bData.add("BCC DDD 8");
        bData.add("BCC DDD 9");
        bData.add("BCC DDD 10");
        bData.add("BCC DDD 11");
        bData.add("BCC DDD 12");
        bData.add("BCC DDD 13");
        bData.add("BCC DDD 14");
        bData.add("BCC DDD 15");
        bData.add("BCC DDD 16");
        bData.add("BCC DDD 17");
        bData.add("BCC DDD 18");
        bData.add("BCC DDD 19");
        bData.add("BCC DDD 20");

    }

    @Override
    public Observable<String> retrieveInfo() {
        final Observable<String> oDataFlow1 = Observable.just(aData.get(randomGenerator.nextInt(aData.size())));
        final Observable<String> oDataFlow2 = Observable.just(" Flow2: " + bData.get(randomGenerator.nextInt(bData.size())));

        oDataFlow1.concatWith(oDataFlow2).subscribeOn(Schedulers.newThread()).subscribe(
                new Action1<String>() {
                    @Override
                    public void call(String s) {
                        rString = rString + " " + s;
                    }
                }
        );

        return Observable.just(rString);
    }
}
