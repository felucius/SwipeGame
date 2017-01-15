package maximedelange.clickgame.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import maximedelange.clickgame.Domain.Player;

/**
 * Created by M on 1/15/2017.
 */

public class Database extends SQLiteOpenHelper{

    // Fields
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "clickgame";
    public final static String TABLE_PLAYER = "player";
    public final static String COLUMN_ID = "id";
    public final static String COLUMN_HIGHSCORE = "highscore";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PLAYER + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HIGHSCORE + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_PLAYER;
        db.execSQL(query);
        onCreate(db);
    }

    public void updateHighscore(int score){
        ContentValues values = new ContentValues();
        values.put(COLUMN_HIGHSCORE, score);

        SQLiteDatabase db = getWritableDatabase();
        db.replace(TABLE_PLAYER, null, values);
        db.close();
    }

    public String getHighscore(){
        String highscore = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PLAYER;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount() <= 0){
            updateHighscore(0);
        }else{
            while(!cursor.isAfterLast()){
                highscore = cursor.getString(cursor.getColumnIndex("highscore"));

                cursor.moveToNext();
            }
        }

        return highscore;
    }
}
