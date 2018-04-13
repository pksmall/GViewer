package office.small.gviewer.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.Random;

public class InfoModelImpl implements InfoModel{
    private ArrayList<String> aData = new ArrayList<String>(20);
    private Random randomGenerator = new Random();
    private boolean aRandomizer = true;

    public InfoModelImpl() {
        Log.d("CONST-INCOMODELIMP"," aRand: " + aRandomizer);
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

    @SuppressLint("StaticFieldLeak")
    @Override
    public void  retrieveInfo(final MyAction<String> onNext) {
        if (aRandomizer == false) {
            aRandomizer = true;
        } else {
            aRandomizer = false;
        }
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Log.d("RETINFO-INCOMODELIMP", " aRand1: " + aRandomizer);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return aData.get(randomGenerator.nextInt(aData.size()));
            }

            @Override
            protected void onPostExecute(String s) {
                //Log.d("RETINFO-INCOMODELIMP", " aRand3: " + aRandomizer);
                if (aRandomizer) s = "";
                onNext.onDownloadCallback(s);
            }
        }.execute();
    }
}
