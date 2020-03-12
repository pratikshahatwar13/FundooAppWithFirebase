package com.bridgelabzs.login.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper;

import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.USER_TABLE_COL_EMAIL;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.USER_TABLE_COL_FIRST_NAME;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.USER_TABLE_COL_ID;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.USER_TABLE_COL_LAST_NAME;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.USER_TABLE_COL_PASSWORD;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.USER_TABLE_NAME;

public class UserDatabaseManager implements DatabaseManager {

        private static final String TAG = "DatabaseHelper.class";
        private SQLiteDatabaseHelper databaseHelper;
        public UserDatabaseManager(Context context){
            databaseHelper = SQLiteDatabaseHelper.getDatabaseHelper(context);
        }

        public boolean addUser(User user) {
            SQLiteDatabase db = databaseHelper.openDb();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_TABLE_COL_FIRST_NAME, user.getFirstName());
            contentValues.put(USER_TABLE_COL_LAST_NAME, user.getLastName());
            contentValues.put(USER_TABLE_COL_EMAIL, user.getEmailId());
            contentValues.put(USER_TABLE_COL_PASSWORD, user.getPassword());

            long res = db.insert(USER_TABLE_NAME, null, contentValues);
            db.close();

            Log.e(TAG, " res is " + res);
            return res > 0;
        }

        public boolean authenticateUser(String email, String password) {

            Log.e("authenticate",email);
            Log.e("authenticate",password);
            SQLiteDatabase db = databaseHelper.openDb();
            String[] columns = {USER_TABLE_COL_ID};
            String selection = USER_TABLE_COL_EMAIL + "=?" + " and " + USER_TABLE_COL_PASSWORD + "=?";
            String[] selectionArgs = {email, password};
            Cursor cursor = db.query("USER_TABLE", columns, selection, selectionArgs, null, null, null);
            Log.e("openDatabase",db.toString());
            int count = cursor.getCount();
            cursor.close();
            db.close();
            Log.e(TAG, "count is " + count);

            return count > 0;
        }


        public boolean checkUserName(String email){
            SQLiteDatabase db = databaseHelper.openDb();
            Cursor cursor = db.query(USER_TABLE_NAME, new String[] {USER_TABLE_COL_ID,USER_TABLE_COL_FIRST_NAME,USER_TABLE_COL_LAST_NAME,USER_TABLE_COL_EMAIL,USER_TABLE_COL_PASSWORD
                             }, USER_TABLE_COL_EMAIL + "=" + "'" + USER_TABLE_COL_PASSWORD+ "'",
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return true;
            }
            return false;
        }
}
