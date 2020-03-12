package com.bridgelabzs.add_note_page.data_manager;

import android.content.ContentValues;
import android.database.Cursor;

import com.bridgelabzs.add_note_page.model.Note;

import java.util.List;

public interface DatabaseManager {
     boolean addNote(Note note);
     List<Note> getAllNoteData();
     boolean deleteNote(Note note);
     boolean updateNote(Note noteToEdit);
     ContentValues getContentValuesFromNote(Note note);
     List<Note> getNoteListFromCursor(Cursor cursor, List<Note> noteList);
     List<Note> showNotes(int offset);
}
