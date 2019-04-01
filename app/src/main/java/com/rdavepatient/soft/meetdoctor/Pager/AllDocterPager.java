package com.rdavepatient.soft.meetdoctor.Pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rdavepatient.soft.meetdoctor.Fragments.DocterList;
import com.rdavepatient.soft.meetdoctor.Fragments.Oppiments;

public class AllDocterPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public AllDocterPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                DocterList tab1 = new DocterList();
                return tab1;
            case 1:
                Oppiments tab2 = new Oppiments();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }}
