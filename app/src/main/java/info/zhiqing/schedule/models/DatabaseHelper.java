package info.zhiqing.schedule.models;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhiqing on 16-10-15.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, "database.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE scores ( "
                + "id INTEGER PRIMARY KEY, "
                + "year TEXT, "
                + "semester TEXT, "
                + "code TEXT, "
                + "name TEXT, "
                + "type TEXT, "
                + "credit REAL, "
                + "usual REAL, "
                + "exam REAL, "
                + "score REAL, "
                + "resit REAL )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
