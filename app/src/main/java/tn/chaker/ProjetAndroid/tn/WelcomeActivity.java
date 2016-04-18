package tn.chaker.ProjetAndroid.tn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Chaker on 4/1/2016.
 */
public class WelcomeActivity extends Activity{

    Button playbtn , hsbtn , exitbtn , playlivebtn , aboutbnt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        playbtn = (Button) findViewById(R.id.playbtn);
        hsbtn = (Button) findViewById(R.id.hsbtn);
        exitbtn = (Button) findViewById(R.id.exitbtn);
        aboutbnt = (Button) findViewById(R.id.aboutbtn);

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,QuestionActivity.class);
                startActivity(intent);
                finish();
            }
        });


        hsbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,HighScoreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aboutbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                WelcomeActivity.this.finish();
            }
        });
    }
}
