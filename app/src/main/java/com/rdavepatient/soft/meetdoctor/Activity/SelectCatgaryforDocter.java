package com.rdavepatient.soft.meetdoctor.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.rdavepatient.soft.meetdoctor.Adapter.AlcatAdapter;
import com.rdavepatient.soft.meetdoctor.Models.DocterType;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SelectCatgaryforDocter extends AppCompatActivity {
    APIService mApiService;
    List<DocterType.DocterTypeEntity> mDocterType;
    List<String> DocterTypes = new ArrayList<String>();
    RecyclerView SearchList;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    AlcatAdapter RecyclerViewHorizontalAdapter;
    SearchView SearchCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_catgaryfor_docter);
        mApiService = ApiUtils.getAPIService();
        SearchList = findViewById(R.id.Searchlist);
        SearchCat = findViewById(R.id.SearchCat);
        SearchCat.setIconifiedByDefault(false);
        SearchCat.setIconified(false);
        initRecyclerView();
        //GetDocterCatgery();

        SearchCat.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showInputMethod(view.findFocus());
                }
            }
        });



        SearchCat.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    RecyclerViewHorizontalAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                    RecyclerViewHorizontalAdapter.getFilter().filter(query);
                return false;
            }
        });






    }

    public void back(View view) {
        super.onBackPressed();
    }

    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }


    public void initRecyclerView() {
        // Adding items to RecyclerView.
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        SearchList.setLayoutManager(HorizontalLayout);

    }




//    private void GetDocterCatgery() {
//        final ProgressDialog loading = ProgressDialog.show(SelectCatgaryforDocter.this, "Loading", "Please wait...", false, false);
//        loading.setCancelable(true);
//        mApiService.GetDoctertype().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<DocterType>() {
//                    @Override
//                    public void onCompleted() {
//                        loading.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        loading.dismiss();
//                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
//                    }
//
//                    @Override
//                    public void onNext(DocterType data) {
//                        if (data.getStatus() == 101) {
//                        }
//                        if (data.getStatus() == 200) {
//                            mDocterType = data.getDocterType();
//                            for (int x = 0; x < mDocterType.size(); x++) {
//                                DocterTypes.add(mDocterType.get(x).getDoctertypeName());
//                            }
//
//                            RecyclerViewHorizontalAdapter = new AlcatAdapter(DocterTypes);
//                            SearchList.setAdapter(RecyclerViewHorizontalAdapter);
//
//
//                        }
//                    }
//                });
//    }
}
