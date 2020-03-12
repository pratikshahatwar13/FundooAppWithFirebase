package com.bridgelabzs.dashboard.ui.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bridgelabzs.fundooapp.R;

public class PaintFragment extends Fragment {

    private PaintViewModel paintViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paintViewModel =
                ViewModelProviders.of(this).get(PaintViewModel.class);
        View root = inflater.inflate(R.layout.fragment_paint, container, false);
//        final TextView textView = root.findViewById(R.id.text_archive);
//        paintViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
