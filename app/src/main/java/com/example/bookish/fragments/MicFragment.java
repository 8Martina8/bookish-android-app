package com.example.bookish.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookish.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MicFragment extends DialogFragment {
    public MicFragment() {
        // Required empty public constructor
    }

    public static MicFragment newInstance() {
        return new MicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mic, container, false);
    }
}