package com.example.pract17xe;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Animal.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "Animal"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_Animal = "Animal";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_Size = "Size";
    public static final String COLUMN_Weight = "Weight";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE +"(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_Animal
                + " TEXT, " + COLUMN_NAME + " TEXT, " + COLUMN_Size + " integer, "
                + COLUMN_Weight + " integer)");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
