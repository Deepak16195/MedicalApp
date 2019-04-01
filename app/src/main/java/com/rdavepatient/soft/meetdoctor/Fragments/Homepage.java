package com.rdavepatient.soft.meetdoctor.Fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rdavepatient.soft.meetdoctor.Activity.Get_Addressactivity;
import com.rdavepatient.soft.meetdoctor.Activity.Login_page;
import com.rdavepatient.soft.meetdoctor.Activity.SelectCatgaryforDocter;
import com.rdavepatient.soft.meetdoctor.Adapter.AlcatAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Models.DocterSpecialisation;
import com.rdavepatient.soft.meetdoctor.Models.DocterType;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.banner.BannerView;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class Homepage extends Fragment implements
        AdapterView.OnItemSelectedListener {

    ViewPager mViewPager;
    @BindView(R.id.Doctors)
    CardView Cv_Doctors;
    @BindView(R.id.Dentist)
    CardView Cv_Dentist;
    @BindView(R.id.Physiotherapist)
    CardView Cv_Physiotherapist;
    @BindView(R.id.Homeopathic)
    CardView Cv_Homeopathic;
    Unbinder unbinder;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textViewName2)
    TextView textViewName2;
    @BindView(R.id.logout)
    ImageButton mlogout;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.Location)
    LinearLayout Location;
    @BindView(R.id.banner)
    BannerView banner;
    @BindView(R.id.viewpager_banner)
    ViewPager viewpagerBanner;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textViewName)
    TextView textViewName;
    public static TextView AreaName;
    private BannerView mBannerView;
    String[] country = {"Mumbai", "Goa", "Delhi", "Japan", "Other"};
    Intent i;
    APIService mApiService;
    List<DocterType.DocterTypeEntity> mDocterType;
    List<String> DocterTypes = new ArrayList<String>();
    List<String> DocterIcon = new ArrayList<String>();
    RecyclerView SearchList;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    AlcatAdapter RecyclerViewHorizontalAdapter;
    public  String  Area_name="";
    SearchView Searchdata;
    EditText listingSearchEt;

    public static Homepage newInstance() {
        Homepage fragment = new Homepage();
        return fragment;
    }

    public Homepage() {
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
//        initViews(view);
//        return view;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        unbinder = ButterKnife.bind(this, view);
        Spinner spin = view.findViewById(R.id.spinner);
        AreaName = view.findViewById(R.id.Area_name);
        listingSearchEt = view.findViewById(R.id.listingSearchEt);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.spinner_itemc, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        SearchList = view.findViewById(R.id.Searchlist);
//        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        SearchList.setLayoutManager(HorizontalLayout);
        HorizontalLayout = new GridLayoutManager(getContext(), 2);
        SearchList.setLayoutManager(HorizontalLayout);


        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getActivity(), Get_Addressactivity.class);
                startActivity(i);
            }
        });

        mApiService = ApiUtils.getAPIService();


        GetDocterCatgery();




        listingSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String filter=listingSearchEt.getText().toString().trim();
                RecyclerViewHorizontalAdapter.getFilter().filter(filter);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }


    private void initViews(View parentView) {
        List<BannerView.BannerItem> bannerItems = new ArrayList<>();
        BannerView.BannerItem item1 = new BannerView.BannerItem("First Step", "https://img3.doubanio.com/view/photo/photo/public/p2327027746.jpg");
        BannerView.BannerItem item2 = new BannerView.BannerItem("second Step", "https://img3.doubanio.com/view/photo/photo/public/p2327027694.jpg");
        bannerItems.add(item1);
        bannerItems.add(item2);
        bannerItems.add(item1);
        bannerItems.add(item2);
        BannerView bannerView = (BannerView) parentView.findViewById(R.id.banner);
        bannerView.setTitleEnabled(true);
        bannerView.setBannerItems(bannerItems);
        bannerView.startLoop();
        bannerView.setOnItemClickListener(new BannerView.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "You click " + String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * Custom Your Image Loader
         */
        bannerView.setImageLoadder(new BannerView.ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Glide.with(imageView.getContext())
                        .load(url)
                        .placeholder(R.drawable.photo_holder_72dp)
                        .dontAnimate()
                        .dontTransform()
                        .into(imageView);
            }
        });
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Toast.makeText(getContext(), country[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDestroyView() {

        if (mBannerView != null) {
            mBannerView.stopLoop();
        }
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {

        if (mBannerView != null) {
            mBannerView.startLoop();
        }

        String daat =  SharePrefarence.getmInstance(getContext()).getAREANAME();
        AreaName.setText(daat);
        super.onResume();
    }

    @OnClick({R.id.Doctors, R.id.Dentist, R.id.Physiotherapist, R.id.Homeopathic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Doctors:
                i = new Intent(getActivity(), SelectCatgaryforDocter.class);
                startActivity(i);
                break;
            case R.id.Dentist:
                i = new Intent(getActivity(), SelectCatgaryforDocter.class);
                startActivity(i);
                break;
            case R.id.Physiotherapist:
                i = new Intent(getActivity(), SelectCatgaryforDocter.class);
                startActivity(i);
                break;
            case R.id.Homeopathic:
                i = new Intent(getActivity(), SelectCatgaryforDocter.class);
                startActivity(i);
                break;
        }
    }

    @OnClick(R.id.logout)
    public void onViewClicked() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        SharePrefarence.getmInstance(getContext()).logout();
                        getActivity().moveTaskToBack(true);
                        getActivity().finish();
                        Intent intent = new Intent(getContext(), Login_page.class);
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure to Logout?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }


    private void GetDocterCatgery() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.GetDoctertype().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DocterType>() {
                    @Override
                    public void onCompleted() {
                        loading.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.dismiss();
                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
                    }

                    @Override
                    public void onNext(DocterType data) {
                        if (data.getStatus() == 101) {
                        }
                        if (data.getStatus() == 200) {
                            mDocterType = data.getDocterType();
                          /*  for (int x = 1; x < mDocterType.size(); x++) {
                                DocterTypes.add(mDocterType.get(x).getSpecialisationName());
                                DocterIcon.add(mDocterType.get(x).getIconPath());
                            }*/

                            RecyclerViewHorizontalAdapter = new AlcatAdapter(getActivity(),mDocterType);
                            SearchList.setAdapter(RecyclerViewHorizontalAdapter);


                        }
                    }
                });
    }
}
