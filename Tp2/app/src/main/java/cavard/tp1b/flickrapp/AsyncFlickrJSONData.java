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

    private AppCompatActivity app; //main activity

    public AsyncFlickrJSONData(AppCompatActivity a){
        this.app = a; //stored our main activity
    }

    @Override
    protected JSONObject doInBackground(String... strings){
        URL url = null;
        JSONObject j = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); //open a connection
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream()); //the Stream into a buffer
                j = readStream(in); //json returned with the stream analysed
                Log.i("JFL", j.toString()); //a log to control the result
            } finally {
                urlConnection.disconnect(); //disconection
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
            ByteArrayOutputStream bo = new ByteArrayOutputStream(); //creation of a nex Stream
            int i = is.read(); //lecture of our stream in argument
            while (i != -1) { // while we still have line to read
                bo.write(i); //whrote the line
                i = is.read(); //i = next line
            }
            StringBuilder sb  = new StringBuilder(); //new string builder
            sb.append(bo.toString()); //add the "big" Stream into our string builder
            String jsonextract = sb.substring("jsonFlickrFeed(".length(), sb.length() - 1); //delete the words that we don't whant for our JSON object
            return new JSONObject(jsonextract); //new JSON object with our string well formed
        } catch (JSONException e) {
            return new JSONObject(); //retrun a JSON object
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) { //after the execution
        super.onPostExecute(jsonObject);
        try {
            String url_to_download = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("media").getString("m"); //we get the first image URL
            AsyncTask<String, Void, Bitmap> task_download = new AsyncBitmapDownloader(this.app); //we start a nex class withe the app in parameter
            task_download.execute(url_to_download, null, null); //we execute the download of our picture
            Log.i("JSONObject", url_to_download); //a log to control the result
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
