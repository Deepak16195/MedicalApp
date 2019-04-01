package com.rdavepatient.soft.meetdoctor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Fragments.Homepage;
import com.rdavepatient.soft.meetdoctor.Pager.AllDocterPager;
import com.rdavepatient.soft.meetdoctor.R;

public class All_Doctors extends AppCompatActivity implements TabLayout.OnTabSelectedListener, AdapterView.OnItemSelectedListener {


    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;
    String[] country = {"Delhi", "Japan", "Other"};
    TextView Area_name;
    LinearLayout Location;
    public static int DocterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__doctors);
        Area_name = (TextView) findViewById(R.id.Area_name);
        Location = (LinearLayout) findViewById(R.id.Location);
        Area_name.setText(SharePrefarence.getmInstance(getApplicationContext()).getAREANAME());
        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Get_Addressactivity.class);
                startActivity(i);
            }
        });


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("My Appointments"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        AllDocterPager adapter = new AllDocterPager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0f, true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onRestart() {
        Area_name.setText(SharePrefarence.getmInstance(getApplicationContext()).getAREANAME());
        super.onRestart();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public void back(View view) {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
