package cavard.tp1b.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.get_image); //first button
        button.setOnClickListener(new GetImageOnClickListener(MainActivity.this)); //Onclick to display an image, withe mainActivity in argument, it's is fondamental
        Button button2 = findViewById(R.id.send_user); //second button
        button2.setOnClickListener(new View.OnClickListener() { //OnClick for the second button
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListActivity.class); //redirection to a second page
                startActivity(i);
            }
        });
    }

}