package com.bridgelabzs.dashboard.ui.mic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bridgelabzs.fundooapp.R;

public class MicFragment extends Fragment {

    private MicViewModel micViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        micViewModel =
                ViewModelProviders.of(this).get(MicViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mic, container, false);
//        final TextView textView = root.findViewById(R.id.text_archive);
//        micViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}