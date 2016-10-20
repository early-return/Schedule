package info.zhiqing.schedule.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import info.zhiqing.schedule.MainActivity;
import info.zhiqing.schedule.R;
import info.zhiqing.schedule.models.Database;
import info.zhiqing.schedule.models.Score;
import info.zhiqing.schedule.util.Fetchr;

public class FetchrActivity extends AppCompatActivity {
    private final String TAG = "FetchrActivity";

    public static final String BROADCAST = "info.zhiqing.schedule.FETCHED";

    TextInputEditText numberEditText;
    TextInputEditText passwordEditText;
    TextInputEditText codeEditText;
    ImageView codeImageView;
    Button fetchrButton;

    Fetchr fetchr = new Fetchr("14101010607", "lzq1997201");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchr);

        numberEditText = (TextInputEditText) findViewById(R.id.numberEditText);
        passwordEditText = (TextInputEditText) findViewById(R.id.passwordEditText);
        codeEditText = (TextInputEditText) findViewById(R.id.codeEditText);
        codeImageView = (ImageView) findViewById(R.id.codeImageView);
        fetchrButton = (Button) findViewById(R.id.fetchrButton);

        new CodeFetchrTask().execute();

        codeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CodeFetchrTask().execute();
            }
        });

        fetchrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchr.setNumber(numberEditText.getText().toString());
                fetchr.setPass(passwordEditText.getText().toString());
                new DataFetchrTask().execute(codeEditText.getText().toString());
            }
        });

        codeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() != 4){
                    codeEditText.setError("请输入正确格式的验证码");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    class CodeFetchrTask extends AsyncTask<Void, Void, byte[]>{

        @Override
        protected byte[] doInBackground(Void... params) {
            byte[] result = null;
            try{
                result = fetchr.fetchCodeImageBytes();
            } catch (IOException ioe){

            }
            return result;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            Bitmap bitmap = null;
            if(bytes.length != 0){
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);;
            }
            codeImageView.setImageBitmap(bitmap);
        }
    }

    class DataFetchrTask extends AsyncTask<String, Void, List<Score>>{

        @Override
        protected List<Score> doInBackground(String... params) {
            List<Score> scores = null;

            fetchr.logIn(params[0]);

            scores = fetchr.fetchScore();

            Database db = new Database(FetchrActivity.this);
            db.replaceScores(scores);

            SharedPreferences.Editor editor = getApplication()
                    .getSharedPreferences(MainActivity.SHARED_PREFER, MODE_PRIVATE).edit();

            editor.putString(MainActivity.SHARED_PREFER_NAME, fetchr.getName());
            editor.putString(MainActivity.SHARED_PREFER_NUMBER, fetchr.getNumber());
            editor.putString(MainActivity.SHARED_PREFER_COLLEGE, fetchr.getCollege());
            editor.putString(MainActivity.SHARED_PREFER_MAJOR, fetchr.getMajor());
            editor.putString(MainActivity.SHARED_PREFER_SEX, fetchr.getSex());
            editor.putString(MainActivity.SHARED_PREFER_PROSPECT, fetchr.getProspect());
            editor.putString(MainActivity.SHARED_PREFER_POLITICAL, fetchr.getPolitical());
            editor.putString(MainActivity.SHARED_PREFER_FROM, fetchr.getFrom());
            editor.putString(MainActivity.SHARED_PREFER_MIDDLE_SCHOOL, fetchr.getMiddleSchool());
            editor.putString(MainActivity.SHARED_PREFER_NATION, fetchr.getNation());
            editor.putString(MainActivity.SHARED_PREFER_ID, fetchr.getId());
            editor.putString(MainActivity.SHARED_PREFER_QUALIFICATION, fetchr.getQualification());
            editor.putString(MainActivity.SHARED_PREFER_CLASS, fetchr.getFromClass());
            editor.commit();

            Intent intent = new Intent(BROADCAST);
            sendBroadcast(intent);

            return scores;
        }

        @Override
        protected void onPostExecute(List<Score> scores) {
            super.onPostExecute(scores);
            for(Score score: scores){
                Log.d(TAG, score.toString());
            }

            Toast.makeText(FetchrActivity.this, "更新数据成功", Toast.LENGTH_SHORT).show();

            FetchrActivity.this.finish();
        }
    }
}
