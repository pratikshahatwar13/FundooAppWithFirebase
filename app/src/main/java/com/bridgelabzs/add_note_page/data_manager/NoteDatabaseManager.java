package com.bridgelabzs.add_note_page.data_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper;
import com.bridgelabzs.add_note_page.model.Note;

import java.util.ArrayList;
import java.util.List;

import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.NOTE_TABLE_COL_ARCHIVE;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.NOTE_TABLE_COL_COLOR;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.NOTE_TABLE_COL_DESCRIPTION;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.NOTE_TABLE_COL_ID;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.NOTE_TABLE_COL_REMINDER;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.NOTE_TABLE_COL_TITLE;
import static com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper.NOTE_TABLE_NAME;

public class NoteDatabaseManager implements DatabaseManager {

    private static final String TAG = "DatabaseHelper.class";

    private SQLiteDatabaseHelper databaseHelper;
    List<Note> noteList;

    public NoteDatabaseManager(Context context) {
        databaseHelper = SQLiteDatabaseHelper.getDatabaseHelper(context);
    }

    public boolean addNote(Note note) {
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COL_TITLE, note.getTitle());
        contentValues.put(NOTE_TABLE_COL_DESCRIPTION, note.getDescription());
        contentValues.put(NOTE_TABLE_COL_COLOR,note.getColor());
        contentValues.put(NOTE_TABLE_COL_REMINDER,note.getReminder());
        long res = db.insert(NOTE_TABLE_NAME, null, contentValues);
        db.close();

        Log.e(TAG, "res is " + res);
        return res > 0;

    }

    public boolean addArchieNote(Note note){
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COL_TITLE, note.getTitle());
        contentValues.put(NOTE_TABLE_COL_DESCRIPTION, note.getDescription());
        contentValues.put(NOTE_TABLE_COL_COLOR,note.getColor());
        contentValues.put(NOTE_TABLE_COL_REMINDER,note.getReminder());
        long res = db.insert(NOTE_TABLE_NAME, null, contentValues);
        db.close();

        Log.e(TAG, "res is " + res);
        return res > 0;
    }

    public boolean addNoteIfReminder(Note note){
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COL_TITLE, note.getTitle());
        contentValues.put(NOTE_TABLE_COL_DESCRIPTION, note.getDescription());
        contentValues.put(NOTE_TABLE_COL_COLOR,note.getColor());
        contentValues.put(NOTE_TABLE_COL_REMINDER,note.getReminder());
        long res = db.insert(NOTE_TABLE_NAME, null, contentValues);
        db.close();

        Log.e(TAG, "res is " + res);
        return res > 0;
    }

    public boolean addAndUpdateNote(Note note){
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COL_TITLE, note.getTitle());
        contentValues.put(NOTE_TABLE_COL_DESCRIPTION, note.getDescription());
        contentValues.put(NOTE_TABLE_COL_COLOR,note.getColor());
        int rows = db.update(NOTE_TABLE_NAME,contentValues,NOTE_TABLE_COL_ID + " = " + note.getId(),null );
        if(rows == 0){
            contentValues.put(NOTE_TABLE_COL_ID,note.getId());
            long res = db.insert(NOTE_TABLE_NAME,null,contentValues);
            Log.e(TAG, "res is " + res);
        }
        return rows > 0;
    }

    public boolean addListOfNotesToDb(List<Note> noteList) {
        SQLiteDatabase db = databaseHelper.openDb();
        long res = 0;
        for (Note note :
                noteList) {

            ContentValues contentValues = getContentValuesFromNote(note);
            res = db.insert(NOTE_TABLE_NAME, null, contentValues);

        }
        Log.e(TAG, "addListOfNotesToDb: loop" + noteList.toString());

        db.close();
        Log.e(TAG, "res is!!!!!!!!! " + res);
        return res > 0;

    }

    public ContentValues getContentValuesFromNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COL_ID,note.getId());
        contentValues.put(NOTE_TABLE_COL_TITLE, note.getTitle());
        contentValues.put(NOTE_TABLE_COL_DESCRIPTION, note.getDescription());
        contentValues.put(NOTE_TABLE_COL_COLOR, note.getColor());
        contentValues.put(NOTE_TABLE_COL_REMINDER, note.getReminder().isEmpty() ? "" :
                note.getReminder());
//        contentValues.put(NOTE_TABLE_COL_ARCHIVE, model.getIsArchived());
//        contentValues.put(NOTE_TABLE_COL_PINNED, model.getIsPinned());
//        contentValues.put(NOTE_TABLE_COL_TRASHED, model.getIsDeleted());
        return contentValues;
    }

    public List<Note> getAllNoteData() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + NOTE_TABLE_NAME + " ; ", null);
        while (cursor.moveToNext()) {
            noteList = getNoteListFromCursor(cursor, noteList);
        }
        cursor.close();
        return noteList;
    }

    public List<Note> getNoteListFromCursor(Cursor cursor,
                                             List<Note> noteList) {
        String id = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TITLE));

        String description = cursor.getString(cursor.getColumnIndexOrThrow
                (NOTE_TABLE_COL_DESCRIPTION));
        String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_COLOR));
        String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_REMINDER));

