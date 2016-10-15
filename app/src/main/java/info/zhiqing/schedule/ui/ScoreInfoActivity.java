package info.zhiqing.schedule.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import info.zhiqing.schedule.R;
import info.zhiqing.schedule.models.Score;

public class ScoreInfoActivity extends AppCompatActivity {

    private Score score = null;
    public static String EXTRA_SCORE = "info.zhiqing.schedule.ScoreInfoActivity.EXTRA_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_info);

        Intent intent = getIntent();
        score = (Score)intent.getSerializableExtra(EXTRA_SCORE);

        ((TextView) findViewById(R.id.scoreInfoNameTextView)).setText(score.getName());
        ((TextView) findViewById(R.id.scoreInfoYearTextView)).setText(score.getYear());
        ((TextView) findViewById(R.id.scoreInfoSemesterTextView)).setText(score.getSemester());
        ((TextView) findViewById(R.id.scoreInfoCodeTextView)).setText(score.getCode());
        ((TextView) findViewById(R.id.scoreInfoTypeTextView)).setText(score.getType());
        ((TextView) findViewById(R.id.scoreInfoCreditTextView)).setText(score.getCredit() + "");
        ((TextView) findViewById(R.id.scoreInfoUsualTextView)).setText(score.getUsual() + "");
        ((TextView) findViewById(R.id.scoreInfoExamTextView)).setText(score.getExam() + "");
        ((TextView) findViewById(R.id.scoreInfoScoreTextView)).setText(score.getScore() + "");
        ((TextView) findViewById(R.id.scoreInfoResitTextView)).setText(score.getResit() + "");
    }
}
