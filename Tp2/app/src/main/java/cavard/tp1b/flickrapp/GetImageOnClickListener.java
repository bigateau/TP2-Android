package cavard.tp1b.flickrapp;

import android.os.AsyncTask;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class GetImageOnClickListener implements View.OnClickListener {

    private AppCompatActivity app; //the main app

    public GetImageOnClickListener(AppCompatActivity MainActivity){
        this.app = MainActivity;
    } //get the main app in parameter is very important

    @Override
    public void onClick(View v) {
        AsyncTask<String, Void, JSONObject> task = new AsyncFlickrJSONData(this.app); //creation of AsyncFlickrJSONData that will use the data get with our app in argument
        task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json", null, null); //execute to the flickr page
    }
}
