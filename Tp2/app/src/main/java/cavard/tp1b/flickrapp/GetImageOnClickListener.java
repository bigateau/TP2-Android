package cavard.tp1b.flickrapp;

import android.os.AsyncTask;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class GetImageOnClickListener implements View.OnClickListener {

    private AppCompatActivity app;

    public GetImageOnClickListener(AppCompatActivity MainActivity){
        this.app = MainActivity;
    }

    @Override
    public void onClick(View v) {
        AsyncTask<String, Void, JSONObject> task = new AsyncFlickrJSONData(this.app);
        task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json", null, null);
    }
}
