package tn.chaker.ProjetAndroid.tn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chaker on 3/31/2016.
 */
public class QuestionActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;
    Question currentQ;
    TextView txtQuestion, times, scored;
    Button button1, button2, button3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuizHelper db = new QuizHelper(this);

        quesList = db.getAllQuestions(1 + (int) Math.random()*5);
        if (quesList.size() == 0){
        quesList = db.getAllQuestions();
            Toast.makeText(this, "Offline game!",
            Toast.LENGTH_LONG).show();

        }else{
        currentQ = quesList.get(qid);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        scored = (TextView) findViewById(R.id.score);

        times = (TextView) findViewById(R.id.timers);
        setQuestionView();
        times.setText("00:01:00");

        CounterClass timer = new CounterClass(60000, 1000);
        timer.start();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button1.getText().toString());
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button2.getText().toString());
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button3.getText().toString());
            }
        });
    }
    }
    public void getAnswer(String AnswerString) {
        if (currentQ.getAnswer().equals(AnswerString)) {
            // if conditions matches increase the int (score) by 1
            // and set the text of the score view
            score++;
            scored.setText("Score : " + score);
        } else {
            // if unlucky start activity and finish the game
            Intent intent = new Intent(QuestionActivity.this,ResultActivity.class);
            // passing the int value
            Bundle b = new Bundle();
            b.putInt("score", score);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
        if (qid < 20) {
            currentQ = quesList.get(qid);
            setQuestionView();
        } else {
            Intent intent = new Intent(QuestionActivity.this,ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
        }
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onFinish() {
            times.setText("Time is up");
            getAnswer("xxxx"); // this gives a false answer so we go to result_activity
            finish();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            long millis = millisUntilFinished;
            String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            // System.out.println(hms);
            times.setText(hms);
        }
    }
    private void setQuestionView() {
        // the method which will put all things together
        txtQuestion.setText(currentQ.getQuestion());
        button1.setText(currentQ.getOptionA());
        button2.setText(currentQ.getOptionB());
        button3.setText(currentQ.getOptionC());
        qid++;
    }
}
