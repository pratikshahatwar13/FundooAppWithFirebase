package com.bridgelabzs.add_note_page.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_note_page.model.Note;
import com.bridgelabzs.common.ItemTouchHelperAdapter;
import com.bridgelabzs.fundooapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> implements ItemTouchHelperAdapter {
    private List<Note> noteList ;
    private List<Note> noteModelArrayList ;
    private List<Note> noteModelArrayListFull;
    private Note note;

//    private OnItemClickListener listener;

    private ItemTouchHelper itemTouchHelper;
    private Context context;

    public NotesAdapter(Context context, List<Note> noteModelArrayList) {
        this.noteModelArrayList = noteModelArrayList;
        this.note = note;
       // this.listener = onItemClickListener;
        this.context = context;
        noteModelArrayListFull = new ArrayList<>(noteModelArrayList);
    }
    @Override
    public NoteHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemview, viewGroup,
                false);
        return new NoteHolder(context,view);
    }

    @Override
    public void onBindViewHolder(NoteHolder noteHolder, int position) {
        Note note = noteModelArrayList.get(position);
        noteHolder.bindNoteToCard(note);
    }

    @Override
    public int getItemCount() {
        if(noteModelArrayList != null){
            return noteModelArrayList.size();
        }

        return 0;
    }

    public Note getNoteAt(int position){
        return noteModelArrayList.get(position);
    }

    @Override
    public void onItemMove(int draggedPosition, int targetPosition) {
        Note draggedNote = noteModelArrayList.get(draggedPosition);
        noteModelArrayList.remove(draggedNote);
        noteModelArrayList.add(targetPosition, draggedNote);
        notifyItemMoved(draggedPosition, targetPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        noteModelArrayList.remove(position);
        notifyItemRemoved(position);

    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.itemTouchHelper = touchHelper;
    }
    public void setNoteModelArrayList(List noteModelArrayList){
        this.noteModelArrayList = noteModelArrayList;
    }

//    public interface OnItemClickListener{
//        void onItemClick(Note note);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.listener = listener;
//
//    }

    public void filterList(List<Note> filteredList){
        noteModelArrayList = filteredList;
        notifyDataSetChanged();
    }
}