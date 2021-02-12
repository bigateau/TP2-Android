package cavard.tp1b.flickrapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Iterator;
import java.util.Vector;

public class MyAdapter extends BaseAdapter {

    private Vector<String> vector = new Vector<String>();
    private Context context;

    public MyAdapter(Context cont){
        this.context = cont;
    }

    public void add(String url){
        this.vector.add(url);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*if (convertView == null) { // Check if an existing view is being reused, otherwise inflate the view
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_list, parent, false);
        }
        // Lookup view for data population
        TextView tv = (TextView) convertView.findViewById(R.id.textviewlayout); //créer un item similaire au truc séléctionné
        tv.setText(getItem(position).toString()); // Populate the data into the template view using the data object*/
        if (convertView == null) { // Check if an existing view is being reused, otherwise inflate the view
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.bitmaplayout, parent, false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
        RequestQueue queue = MySingleton.getInstance(image.getContext()).getRequestQueue();
        ImageRequest ir = new ImageRequest(getItem(position).toString(), (Response.Listener<Bitmap>) image::setImageBitmap, 400, 400, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JFL", "Image error");
            }
        });
        queue.add(ir);

        return convertView; // Return the completed view to render on screen


        //Log.i("JFL", "TODO");
        //return null;


    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
       return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
