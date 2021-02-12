package cavard.tp1b.flickrapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject>{

    private MyAdapter adapter;

    public AsyncFlickrJSONDataForList(MyAdapter ada){
        this.adapter = ada;
    }

    protected JSONObject doInBackground(String... strings){
        URL url = null;
        JSONObject j = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                j = readStream(in);
                //Log.i("JFL", j.toString());
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
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i<items.length(); i++)
            {
                Log.i("JFL", "Adding to adapter url : " + items.getJSONObject(i).getJSONObject("media").getString("m"));
                adapter.add(items.getJSONObject(i).getJSONObject("media").getString("m"));
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
