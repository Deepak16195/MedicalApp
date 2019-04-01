package com.rdavepatient.soft.meetdoctor.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdavepatient.soft.meetdoctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reminder extends Fragment {


    public static Reminder newInstance() {
        Reminder fragment = new Reminder();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder, container, false);
    }

}
