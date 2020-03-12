package com.bridgelabzs.add_note_page.data_manager;

import com.bridgelabzs.add_note_page.model.Note;

import java.util.List;

public interface FirebaseCallback {
    void onCallback(List<Note> noteList);

}
