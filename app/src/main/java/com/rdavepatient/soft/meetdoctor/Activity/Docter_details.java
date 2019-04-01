package com.rdavepatient.soft.meetdoctor.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rdavepatient.soft.meetdoctor.Models.DetailsOfDocter;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.Utility.CheckInternet;
import com.rdavepatient.soft.meetdoctor.Utility.Utils;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Docter_details extends AppCompatActivity implements OnMapReadyCallback {

    Button Bookbutton;
    @BindView(R.id.img_home_profile_pic)
    ImageView imgHomeProfilePic;
    @BindView(R.id.lay_rel_img)
    RelativeLayout layRelImg;
    @BindView(R.id.Doctername)
    TextView Doctername;
    @BindView(R.id.subQulifaction)
    TextView subQulifaction;
    @BindView(R.id.speclist)
    TextView speclist;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    public static int Docterid;
    double sourceLatitude /*= 19.244516*/;
    double sourceLongitude /*= 72.862827*/;
    @BindView(R.id.Address)
    TextView Address;
    String doctername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_details);
        ButterKnife.bind(this);
        Bookbutton = (Button) findViewById(R.id.Bookbutton);


        if (CheckInternet.isConnectingToInternet(Docter_details.this)) {
            GetDocter(Docterid);
        } else {
            Utils.customMessage(getApplication(), "Please check your Internet connection!");
        }




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Docter_details.this, BookingActivity.class);
                BookingActivity.DocternameData=doctername;
                startActivity(intent);
            }
        });


    }


    private void GetDocter(int data) {
        final ProgressDialog loading = ProgressDialog.show(Docter_details.this, "Loading", "Please wait...", false, false);
        loading.setCancelable(true);
        ApiUtils.getAPIService().GetDetailsOfDocter(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DetailsOfDocter>() {
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
                    public void onNext(DetailsOfDocter data) {
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
                            sourceLatitude = Double.valueOf(data.getUser().getProfesion().getLatitude());
                            sourceLongitude = Double.valueOf(data.getUser().getProfesion().getLongitude());
                            Doctername.setText(data.getUser().getBasicDetails().getUserName());
                            subQulifaction.setText(data.getUser().getProfesion().getSpecialisationName());
                            Address.setText(data.getUser().getProfesion().getMapAddress());
                            doctername=data.getUser().getBasicDetails().getUserName();
                        }
                    }
                });


    }

    public void back(View view) {
        super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(sourceLatitude, sourceLongitude))
                .title("Marker"));
        CameraPosition cameraPosition = new CameraPosition.Builder().
                target(new LatLng(sourceLatitude, sourceLongitude)).
                zoom(15).
                bearing(0).
                build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.setMaxZoomPreference(30.0f);
    }

}
