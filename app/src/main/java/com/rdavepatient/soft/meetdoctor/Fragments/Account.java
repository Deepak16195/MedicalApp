package com.rdavepatient.soft.meetdoctor.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdavepatient.soft.meetdoctor.Activity.AppoitemntsActivty;
import com.rdavepatient.soft.meetdoctor.Activity.FamilymemberList;
import com.rdavepatient.soft.meetdoctor.Activity.Login_page;
import com.rdavepatient.soft.meetdoctor.Activity.ProfileSetting;
import com.rdavepatient.soft.meetdoctor.Activity.ReviewActivty;
import com.rdavepatient.soft.meetdoctor.Activity.UplodeReportsActivty;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment {


    @BindView(R.id.Profile)
    LinearLayout Profile;
    @BindView(R.id.Familymember)
    LinearLayout Familymember;
    @BindView(R.id.Appoitemnts)
    LinearLayout Appoitemnts;
    @BindView(R.id.Review)
    LinearLayout Review;
    @BindView(R.id.UplodeReports)
    LinearLayout UplodeReports;
    @BindView(R.id.AboutUs)
    LinearLayout AboutUs;
    @BindView(R.id.Termcandation)
    LinearLayout Termcandation;
    @BindView(R.id.Privacy)
    LinearLayout Privacy;
    @BindView(R.id.Faq)
    LinearLayout Faq;
    Unbinder unbinder;
    Intent i;
    @BindView(R.id.Logout)
    LinearLayout Logout;

    public Account() {
        // Required empty public constructor
    }

    public static Account newInstance() {
        Account fragment = new Account();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Profile, R.id.Familymember, R.id.Appoitemnts, R.id.Review, R.id.UplodeReports, R.id.AboutUs, R.id.Termcandation, R.id.Privacy, R.id.Faq,R.id.Logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Profile:
                i = new Intent(getContext(), ProfileSetting.class);
                startActivity(i);
                break;
            case R.id.Familymember:
                i = new Intent(getContext(), FamilymemberList.class);
                startActivity(i);
                break;
            case R.id.Appoitemnts:
                i = new Intent(getContext(), AppoitemntsActivty.class);
                startActivity(i);
                break;
            case R.id.Review:
                i = new Intent(getContext(), ReviewActivty.class);
                startActivity(i);
                break;
            case R.id.UplodeReports:
                i = new Intent(getContext(), UplodeReportsActivty.class);
                startActivity(i);
                break;
            case R.id.AboutUs:
                break;
            case R.id.Termcandation:
                break;
            case R.id.Privacy:
                break;
            case R.id.Faq:
                break;
            case R.id.Logout:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                SharePrefarence.getmInstance(getContext()).logout();
                                getActivity().moveTaskToBack(true);
                                getActivity().finish();
                                Intent intent  = new Intent(getContext(),Login_page.class);
                                startActivity(intent);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure to Logout?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


                break;


        }
    }
}
