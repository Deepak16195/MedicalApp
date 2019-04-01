package com.rdavepatient.soft.meetdoctor.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.rdavepatient.soft.meetdoctor.Adapter.All_Photo_Adapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.MainActivity;
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

public class UplodeReportsActivty extends AppCompatActivity {

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
   // List<DocterReorts> items = new ArrayList<>();
    List<String> ReportsList = new ArrayList<String>();
    All_Photo_Adapter RecyclerViewHorizontalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplode_reports_activty);
        mSelectedImagesContainer = findViewById(R.id.selected_photos_container);
        Capture = findViewById(R.id.lin_capture);
        SubmitImage = findViewById(R.id.SubmitImage);
        backText = findViewById(R.id.backbutton);
        rv_photo = findViewById(R.id.rv_photo);
        mApiService = ApiUtils.getAPIService();
        UserID = SharePrefarence.getmInstance(UplodeReportsActivty.this).getUserId();
        checkPermissionNew();
        initRecyclerView();

      //  GetClinctImage(UserID);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UplodeReportsActivty.this, MainActivity.class);
                startActivity(i);
            }
        });

        Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelected.clear();
                image_urisPath.clear();
                image_uris.clear();
                fileToUpload.clear();
                Matisse.from(UplodeReportsActivty.this)
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


        SubmitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fileToUpload.size() >= 1) {
                    //UpdateProfileImage();
                }


            }
        });

    }

    public void initRecyclerView() {
        RecyclerViewLayoutManager = new LinearLayoutManager(UplodeReportsActivty.this);
        rv_photo.setLayoutManager(RecyclerViewLayoutManager);
        // Adding items to RecyclerView.
        HorizontalLayout = new LinearLayoutManager(UplodeReportsActivty.this, LinearLayoutManager.HORIZONTAL, false);
        rv_photo.setLayoutManager(HorizontalLayout);

    }


    private void checkPermissionNew() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.O_MR1) {
            //Checking Permission of Read Phone State
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                }, Request_Permission);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
            image_uris.add(CropImage.getPickImageResultUri(this, data));
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
        } else {
            SubmitImage.setVisibility(View.GONE);
        }
        for (Uri uri : mSelected) {
            View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
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
