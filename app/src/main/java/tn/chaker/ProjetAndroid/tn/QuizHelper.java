package tn.chaker.ProjetAndroid.tn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chaker on 3/31/2016.
 */
public class QuizHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mathsone";
    // tasks table name
    private static final String TABLE_QUEST = "quest";
    // tasks Table Columns names
    private static final String KEY_ID = "qid";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer"; // correct option
    private static final String KEY_OPTA = "opta"; // option a
    private static final String KEY_OPTB = "optb"; // option b
    private static final String KEY_OPTC = "optc"; // option c

    private SQLiteDatabase dbase;

    public QuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT, " + KEY_OPTC + " TEXT)";
        db.execSQL(sql);
        addQuestion();
    }
    private void addQuestion() {
        this.addQuestion(new Question("5+2 = ?", "7", "8", "6", "7"));
        this.addQuestion(new Question("2+18 = ?", "18", "19", "20", "20"));
        this.addQuestion(new Question("10-3 = ?", "6", "7", "8", "7"));
        this.addQuestion(new Question("5+7 = ?", "12", "13", "14", "12"));
        this.addQuestion(new Question("3-1 = ?", "1", "3", "2", "2"));
        this.addQuestion(new Question("0+1 = ?", "1", "0", "10", "1"));
        this.addQuestion(new Question("9-9 = ?", "0", "9", "1", "0"));
        this.addQuestion(new Question("3+6 = ?", "8", "7", "9", "9"));
        this.addQuestion(new Question("1+5 = ?", "6", "7", "5", "6"));
        this.addQuestion(new Question("7-5 = ?", "3", "2", "6", "2"));
        this.addQuestion(new Question("7-2 = ?", "7", "6", "5", "5"));
        this.addQuestion(new Question("3+5 = ?", "8", "7", "5", "8"));
        this.addQuestion(new Question("0+6 = ?", "7", "6", "5", "6"));
        this.addQuestion(new Question("12-10 = ?", "1", "2", "3", "2"));
        this.addQuestion(new Question("12+2 = ?", "14", "15", "16", "14"));
        this.addQuestion(new Question("2-1 = ?", "2", "1", "0", "1"));
        this.addQuestion(new Question("6-6 = ?", "6", "12", "0", "0"));
        this.addQuestion(new Question("5-1 = ?", "4", "3", "2", "4"));
        this.addQuestion( new Question("4+2 = ?", "6", "7", "5", "6"));
        this.addQuestion(new Question("5+1 = ?", "6", "7", "5", "6"));
        this.addQuestion(new Question("5-4 = ?", "5", "4", "1", "1"));
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);

        onCreate(db);
    }
    // Adding new question
    public void addQuestion(Question quest) {
        // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQuestion());
        values.put(KEY_ANSWER, quest.getAnswer());
        values.put(KEY_OPTA, quest.getOptionA());
        values.put(KEY_OPTB, quest.getOptionB());
        values.put(KEY_OPTC, quest.getOptionC());
        dbase.insert(TABLE_QUEST, null, values);

    }
    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_QUEST;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQuestion(cursor.getString(1));
                quest.setAnswer(cursor.getString(2));
                quest.setOptionA(cursor.getString(3));
                quest.setOptionB(cursor.getString(4));
                quest.setOptionC(cursor.getString(5));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        Collections.shuffle(quesList);
        cursor.close();
        return quesList;
    }

    public List<Question> getAllQuestions(int JSONFile) {
        List<Question> quesList = new ArrayList<Question>();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .penaltyLog()
                .penaltyDialog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
                .penaltyLog()
                .build());
        JSONObject json = null;
        String str = "";
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost myConnection = new HttpPost("http://chaker.tn/"+JSONFile+".json");

        try {
            response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{

            JSONArray jArray = new JSONArray(str);
            for (int i=0 ; i< jArray.length() ; i++){
                json = jArray.getJSONObject(i);
                Question quest = new Question();
                quest.setQuestion(json.getString("question"));
                quest.setAnswer(json.getString("answer"));
                quest.setOptionA(json.getString("opta"));
                quest.setOptionB(json.getString("optb"));
                quest.setOptionC(json.getString("optc"));
                quesList.add(quest);
            }
        } catch ( JSONException e) {
            e.printStackTrace();
        }
        // return quest list
        Collections.shuffle(quesList);
        return quesList;
    }
}