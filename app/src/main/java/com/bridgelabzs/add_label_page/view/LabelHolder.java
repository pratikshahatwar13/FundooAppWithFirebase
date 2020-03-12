package com.bridgelabzs.add_label_page.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_label_page.model.Label;
import com.bridgelabzs.fundooapp.R;

class LabelHolder extends RecyclerView.ViewHolder{

    private LabelAdapter.OnItemClickListener listener;
    private TextView mLabel;
    public LabelHolder(@NonNull View labelItemView, LabelAdapter.OnItemClickListener onItemClickListener) {
        super(labelItemView);
        mLabel = labelItemView.findViewById(R.id.tv_label_item_view);
        this.listener = onItemClickListener;

    }
    public void bindNoteToCard(Label label){
        mLabel.setText(label.getLabelName());

    }
}