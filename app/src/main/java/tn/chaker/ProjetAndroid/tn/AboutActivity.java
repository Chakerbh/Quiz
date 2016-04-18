package tn.chaker.ProjetAndroid.tn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Chaker on 4/14/2016.
 */
public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button backbtn = (Button) findViewById(R.id.backbtn);
        TextView chaker = (TextView) findViewById(R.id.chakertxt);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "font1.ttf");
        chaker.setTypeface(typeface);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
