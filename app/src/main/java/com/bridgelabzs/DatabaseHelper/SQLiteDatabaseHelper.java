package com.bridgelabzs.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteDatabaseHelper.class";
    private SQLiteDatabase sqLiteDatabase;

    private static final String DATABASE_NAME = "Application.db";
    private static final int DATABASE_VERSION = 2;

    //USER TABLE COLUMNS
    public static final String USER_TABLE_NAME = "USER_TABLE";
    public static final String USER_TABLE_COL_ID = "id";
    public static final String USER_TABLE_COL_FIRST_NAME = "firstName";
    public static final String USER_TABLE_COL_LAST_NAME = "lastName";
    public static final String USER_TABLE_COL_EMAIL = "email";
    public static final String USER_TABLE_COL_PASSWORD = "password";

    //NOTE TABLE COLUMNS
    public static final String NOTE_TABLE_NAME = "NOTE_TABLE";
    public static final String NOTE_TABLE_COL_ID = "id";
    public static final String NOTE_TABLE_COL_TITLE = "title";
    public static final String NOTE_TABLE_COL_DESCRIPTION = "description";
    public static final String NOTE_TABLE_COL_COLOR = "color";
    //    public static final String NOTE_TABLE_COL_USER_ID = "user_id";
    public static final String NOTE_TABLE_COL_ARCHIVE = "isArchive";
    public static final String NOTE_TABLE_COL_REMINDER = "ifReminder";
//    public static final String NOTE_TABLE_COL_PINNED = "isPinned";
//    public static final String NOTE_TABLE_COL_TRASHED = "isTrashed";

    //LABEL TABLE COLUMNS
    public static final String LABEL_TABLE_NAME = "LABEL_TABLE";
    public static final String LABEL_TABLE_COL_ID = "label_id";
    public static final String LABEL_TABLE_COL_NAME = "label_name";
    public static final String LABEL_TABLE_COL_USER_ID = "user_id";

    //USER TABLE CREATION QUERY
    private static final String CREATE_USER_TABLE_QUERY = "CREATE TABLE " + USER_TABLE_NAME +
            "(" + USER_TABLE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_TABLE_COL_FIRST_NAME + " TEXT, " +
            USER_TABLE_COL_LAST_NAME + " TEXT, " +
            USER_TABLE_COL_EMAIL + " TEXT, " +
            USER_TABLE_COL_PASSWORD + " TEXT " + ")";

    //NOTE TABLE CREATION QUERY
    private static final String CREATE_NOTE_TABLE_QUERY = "CREATE TABLE " + NOTE_TABLE_NAME +
            "(" + NOTE_TABLE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NOTE_TABLE_COL_TITLE + " TEXT, " +
            NOTE_TABLE_COL_DESCRIPTION + " TEXT,"+
            NOTE_TABLE_COL_COLOR + " TEXT, " +
            NOTE_TABLE_COL_REMINDER + " TEXT, " +
            NOTE_TABLE_COL_ARCHIVE + " INTEGER " + ")";
//            NOTE_TABLE_COL_USER_ID + " TEXT, " +
//            NOTE_TABLE_COL_PINNED + " INTEGER, " +
//            NOTE_TABLE_COL_TRASHED + " INTEGER " + ")";


    //LABEL TABLE CREATION QUERY
    public static final String CREATE_LABEL_TABLE_QUERY = "CREATE TABLE " + LABEL_TABLE_NAME +
            "(" + LABEL_TABLE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LABEL_TABLE_COL_NAME + " TEXT, " +
            LABEL_TABLE_COL_USER_ID + " INTEGER, " +
            "FOREIGN KEY(" + LABEL_TABLE_COL_USER_ID + ") REFERENCES " +
            USER_TABLE_NAME + "(" + USER_TABLE_COL_ID + "))";

    private static SQLiteDatabaseHelper databaseHelper;

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static SQLiteDatabaseHelper getDatabaseHelper(Context context) {
        if (databaseHelper == null) {
            synchronized (SQLiteDatabaseHelper.class) {
                if (databaseHelper == null) {
                    databaseHelper = new SQLiteDatabaseHelper(context);
                }
            }
        }
        return databaseHelper;
    }

    public SQLiteDatabase openDb() {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase;
    }

    // close db
    public void closeDb() {
        sqLiteDatabase.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE_QUERY);
        db.execSQL(CREATE_NOTE_TABLE_QUERY);
        db.execSQL(CREATE_LABEL_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + NOTE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LABEL_TABLE_NAME);
        db.execSQL(CREATE_USER_TABLE_QUERY);
        db.execSQL(CREATE_NOTE_TABLE_QUERY);
        db.execSQL(CREATE_LABEL_TABLE_QUERY);
    }

}
