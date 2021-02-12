package cavard.tp1b.flickrapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {
    private final String url;
    public JSONObject json;
    private AppCompatActivity app;

    public AsyncFlickrJSONData(AppCompatActivity a){
        this.url = null;
        this.app = a;
    }

    @Override
    protected JSONObject doInBackground(String... strings){
        URL url = null;
        JSONObject j = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                j = readStream(in);
                Log.i("JFL", j.toString());
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return j;
    }

    private JSONObject readStream(InputStream is) throws IOException {
        try{
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            StringBuilder sb  = new StringBuilder();
            sb.append(bo.toString());
            String jsonextract = sb.substring("jsonFlickrFeed(".length(), sb.length() - 1);
            return new JSONObject(jsonextract);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            String url_to_download = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("media").getString("m");
            Log.i("JSONObject", url_to_download);
            AsyncTask<String, Void, Bitmap> task_download = new AsyncBitmapDownloader(this.app);
            task_download.execute(url_to_download, null, null);
            Log.i("JSONObject", url_to_download);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
