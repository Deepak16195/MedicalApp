package com.rdavepatient.soft.meetdoctor.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rdavepatient.soft.meetdoctor.R;

public class ReviewActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_activty);
    }
    public void back(View view) {
        super.onBackPressed();
    }
}
