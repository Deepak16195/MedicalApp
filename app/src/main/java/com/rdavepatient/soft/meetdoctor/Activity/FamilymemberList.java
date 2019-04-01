package com.rdavepatient.soft.meetdoctor.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.rdavepatient.soft.meetdoctor.Adapter.familyListAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.MainActivity;
import com.rdavepatient.soft.meetdoctor.Models.Famelimember;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FamilymemberList extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.Add)
    FloatingActionButton Add;
    Intent i;
    APIService mApiService;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    int RecyclerViewItemPosition;
    familyListAdapter RecyclerViewHorizontalAdapter;
    List<Famelimember.FamelyEntity> mFamilyList = new ArrayList<>();
    int Userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_familymember2);
        ButterKnife.bind(this);
        mApiService = ApiUtils.getAPIService();
        initView();
        Userid = SharePrefarence.getmInstance(FamilymemberList.this).getUserId();
        GetGamilyList(Userid);


    }

    @Override
    public void onBackPressed() {

        i = new Intent(getApplication(), MainActivity.class);
        startActivity(i);

        super.onBackPressed();
    }

    public void initRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplication());
        recyclerView2.setLayoutManager(RecyclerViewLayoutManager);
        // Adding items to RecyclerView.

        RecyclerViewHorizontalAdapter = new familyListAdapter(FamilymemberList.this, mFamilyList);
        HorizontalLayout = new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(HorizontalLayout);
        recyclerView2.setAdapter(RecyclerViewHorizontalAdapter);
    }


    private void GetGamilyList(int data) {
        final ProgressDialog loading = ProgressDialog.show(FamilymemberList.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.Getfamelymeber(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Famelimember>() {
                    @Override
                    public void onCompleted() {
                        loading.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.dismiss();
                        Log.e("retro_error", e.toString());
                    }

                    @Override
                    public void onNext(Famelimember data) {
                        loading.dismiss();
                        if (data.getStatus() == 101) {
                            Toasty.normal(getApplication(), "Email Id Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 102) {
                            Toasty.normal(getApplication(), "Mobile No Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 200) {
                            if (data.getFamely().size() > 0) {
                                mFamilyList = data.getFamely();
                            }
                            initRecyclerView();
                        }
                    }
                });


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Add) {
            i = new Intent(getApplication(), AddFamilymemberActivty.class);
            startActivity(i);

        }
    }

    private void initView() {
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        Add = (FloatingActionButton) findViewById(R.id.Add);
        Add.setOnClickListener(FamilymemberList.this);
    }

    public void back(View view) {
        super.onBackPressed();
    }
}
