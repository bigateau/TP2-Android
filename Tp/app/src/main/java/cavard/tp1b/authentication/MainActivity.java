package cavard.tp1b.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
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

public class MainActivity extends AppCompatActivity {

    public JSONObject json; //json object where the result will be set

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) { //button action
        Thread  t1 = new Thread(){
            public void run() {
                URL url = null;
                try {
                    url = new URL("https://httpbin.org/basic-auth/bob/sympa"); //the URL
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); //the connection
                    EditText login   = (EditText)findViewById(R.id.login); //we get our EditTexts to have the content
                    EditText password   = (EditText)findViewById(R.id.password);
                    String str = login.getText()+":"+password.getText(); //we join them to have only one string

                    String basicAuth = "Basic " + Base64.encodeToString(str.getBytes(), //Preparation of the authentification by passing in argument our string
                            Base64.NO_WRAP);
                    urlConnection.setRequestProperty ("Authorization", basicAuth); //set the property to do the connection
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream()); //Result of the connection
                        String s = readStream(in); //result of the analyse
                        json = new JSONObject(s); //creation of JSONObject with the previous result
                        Log.i("JFL", s); //a log with our result
                    } catch (JSONException e) { //exception
                        e.printStackTrace();
                    } finally {
                        urlConnection.disconnect(); //disconection
                    }
                } catch (IOException e) { //exception
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView)findViewById(R.id.result); //we get the TextView where we want the result to be display
                        JSONArray nameArray = json.names(); //we get the names of our json result
                        try {
                            JSONArray valArray = json.toJSONArray(nameArray); //decompose our JSONArray to have access to the splitted data
                            tv.setText("authenticated : "+valArray.getString(0) + ", user : " +valArray.getString(1)); //we set the text
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    };
                });
            }
        };
        t1.start();
    }


    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder(); //creation of a stringBuilder
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000); //creation of a BufferedReader with the stream in argument
        for (String line = r.readLine(); line != null; line =r.readLine()){//decompostition of the result while line != null
            sb.append(line); //add the line in our StringBuilder
        }
        is.close(); //end of our stream
        return sb.toString(); //return the string
    }
}