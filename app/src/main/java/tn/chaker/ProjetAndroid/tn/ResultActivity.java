package tn.chaker.ProjetAndroid.tn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Chaker on 3/31/2016.
 */
public class ResultActivity extends Activity {

    Button backmenubtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textResult = (TextView) findViewById(R.id.textResult);
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        textResult.setText("Your score is " + " " + score + ". Thanks for playing!");
        SharedPreferences prefs = this.getSharedPreferences("highScore", Context.MODE_PRIVATE);
        int hscore = prefs.getInt("key", 0);
        if(score > hscore){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("key", score);
            editor.commit();
        }

        backmenubtn = (Button) findViewById(R.id.backmenubtn);

        backmenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
    public void playagain(View o) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}