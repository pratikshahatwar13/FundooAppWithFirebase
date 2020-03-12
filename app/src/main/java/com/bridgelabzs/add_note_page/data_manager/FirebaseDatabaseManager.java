package com.bridgelabzs.add_note_page.data_manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;


import com.bridgelabzs.add_note_page.model.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseManager implements DatabaseManager{
    private DatabaseReference mDatabase;
    List<Note> noteList;

    public FirebaseDatabaseManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean addNote(Note note) {
        String noteId = mDatabase.push().getKey();
        mDatabase.child("Notes").child(noteId).setValue(note);
        return true;
    }

    @Override
    public List<Note> getAllNoteData() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");
        mDatabase.keepSynced(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Note> noteList = new ArrayList<>();
                if (dataSnapshot.exists()){
                    Log.e("value", String.valueOf(noteList));
                    for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()){
                        String notekey = noteSnapshot.getKey();
                        Note note=noteSnapshot.getValue(Note.class);
                        noteList.add(note);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return noteList;
    }

    @Override
    public boolean deleteNote(Note note) {
        return false;
    }

    @Override
    public boolean updateNote(Note noteToEdit) {
        return false;
    }

    @Override
    public ContentValues getContentValuesFromNote(Note note) {
        return null;
    }

    @Override
    public List<Note> getNoteListFromCursor(Cursor cursor, List<Note> noteList) {
        return null;
    }

    @Override
    public List<Note> showNotes(int offset) {
        return null;
    }

    public void readDataFromFirebase(final FirebaseCallback firebaseCallback){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");
        mDatabase.keepSynced(true);


        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Note> noteList = new ArrayList<>();
                Log.d("data", "onDataChange: data fetched");
                if (dataSnapshot.exists()){

                    Log.e("value", String.valueOf(noteList));
                    for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()){
                        String notekey = noteSnapshot.getKey();
                        Note note=noteSnapshot.getValue(Note.class);
                        Log.e("data", "onDataChange: note data not found");
                        noteList.add(note);
                    }
                    Log.d("data", "onDataChange: ");
                    firebaseCallback.onCallback(noteList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


