package edu.olemiss.hpatel3.p4hpatel3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Harsh on 3/29/2015.
 */
public class DBAdapter {
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "scoredb";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table scoreinfo(scoreid integer primary key autoincrement, " + "name text, score integer not null, level text);";

    private final Context context;

    protected DatabaseHelper DBHelper;
    protected SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " +
                    newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS scoreinfo");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertScore(String name, int score, String level) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", name);
        initialValues.put("score", score);
        initialValues.put("level", level);

        return db.insert("scoreinfo", null, initialValues);
    }


    public Cursor bmin() throws SQLException {
        String level  = "beginner";
        String query = "SELECT name, MIN(score) AS bscore FROM scoreinfo where level like '%" + level + "%';";
        Cursor cur = db.rawQuery(query , null);

        if (cur != null) {
            cur.moveToFirst();
        }

        return cur;
    }

    public Cursor imin() throws SQLException {
        String level  = "intermediate";
        String query = "SELECT name, MIN(score) AS iscore FROM scoreinfo where level like '%" + level + "%';";
        Cursor cur = db.rawQuery(query , null);

        if (cur != null) {
            cur.moveToFirst();
        }

        return cur;
    }

    public Cursor emin() throws SQLException {
        String level  = "expert";
        String query = "SELECT name, MIN(score) AS escore FROM scoreinfo where level like '%" + level + "%';";
        Cursor cur = db.rawQuery(query , null);

        if (cur != null) {
            cur.moveToFirst();
        }

        return cur;
    }

    public Cursor cmin() throws SQLException {
        String level  = "custom";
        String query = "SELECT name, MIN(score) AS escore FROM scoreinfo where level like '%" + level + "%';";
        Cursor cur = db.rawQuery(query , null);

        if (cur != null) {
            cur.moveToFirst();
        }

        return cur;
    }


}