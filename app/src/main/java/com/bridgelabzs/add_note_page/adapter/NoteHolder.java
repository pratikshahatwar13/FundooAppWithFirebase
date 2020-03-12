package com.bridgelabzs.add_note_page.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_note_page.AddNoteActivity;
import com.bridgelabzs.add_note_page.model.Note;
import com.bridgelabzs.fundooapp.R;

import java.util.List;


public class NoteHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NoteHolder";
    private TextView mTitle;
    private TextView mDescription;
    private TextView mReminder;
    private CardView noteCard;
    private CardView reminderCard;
    private ConstraintLayout noteContainerLayout;
    private Context context;
    private List<Note> noteModelArrayList;
    private Note note;

    public NoteHolder(final Context context, @NonNull final View itemView) {
        super(itemView);
       // this.noteModelArrayList = noteModelArrayList;
        this.context = context;
        mTitle = itemView.findViewById(R.id.tv_title);
        mDescription = itemView.findViewById(R.id.tv_description);
        noteCard = itemView.findViewById(R.id.noteItemCard);
        noteContainerLayout = itemView.findViewById(R.id.layout_item_container);
//        reminderCard = itemView.findViewById(R.id.reminderItemCard);
        mReminder = itemView.findViewById(R.id.tv_reminder);
//        noteCard = itemView.findViewById(R.id.noteItemCard);
//

 //       this.listener = onItemClickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
//                if(listener!= null && position != RecyclerView.NO_POSITION) {
//                    listener.onItemClick(noteModelArrayList.get(position));
//                }
//                listener.onItemClick(noteModelArrayList.get(position));
                //Note note = noteModelArrayList.get(position);
                Intent intent = new Intent(context, AddNoteActivity.class);
                intent.putExtra("Id",note.getId());
                intent.putExtra("Title", note.getTitle());
                intent.putExtra("Description",note.getDescription());
                intent.putExtra("Remainder",note.getReminder());
                context.startActivity(intent);
            }
        });

//        itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ArchiveFragment archiveFragment = new ArchiveFragment();
//                FragmentManager fragmentManager=((Activity)context).getFragmentManager();
//                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.nav_archive,archiveFragment,"tag");
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                Intent intent = new Intent(context, ArchiveFragment.class);
//                context.startActivity(intent);
//                return true;
//            }
//        });
    }

    public void bindNoteToCard(Note note) {
        this.note = note;
        mTitle.setText(note.getTitle());
        mDescription.setText(note.getDescription());
        mReminder.setText(note.getReminder());

        if (note.getColor() != null && !note.getColor().isEmpty()) {
            noteCard.setCardBackgroundColor(Color.parseColor(note.getColor()));
        } else {
            noteCard.setCardBackgroundColor(Color.WHITE);
        }
        if(note.getReminder() != null){
            mReminder.setText(note.getReminder());
        }else{
            mReminder.setText(" ");
        }
    }

//    private void setReminderView(Note note) {
//       if(!note.getReminder().isEmpty()){
//            mReminder.setVisibility(View.GONE);
//       }
//        else{
//            mReminder.setText(note.getReminder().get(0));
//            mReminder.setBackgroundColor(Color.parseColor(note.getColor()));
//      }
//  }
}