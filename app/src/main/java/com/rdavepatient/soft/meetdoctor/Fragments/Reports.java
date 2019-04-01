package com.rdavepatient.soft.meetdoctor.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rdavepatient.soft.meetdoctor.Adapter.All_Photo_Adapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reports extends Fragment {


    private ViewGroup mSelectedImagesContainer;
    public static final int Request_Permission = 10;
    public static final int REQUEST_CODE_CHOOSE = 5;
    LinearLayout Capture;
    ArrayList<Uri> image_uris = new ArrayList<Uri>();
    ArrayList<String> image_urisPath = new ArrayList<>();
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    Button SubmitImage;
    TextView backText;
    private static final String TAG = "TedPicker";
    List<MultipartBody.Part> fileToUpload = new ArrayList<>();
    APIService mApiService;
    List<Uri> mSelected = new ArrayList<Uri>();
    public RecyclerView rv_photo;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    int UserID;
    LinearLayout HideDefult;
    //List<DocterReorts> items = new ArrayList<>();
    List<String> ReportsList = new ArrayList<String>();
    All_Photo_Adapter RecyclerViewHorizontalAdapter;


    public Reports() {
        // Required empty public constructor
    }
    public static Reports newInstance() {
        Reports fragment = new Reports();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        // Inflate the layout for this fragment
        mSelectedImagesContainer = view.findViewById(R.id.selected_photos_container);
        Capture = view.findViewById(R.id.lin_capture);
        HideDefult = view.findViewById(R.id.HideDefult);
        SubmitImage = view.findViewById(R.id.SubmitImage);
        rv_photo = view.findViewById(R.id.rv_photo);
        mApiService = ApiUtils.getAPIService();
        UserID = SharePrefarence.getmInstance(getContext()).getUserId();
//        checkPermissionNew();
        initRecyclerView();
       // GetClinctImage(UserID);

        return view;
    }

    public void initRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        rv_photo.setLayoutManager(RecyclerViewLayoutManager);
        // Adding items to RecyclerView.
        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_photo.setLayoutManager(HorizontalLayout);
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

        Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelected.clear();
                image_urisPath.clear();
                image_uris.clear();
                fileToUpload.clear();
                Matisse.from(getActivity())
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(5)
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.bottom_height))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);

//                Config config = new Config();
//                config.setCameraHeight(R.dimen.app_camera_height);
//                config.setToolbarTitleRes(R.string.app_name);
//                config.setSelectionMin(1);
//                config.setSelectionLimit(5);
//                config.setSelectedBottomHeight(R.dimen.bottom_height);
//                getImages(config);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
            image_uris.add(CropImage.getPickImageResultUri(getContext(), data));
            image_urisPath = data.getExtras().getStringArrayList("extra_result_selection_path");
            if (mSelected != null) {
                showMedia();
            }


        }
    }




    private void showMedia() {
        // Remove all views before
        // adding the new ones.
        mSelectedImagesContainer.removeAllViews();
        if (mSelected.size() >= 1) {
            mSelectedImagesContainer.setVisibility(View.VISIBLE);
        }
        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        if (mSelected != null || mSelected.size() == 0) {
            SubmitImage.setVisibility(View.VISIBLE);
            HideDefult.setVisibility(View.INVISIBLE);

        } else {
            SubmitImage.setVisibility(View.GONE);
        }
        for (Uri uri : mSelected) {
            View imageHolder = LayoutInflater.from(getContext()).inflate(R.layout.image_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);
            Glide.with(this)
                    .load(uri.toString())
                    .fitCenter()
                    .into(thumbnail);
            mSelectedImagesContainer.addView(imageHolder);
            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));
        }
        for (String Imagepath : image_urisPath) {
            File file = null;
            file = new File(Imagepath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            fileToUpload.add(MultipartBody.Part.createFormData("imagefile", file.getName(), mFile));
        }


    }


}
