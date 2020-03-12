package com.bridgelabzs.add_note_page.view_model;

import android.content.Context;
import android.database.Observable;

import com.bridgelabzs.add_note_page.data_manager.DatabaseManager;
import com.bridgelabzs.add_note_page.data_manager.FirebaseDatabaseManager;
import com.bridgelabzs.add_note_page.data_manager.NoteDatabaseManager;
import com.bridgelabzs.add_note_page.model.Note;

import java.util.List;

public class NoteViewModel
{
    String title;
    String description;

    private Observable<List<Note>> observableNotes;
//    private NoteDatabaseManager noteDbManager;
    private DatabaseManager noteDbManager;

//    public NoteViewModel(Context context){
//        noteDbManager = new NoteDatabaseManager(context);
//    }
    public NoteViewModel(){
        noteDbManager = new FirebaseDatabaseManager();
    }

    public NoteViewModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public  boolean addNote(Note note){
        return noteDbManager.addNote(note);
    }

//    public boolean addNoteIfReminder(Note note){
//        return noteDbManager.addNoteIfReminder(note);
//    }
//
//    public boolean addAndUpdateNote(Note note){
//        return noteDbManager.addAndUpdateNote(note);
//    }

    public List<Note> getAllNoteData() {
        return noteDbManager.getAllNoteData();
    }


    public boolean deleteNote(Note note)
    {
        return  noteDbManager.deleteNote(note);
    }

//    public boolean deleteAllNotes() {
//        return noteDbManager.deleteAllNotes();
//    }
    public boolean updateNote(Note noteToEdit){
        return noteDbManager.updateNote(noteToEdit);
    }

//    public List<Note> getArchivedNotes(){
//        return noteDbManager.getArchivedNotes();
//    }
//    public List<Note> getReminderNotes(){
//        return noteDbManager.getReminderNotes();
//    }

    public List<Note> showPagerNotes(int offset){
        return noteDbManager.showNotes(offset);
    }

//    public boolean addArchiveNote(Note note){
//        return noteDbManager.addArchieNote(note);
//    }
}