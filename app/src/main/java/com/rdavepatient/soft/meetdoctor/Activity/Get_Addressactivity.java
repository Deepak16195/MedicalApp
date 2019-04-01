package com.rdavepatient.soft.meetdoctor.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rdavepatient.soft.meetdoctor.Adapter.All_CityAdapter;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Fragments.Homepage;
import com.rdavepatient.soft.meetdoctor.Map.GPSTracker;
import com.rdavepatient.soft.meetdoctor.Models.Allcity;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Get_Addressactivity extends AppCompatActivity {

    @BindView(R.id.logout)
    ImageButton logout;
    @BindView(R.id.Cityname)
    RecyclerView Cityname;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    All_CityAdapter RecyclerViewHorizontalAdapter;
    APIService mApiService;
    List<Allcity.CityEntity> mListData;
    @BindView(R.id.SelectedAddreess)
    TextView SelectedAddreess;
    @BindView(R.id.getFullAddreessFormGoogle)
    TextView getFullAddreessFormGoogle;
    double sourceLatitude;
    double sourceLongitude;
    String sourceAddress, City, State, Contry, PostalCode,sublocalCity;
    LocationManager locationManager;
    String currentLocation, current_locality;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    GPSTracker gps;
    List<android.location.Address> addresses;
    String city;

    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    Location location; // location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__addressactivity);
        ButterKnife.bind(this);
        HorizontalLayout = new LinearLayoutManager(Get_Addressactivity.this, LinearLayoutManager.VERTICAL, false);
        Cityname.setLayoutManager(HorizontalLayout);
        mApiService = ApiUtils.getAPIService();
        int id = SharePrefarence.getmInstance(Get_Addressactivity.this).getUserId();
        GetCity();
        Location();



//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(this, Locale.getDefault());
//        addresses = geocoder.getFromLocation(sourceLatitude, sourceLongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName();


    }





    public double getLatitude() {
        if (location != null) {
            sourceLatitude = location.getLatitude();
        }
        // return latitude
        return sourceLatitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            sourceLongitude = location.getLongitude();
        }
        // return longitude
        return sourceLongitude;
    }


    private void GetCity() {
        final ProgressDialog loading = ProgressDialog.show(Get_Addressactivity.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        mApiService.GetAllCity().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Allcity>() {
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
                    public void onNext(Allcity data) {
                        loading.dismiss();
                        if (data.getStatus() == 101) {
                            Toasty.normal(Get_Addressactivity.this, "Email Id Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 102) {
                            Toasty.normal(Get_Addressactivity.this, "Mobile No Already Exist!").show();
                            return;
                        }
                        if (data.getStatus() == 200) {

                            mListData = data.getCity();
                            RecyclerViewHorizontalAdapter = new All_CityAdapter(Get_Addressactivity.this,SelectedAddreess, mListData);
                            Cityname.setAdapter(RecyclerViewHorizontalAdapter);

                        }
                    }
                });


    }

    public void back(View view) {
        super.onBackPressed();
    }

    @OnClick({R.id.logout, R.id.getFullAddreessFormGoogle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logout:
                break;
            case R.id.getFullAddreessFormGoogle:
                GPSTracker gs = new GPSTracker(getApplicationContext());
                Location longi = gs.getLocation();
                sourceLatitude = longi.getLatitude();
                sourceLongitude = longi.getLongitude();
                String data = getGoogleAddress();
                SelectedAddreess.setText("    "+sublocalCity);
                Homepage.AreaName.setText( sublocalCity+"");
                SharePrefarence.getmInstance(getApplicationContext()).setAREANAME(sublocalCity);
                break;
        }
    }


    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses == null) {
                addresses = new ArrayList<>();
            }
            if (addresses.size() > 0) {
                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                return addresses.get(0);
            }


        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "failed to locate you , please try again!", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
        return null;
    }

    public void Location() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

    }


    public String getGoogleAddress() {
        android.location.Address locationAddress = getAddress(sourceLatitude, sourceLongitude);
        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();
            City = locationAddress.getLocality();
            sublocalCity = locationAddress.getSubLocality();
            State = locationAddress.getAdminArea();
            Contry = locationAddress.getCountryName();
            PostalCode = locationAddress.getPostalCode();

            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;
                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;
                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;
                    this.city = city;
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }
                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;
                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;
            }
        }
        return currentLocation;

    }


}
