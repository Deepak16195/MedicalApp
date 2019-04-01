package com.rdavepatient.soft.meetdoctor;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Fragments.Account;
import com.rdavepatient.soft.meetdoctor.Fragments.Homepage;
import com.rdavepatient.soft.meetdoctor.Fragments.Notification;
import com.rdavepatient.soft.meetdoctor.Fragments.Oppiments;
import com.rdavepatient.soft.meetdoctor.Fragments.Reminder;
import com.rdavepatient.soft.meetdoctor.Fragments.Reports;
import com.rdavepatient.soft.meetdoctor.Map.GPSTracker;
import com.rdavepatient.soft.meetdoctor.Models.Otprecvier;
import com.rdavepatient.soft.meetdoctor.banner.BannerView;
import com.rdavepatient.soft.meetdoctor.remote.APIService;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private BannerView mBannerView;
    boolean doubleBackToExitPressedOnce = false;
    int UserID;
    APIService mApiService;
    LocationManager locationManager;
    double sourceLatitude;
    double sourceLongitude;
    String sourceAddress, City, State, Contry, PostalCode,sublocalCity;
    List<android.location.Address> addresses;
    String currentLocation;
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = ApiUtils.getAPIService();
        UserID = SharePrefarence.getmInstance(MainActivity.this).getUserId();
        String data = FirebaseInstanceId.getInstance().getToken();
        UpadteToken(UserID + "", data);
        ACCESS_FINE_LOCATION();
        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.bnv_booking_list);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationItemView);
        android.support.v4.app.Fragment selectedFragment = null;


        GPSTracker gs = new GPSTracker(getApplicationContext());
        Location longi = gs.getLocation();
        sourceLatitude = longi.getLatitude();
        sourceLongitude = longi.getLongitude();
        String FulllocationAddress = getGoogleAddress();
        if(sublocalCity != null)
        {
            SharePrefarence.getmInstance(getApplicationContext()).setAREANAME(sublocalCity);
        }
       else {
            SharePrefarence.getmInstance(getApplicationContext()).setAREANAME(City);
        }

        bottomNavigationItemView.setSelectedItemId(R.id.navigation_home);
        selectedFragment = Homepage.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(selectedFragment, "home")
                // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                .addToBackStack(null)
                .commit();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                android.support.v4.app.Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.navigation_home:
                        selectedFragment = Homepage.newInstance();
                        break;
                   /* case R.id.navigation_Reminders:

                        selectedFragment = Oppiments.newInstance();
                        break;*/
                    case R.id.navigation_dashboard:

                        selectedFragment = Reports.newInstance();
                        break;
                    case R.id.navigation_notifications:
                        selectedFragment = Notification.newInstance();
                        break;
                    case R.id.navigation_Account:
                        selectedFragment = Account.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;
            }
        });

    }

    public void ACCESS_FINE_LOCATION() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

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
            if(locationAddress.getPostalCode() != null)
            {
                SharePrefarence.getmInstance(getApplicationContext()).setAreacode(locationAddress.getPostalCode());
            }
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

    private void UpadteToken(String userid, String FCMTOKEN) {
        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Loading", "Please wait...", false, false);
        mApiService.saveFCMToken(userid, FCMTOKEN).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Otprecvier>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // Utiles.customMessage(RegisterActivity.this, e.getMessage().toString());
                        Log.e("Token_Status", "FCM Token Can't be Saved");
                        loading.dismiss();
                    }

                    @Override
                    public void onNext(Otprecvier response) {
                        if (response.getStatus() == 101) {
                            Log.e("Token_Status", "FCM Token Can't be Saved");
                            return;
                        }
                        if (response.getStatus() == 200) {
                            loading.dismiss();
                            Log.e("Token_Status", "FCM Token Saved");

                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv_booking_list);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.navigation_home != seletedItemId) {
            setHomeItem(MainActivity.this);
        } else {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).create().show();
        }
    }
    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.bnv_booking_list);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

    }


}
