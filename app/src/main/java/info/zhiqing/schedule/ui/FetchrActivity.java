package info.zhiqing.schedule.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import info.zhiqing.schedule.R;
import info.zhiqing.schedule.models.Database;
import info.zhiqing.schedule.models.Score;
import info.zhiqing.schedule.util.ScheduleFetchr;

public class FetchrActivity extends AppCompatActivity {
    private final String TAG = "FetchrActivity";

    TextInputEditText numberEditText;
    TextInputEditText passwordEditText;
    TextInputEditText codeEditText;
    ImageView codeImageView;
    Button fetchrButton;

    ScheduleFetchr fetchr = new ScheduleFetchr("14101010607", "lzq1997201");

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
                new ScoreFetchrTask().execute(codeEditText.getText().toString());
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

    class ScoreFetchrTask extends AsyncTask<String, Void, List<Score>>{

        @Override
        protected List<Score> doInBackground(String... params) {
            List<Score> scores = null;

            fetchr.logIn(params[0]);

            scores = fetchr.fetchScore();

            Database db = new Database(FetchrActivity.this);
            db.replaceScores(scores);

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
