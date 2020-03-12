package com.bridgelabzs.dashboard.ui.notes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_note_page.adapter.NoteHolder;
import com.bridgelabzs.add_note_page.adapter.NotesAdapter;
import com.bridgelabzs.add_note_page.data_manager.FirebaseCallback;
import com.bridgelabzs.add_note_page.data_manager.FirebaseDatabaseManager;
import com.bridgelabzs.add_note_page.model.Note;
import com.bridgelabzs.add_note_page.view_model.NoteViewModel;
import com.bridgelabzs.fundooapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NotesFragment extends Fragment {

    private static final String TAG = "NotesFragment";
    private List<Note> noteList = new ArrayList<>();
    private Note note;
    Boolean isListView = false;
    private NoteViewModel noteViewModel;
    NotesAdapter notesAdapter;
    RecyclerView mRecyclerView;
//    RecyclerView.LayoutManager manager;
    RecyclerView.LayoutManager manager;
    //private GridLayoutManager gridLayoutManager;
    private CheckBox listGridView;
    Note noteToDelete;
    Note noteToUpdate;
    Boolean isScrolling = false;
    private int offset = 0;
    private int LIMIT = 10;
    List<Note> localNotes;
    ProgressBar progressBar;
    int currentItems, totalItems, scrollOutItems;
    private BroadcastReceiver noteBroadcastReceiver;
    private BroadcastReceiver searchBroadcastReceiver;
    private BroadcastReceiver firebaseBroadcastReceiver;

//    private FirebaseRecyclerOptions<Note> options;
//    private FirebaseRecyclerAdapter<Note, NoteHolder> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        progressBar = root.findViewById(R.id.progress_bar);
        mRecyclerView = root.findViewById(R.id.recycleView);

//        noteViewModel = new NoteViewModel(getActivity());
        noteViewModel = new NoteViewModel();
        mRecyclerView.setAdapter(notesAdapter);
        noteList = new ArrayList<Note>();
        prepareRecyclerView();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        noteBroadcastReceiver = new NotesBroadcastReceiver();
        IntentFilter noteIntentFilter = new IntentFilter("com.bridgelabz.TEST");
        getActivity().registerReceiver(noteBroadcastReceiver,noteIntentFilter);

        searchBroadcastReceiver = new SearchNotesReceiver();
        IntentFilter searchIntentFilter = new IntentFilter("com.bridgelabz.search");
        getActivity().registerReceiver(searchBroadcastReceiver,searchIntentFilter);

        firebaseBroadcastReceiver = new FirebaseBroadcastReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(noteBroadcastReceiver);
        getActivity().unregisterReceiver(searchBroadcastReceiver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListGridView();
    }

    private void prepareRecyclerView() {
        notesAdapter = new NotesAdapter(getContext(),noteList);
        showList();

        mRecyclerView.setAdapter(notesAdapter);
        noteItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }
////            @Override
////            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
////                super.onScrolled(recyclerView, dx, dy);
////                if(recyclerView.getLayoutManager() instanceof LinearLayoutManager)
////                {
////                    LinearLayoutManager manager1 = (LinearLayoutManager)recyclerView.getLayoutManager();
////                    manager1.findFirstVisibleItemPosition();
////                }
////                currentItems = manager.getChildCount();
////                totalItems = manager.getItemCount();
////                scrollOutItems = manager.findFirstVisibleItemPosition();
////
////                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
////                    //fetch data.......
////                    isScrolling = false;
////                    fetchData();
////                }
////            }
       });
    }

    private void fetchData() {
//        Toast.makeText(this, "fetch data", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                offset = offset + LIMIT;
                List<Note> noteList1 = noteViewModel.showPagerNotes(offset);
//                localNotes.addAll(noteList1);
//                notesAdapter.setNoteModelArrayList(noteList1);
//                notesAdapter.notifyItemRangeInserted(localNotes.size(), noteList1.size() - 1);
//                notesAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        }, 3000);
    }


    public void setListGridView() {
        isListView =!isListView;
        if (isListView){
            manager = new GridLayoutManager(getContext(),2);
            mRecyclerView.setLayoutManager(manager);
        }
        else{
            manager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(manager);
        }
    }

    private ItemTouchHelper noteItemTouchHelper = new ItemTouchHelper
            (new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                        draggedViewHolder, @NonNull RecyclerView.ViewHolder targetViewHolder) {
                    int draggedPosition = draggedViewHolder.getAdapterPosition();
                    int targetPosition = targetViewHolder.getAdapterPosition();

                    notesAdapter.onItemMove(draggedPosition, targetPosition);
                    Collections.swap(noteList, draggedPosition, targetPosition);
                    Log.e(TAG, "dragged and moved");
                    return true;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder swipedViewHolder, int direction) {
                    noteViewModel.deleteNote(notesAdapter.getNoteAt(swipedViewHolder.getAdapterPosition()));
                    Toast.makeText(getActivity(), "Item Deleted",
                            Toast.LENGTH_SHORT).show();

                    final int adapterPosition = swipedViewHolder.getAdapterPosition();
                    Log.e(TAG, "The adapter position is " + adapterPosition);
                    noteToDelete = notesAdapter.getNoteAt(adapterPosition);

                    notesAdapter.onItemSwiped(adapterPosition);
                    notesAdapter.notifyItemRemoved(adapterPosition);
                    Toast.makeText(getActivity(), "Item Deleted", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
                    super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                }
            });


    private boolean deleteNote(Note noteAt) {

        return true;          // TODO: change
    }

//    private void showList(){
//        noteList = noteViewModel.getAllNoteData();
//        notesAdapter.setNoteModelArrayList(noteList);
//        notesAdapter.notifyDataSetChanged();
//    }

    private void showList(){
        FirebaseDatabaseManager firebaseDatabaseManager = new FirebaseDatabaseManager();
        firebaseDatabaseManager.readDataFromFirebase(new FirebaseCallback() {
            @Override
            public void onCallback(List<Note> noteList1) {
                Log.e("return value", String.valueOf(noteList1));
                notesAdapter.setNoteModelArrayList(noteList1);
                notesAdapter.notifyDataSetChanged();
            }
        });

    }


    public void filter(String text){
        noteList = noteViewModel.getAllNoteData();
        List<Note> filteredList = new ArrayList<>();
        for(Note note : noteList){
            if(note.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(note);
            }
        }
        notesAdapter.filterList(filteredList);
    }

     public class NotesBroadcastReceiver extends BroadcastReceiver{

         @Override
         public void onReceive(Context context, Intent intent) {
             Log.e(TAG, "Local Broadcast is working for  List and grid view");
             setListGridView();
         }
     }

     public class SearchNotesReceiver extends BroadcastReceiver{

         @Override
         public void onReceive(Context context, Intent intent) {
             filter(intent.getStringExtra("search"));
         }
     }

     public class FirebaseBroadcastReceiver extends BroadcastReceiver{

         @Override
         public void onReceive(Context context, Intent intent) {

         }
     }

}