package com.bridgelabzs.dashboard.ui.create_label;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_label_page.model.Label;
import com.bridgelabzs.add_label_page.view.LabelAdapter;
import com.bridgelabzs.add_label_page.view_model.LabelViewModel;
import com.bridgelabzs.fundooapp.R;

import java.util.List;

public class AddLabelFragment extends Fragment {

    private static final String TAG = "AddLabelFragment";
    private EditText mCreateLabel;
    private ImageButton mAdd;
    private ImageButton mCancel;
    LabelViewModel labelViewModel;
    LabelAdapter labelAdapter;
    RecyclerView mRecyclerView;
    private List<Label> labelList;

    public AddLabelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_label, container, false);
        labelViewModel = new LabelViewModel(getContext());

        // Inflate the layout for this fragment
        return root;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        findViews();
        initRecyclerView();
        setOnClickAdd();
        setOnClickDelete();
    }

    private void initRecyclerView() {
        labelList = labelViewModel.getAllLabelData();

        for (Label label : labelList) {
            System.out.println(label);
        }

        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        labelAdapter = new LabelAdapter(labelList, new LabelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int labelPosition) {


            }
        });
        mRecyclerView.setAdapter(labelAdapter);

    }

    private void setOnClickAdd() {

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "add clicked", Toast.LENGTH_SHORT).show();

                String labelName = mCreateLabel.getText().toString().trim();

                if(labelName.isEmpty()){
                    Toast.makeText(getContext(), "Enter label name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Label label = new Label(labelName);
                    labelAddToDb(label);
                    labelList.add(label);
                    labelAdapter.notifyDataSetChanged();
                    mCreateLabel.getText().clear();

                }

            }
        });
    }

    private void labelAddToDb(Label label) {
        boolean isLabelAdd = labelViewModel.addLabel(label);
        if(isLabelAdd){
            Toast.makeText(getContext(), "Label successfully added", Toast.LENGTH_SHORT).show();
            Log.e(TAG, label.toString());
        }
    }
    private void setOnClickDelete() {
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.imgBtn_cancel:
                        labelViewModel.deleteAllLabels();
                        labelList.clear();
                        labelAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "All labels deleted ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findViews() {

        mCreateLabel = getView().findViewById(R.id.et_labelName);
        mAdd = getView().findViewById(R.id.imgBtn_Add);
        mCancel = getView().findViewById(R.id.imgBtn_cancel);
    }
}