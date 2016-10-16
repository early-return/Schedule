package info.zhiqing.schedule.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiqing on 16-10-15.
 */

public class Database {
    private static final String TAG = "Database";
    SQLiteDatabase db = null;

    public Database(Context context){
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public void inserScore(Score score){
        ContentValues cv = new ContentValues();
        cv.put("year", score.getYear());
        cv.put("semester", score.getSemester());
        cv.put("code", score.getCode());
        cv.put("name", score.getName());
        cv.put("type", score.getType());
        cv.put("credit", score.getCredit());
        cv.put("usual", score.getUsual());
        cv.put("exam", score.getExam());
        cv.put("score", score.getScore());
        cv.put("resit", score.getResit());
        long n = db.insert("scores", null, cv);

        Log.d(TAG, "Insert " + n);
    }

    public void insertScores(List<Score> scores) {
        for(Score score : scores) {
            inserScore(score);
        }
    }

    public void replaceScores(List<Score> scores) {
        db.delete("scores", null, null);
        insertScores(scores);
    }

    public List<Score> getScores(String year, String semester){
        List<Score> scores = new ArrayList<>();

        String sql = "SELECT * FROM scores ";
        String where = "";
        String and = "";
        String yearWhere = "";
        String semesterWhere = "";

        if(!year.equals("") && !year.equals("*")){
            yearWhere = " year='" + year + "' ";
            where = " WHERE ";
        }

        if(!semester.equals("") && !semester.equals("*")){
            semesterWhere = " semester='" + semester + "'";
            if(!where.equals("")){
                and = " AND ";
            } else {
                where = " WHERE ";
            }
        }
        Cursor c = db.rawQuery(sql + where + yearWhere + and + semesterWhere + ";" , null);

        Log.d(TAG, "Get " + c.getCount());
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                scores.add(new Score(
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getFloat(6),
                        c.getFloat(7),
                        c.getFloat(8),
                        c.getFloat(9),
                        c.getFloat(10)
                ));
                c.moveToNext();
            }
        }
        return scores;
    }

    public List<Score> getScores(){
        return getScores("*", "*");
    }

    public String[] getYears(){
        List<String> years = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT DISTINCT year FROM scores", null);

        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                years.add(c.getString(0));
                c.moveToNext();
            }
        }
        return years.toArray(new String[0]);
    }
}