//        Note note = new Note();
//        note.setId(id);
//        note.setTitle(title);
//        note.setDescription(description);
//        note.setColor(color);
//        note.setReminder(ifReminder);
        Note note = new Note(id,title,description,color);
        noteList.add(note);
        return noteList;
    }

    public boolean deleteNote(Note note) {
        SQLiteDatabase db = databaseHelper.openDb();
        long res = db.delete(NOTE_TABLE_NAME,
                NOTE_TABLE_COL_ID + " = ? ",
                new String[]{String.valueOf(note.getId())});
        Log.e(TAG, "item deleted");

        return res > 0;
    }

    public boolean updateNote(Note noteToEdit) {
        SQLiteDatabase db = databaseHelper.openDb();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TABLE_COL_ID,noteToEdit.getId());
        contentValues.put(NOTE_TABLE_COL_TITLE, noteToEdit.getTitle());
        contentValues.put(NOTE_TABLE_COL_DESCRIPTION, noteToEdit.getDescription());
        contentValues.put(NOTE_TABLE_COL_COLOR, noteToEdit.getColor());
        contentValues.put(NOTE_TABLE_COL_REMINDER,noteToEdit.getReminder());

//        long res = db.update(NOTE_TABLE_NAME, contentValues,
//                SQLiteDatabaseHelper.NOTE_TABLE_COL_ID + " = ? ",
//                new String[]{noteToEdit.getId() + ""});
        long res = db.update(NOTE_TABLE_NAME, contentValues,
                SQLiteDatabaseHelper.NOTE_TABLE_COL_ID + " = ? ",
                new String[]{String.valueOf(noteToEdit.getId())});

//        long res = db.update(NOTE_TABLE_NAME,contentValues,NOTE_TABLE_COL_ID + " = " + noteToEdit.getId(),null );

        db.close();

        Log.e(TAG, "res is " + res);
        return res > 0;
    }

    public boolean deleteAllNotes() {
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery(" DELETE  FROM " + NOTE_TABLE_NAME + " ; ", null);

        boolean isDeleted = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isDeleted;
    }

    public List<Note> showNotes(int offset) {

//        "SELECT id,name,birthday FROM " + TABLENAME
//                + " WHERE (name LIKE ? OR birthday LIKE ?)"
//                + " LIMIT ?,? " ;
        SQLiteDatabase db = databaseHelper.openDb();
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE_NAME + " ORDER BY " + NOTE_TABLE_COL_ID +
                " ASC " + " LIMIT ?, ?", new String[]{String.valueOf(offset), String.valueOf(10)});
        while (cursor.moveToNext()) {
            noteList = getNoteListFromCursor(cursor, noteList);
        }
        cursor.close();
        return noteList;
    }

    public List<Note> getReminderNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE_NAME +
                " WHERE " + NOTE_TABLE_COL_REMINDER + " != \"\"", null);

//        Note note = null;
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_DESCRIPTION));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_COLOR));
//            boolean isArchived = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
//                    (NOTE_TABLE_COL_ARCHIVE)));
//            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
//                    (NOTE_TABLE_COL_PINNED)));
//            boolean isTrashed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow
//                    (NOTE_TABLE_COL_TRASHED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_REMINDER));
//            note = new Note();
//            note.setId(id);
//            note.setTitle(title);
//            note.setDescription(description);
//            note.setColor(color);
//            note.setReminder(ifReminder);
            Note note = new Note(id,title,description,color);
            noteList.add(note);

            for (Note newNote : noteList) {
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return noteList;
    }

    public List<Note> getArchivedNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.openDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE_NAME +
                " WHERE " + NOTE_TABLE_COL_ARCHIVE + "= 1", null);
//        Note note;
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_DESCRIPTION));
            String color = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_COLOR));
            String isArchived = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_ARCHIVE));
//            boolean isPinned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_PINNED)));
//            boolean isTrashed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_TRASHED)));
            String ifReminder = cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TABLE_COL_REMINDER));
//            note = new Note();
//            note.setId(id);
//            note.setTitle(title);
//            note.setDescription(description);
//            note.setColor(color);
//            note.setReminder(ifReminder);
//            note.setArchive(isArchived);
            Note note = new Note(id,title,description,color);
            noteList.add(note);

            for (Note newNote : noteList) {
                System.out.println(newNote.toString());
            }

        }
        cursor.close();
        return noteList;
    }
//    public Observable<List<Note>> getAllObservableNotes() {
//        return new ObservableNotes(getAllNoteData());
//    }
}
