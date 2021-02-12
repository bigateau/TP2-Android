package cavard.tp1b.flickrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap>{

    private ImageView image_view;

    public AsyncBitmapDownloader(AppCompatActivity a){
        this.image_view = a.findViewById(R.id.image);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        Bitmap b = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                b = BitmapFactory.decodeStream(in);
                Log.i("AsyncBitmapDownloader", b.toString());
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
    protected void onPostExecute(Bitmap bit) {
        super.onPostExecute(bit);
        try {
            image_view.setImageBitmap(bit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
