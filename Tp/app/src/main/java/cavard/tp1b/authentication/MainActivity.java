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

    public JSONObject json;

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
                    url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    EditText login   = (EditText)findViewById(R.id.login);
                    EditText password   = (EditText)findViewById(R.id.password);
                    String str = login.getText()+":"+password.getText();

                    String basicAuth = "Basic " + Base64.encodeToString(str.getBytes(),
                            Base64.NO_WRAP);
                    urlConnection.setRequestProperty ("Authorization", basicAuth);
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        json = new JSONObject(s);
                        Log.i("JFL", s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = (TextView)findViewById(R.id.result);
                        JSONArray nameArray=json.names();
                        try {
                            JSONArray valArray = json.toJSONArray(nameArray);
                            tv.setText("authenticated : "+valArray.getString(0) + ", user : " +valArray.getString(1));
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
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}