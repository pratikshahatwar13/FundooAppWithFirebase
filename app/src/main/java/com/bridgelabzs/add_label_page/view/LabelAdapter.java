package com.bridgelabzs.add_label_page.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_label_page.model.Label;
import com.bridgelabzs.fundooapp.R;

import java.util.List;

public class LabelAdapter extends RecyclerView.Adapter<LabelHolder>
{
    private List<Label> labelModelArrayList;
    private OnItemClickListener listener;
    public LabelAdapter(List<Label> labelModelArrayList, OnItemClickListener onItemClickListener){
        this.labelModelArrayList = labelModelArrayList;
        this.listener = onItemClickListener;
    }
    @Override
    public LabelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.label_itemview, viewGroup,
                false);
        return new LabelHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelHolder labelHolder, int position) {
        Label label = labelModelArrayList.get(position);
        labelHolder.bindNoteToCard(label);
    }

    @Override
    public int getItemCount() {
        return labelModelArrayList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(int labelPosition);
    }
}
