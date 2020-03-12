package com.bridgelabzs.add_label_page.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.LABEL_TABLE_COL_ID;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.LABEL_TABLE_COL_NAME;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.LABEL_TABLE_COL_USER_ID;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.LABEL_TABLE_NAME;

public class LabelDatabaseManager {
    private static final String TAG = "DatabaseHelper.class";
    private SQLiteDatabaseHelper databaseHelper;
    private List<Label> labelList;

    public LabelDatabaseManager(Context context){
        databaseHelper = SQLiteDatabaseHelper.getDatabaseHelper(context);
    }

    public boolean addLabel(Label label){
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LABEL_TABLE_COL_NAME, label.getLabelName());
        contentValues.put(LABEL_TABLE_COL_USER_ID, label.getLabelId());

        long res = db.insert(LABEL_TABLE_NAME, null, contentValues);
        db.close();

        Log.e(TAG, "res is " + res);
        return res > 0;
    }
    public  boolean deleteLabel(Label label){
        SQLiteDatabase db = databaseHelper.openDb();
        long res = db.delete(LABEL_TABLE_NAME, LABEL_TABLE_COL_ID + "=?" ,
                new String[]{String.valueOf(label.getLabelId())});
        Log.e(TAG, "item deleted");
        return  res > 0;
    }
    public boolean updateLabel(Label labelToEdit){
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LABEL_TABLE_COL_NAME, labelToEdit.getLabelName());

        long res = db.update(LABEL_TABLE_NAME, contentValues, LABEL_TABLE_COL_ID + "=?" ,
                new String[]{labelToEdit.getLabelId() + ""});
        db.close();

        Log.e(TAG, "res is " + res);
        return res > 0;


    }
    public List<Label> getAllLabelData(){
        List<Label> labelList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LABEL_TABLE_NAME + ";" , null );
        Label label;
        while (cursor.moveToNext()){
            int label_id = cursor.getInt(cursor.getColumnIndexOrThrow(LABEL_TABLE_COL_ID));
            int user_id = cursor.getInt(cursor.getColumnIndexOrThrow(LABEL_TABLE_COL_USER_ID));
            String label_name = cursor.getString(cursor.getColumnIndexOrThrow(LABEL_TABLE_COL_NAME));
            label = new Label(label_name, user_id);
            label.setLabelId(label_id);
            labelList.add(label);

        }
        cursor.close();
        return labelList;
    }
    public boolean deleteAllLabels(){
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LABEL_TABLE_NAME + " ; ", null);
        boolean isLabelDeleted = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return  isLabelDeleted;
    }
}
