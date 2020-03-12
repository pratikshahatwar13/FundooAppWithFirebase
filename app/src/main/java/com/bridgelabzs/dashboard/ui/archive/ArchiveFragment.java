package com.bridgelabzs.dashboard.ui.archive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_note_page.adapter.NotesAdapter;
import com.bridgelabzs.add_note_page.model.Note;
import com.bridgelabzs.add_note_page.view_model.NoteViewModel;
import com.bridgelabzs.fundooapp.R;

import java.util.ArrayList;
import java.util.List;

public class ArchiveFragment extends Fragment {

    private List<Note> noteList = new ArrayList<>();
    private NoteViewModel noteViewModel;
    NotesAdapter notesAdapter;
    RecyclerView mRecyclerView;
    Boolean isScrolling = false;
    RecyclerView.LayoutManager manager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_archive, container, false);
//        progressBar = root.findViewById(R.id.progress_bar);
        mRecyclerView = root.findViewById(R.id.archiveRecycleView);

//        noteViewModel = new NoteViewModel(getActivity());
        noteViewModel = new NoteViewModel();
        mRecyclerView.setAdapter(notesAdapter);
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        prepareRecyclerView();
        return root;
    }

    private void prepareRecyclerView() {
        notesAdapter = new NotesAdapter(getContext(),noteList);
        showList();

        mRecyclerView.setAdapter(notesAdapter);

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

    private void showList(){
//        noteList = noteViewModel.getArchivedNotes();
        notesAdapter.setNoteModelArrayList(noteList);
        notesAdapter.notifyDataSetChanged();
    }
}