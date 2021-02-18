package cavard.tp1b.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONObject;

public class ListActivity extends AppCompatActivity {

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView list = findViewById(R.id.list); //get the list view

        adapter = new MyAdapter(this); //adapter
        list.setAdapter(adapter); //set the adapter to the list

        AsyncTask<String, Void, JSONObject> task = new AsyncFlickrJSONDataForList(adapter); //asynctask for a list
        task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json", null, null); //link of pictures
    }
}