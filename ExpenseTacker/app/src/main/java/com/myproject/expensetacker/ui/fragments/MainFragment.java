package com.myproject.expensetacker.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myproject.expensetacker.R;

public class MainFragment extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textView = view.findViewById(R.id.text_view);

        // Get Title
        String sTitle = getArguments().getString("title");

        // Set title on text view
        textView.setText(sTitle);
        return view;
    }
}