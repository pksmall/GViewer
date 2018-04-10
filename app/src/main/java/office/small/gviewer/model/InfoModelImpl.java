package office.small.gviewer.model;

import android.os.AsyncTask;

public class InfoModelImpl implements InfoModel{
    @Override
    public void  retrieveInfo(final MyAction<String> onNext) {
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return "Foo Bar";
            }

            @Override
            protected void onPostExecute(String s) {
                onNext.onDownloadCallback(s);
            }
        }.execute();
    }
}
