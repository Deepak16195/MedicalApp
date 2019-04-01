package com.rdavepatient.soft.meetdoctor.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.Activity.BookingActivity;
import com.rdavepatient.soft.meetdoctor.Models.Appointment;
import com.rdavepatient.soft.meetdoctor.Models.Otprecvier;
import com.rdavepatient.soft.meetdoctor.Models.PatientAppoint;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class All_PatientAppAdapter extends RecyclerView.Adapter<All_PatientAppAdapter.QueslistMyView> {


    private List<PatientAppoint.AppointmentsEntity> list;
    Context mcontext;

    public class QueslistMyView extends RecyclerView.ViewHolder {
        @BindView(R.id.img_home_profile_pic)
        ImageView imgHomeProfilePic;
        @BindView(R.id.lay_rel_img)
        RelativeLayout layRelImg;
        @BindView(R.id.Doctername)
        TextView Doctername;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.Docter_Time)
        TextView DocterTime;
        @BindView(R.id.Booknow)
        TextView Booknow;
        @BindView(R.id.OpenMAp)
        TextView OpenMAp;
        @BindView(R.id.linearLayout4)
        RelativeLayout linearLayout4;
        @BindView(R.id.Doctors)
        CardView Doctors;


        public QueslistMyView(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Doctername = (TextView) view.findViewById(R.id.Doctername);
            location = (TextView) view.findViewById(R.id.location);

        }
    }


    public All_PatientAppAdapter(Context context, List<PatientAppoint.AppointmentsEntity> mListData) {
        this.list = mListData;
        this.mcontext = context;
    }

    @Override
    public QueslistMyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appomentslist, parent, false);
        return new QueslistMyView(itemView);
    }

    @Override
    public void onBindViewHolder(final QueslistMyView holder, final int position) {

//        holder.Doctername.setText(list.get(position));

        holder.location.setText(list.get(position).getDocterAddress() + "");
        holder.Doctername.setText(list.get(position).getDoctorName());
        holder.DocterTime.setText(list.get(position).getAppointmentDate() + " " + list.get(position).getAppointmentTime());
        holder.OpenMAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String destinationLatitude = list.get(position).getLatitute();
                String destinationLongitude = list.get(position).getLogitute();
                Context context = view.getContext();

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destinationLatitude + ", " + destinationLongitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }

        });


        holder.Booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Appointment appointment = new Appointment();
                                appointment.setDoctorId(list.get(position).getDoctorId());
                                appointment.setAppointmentId(list.get(position).getAppointmentId());
                                appointment.setPatientId(list.get(position).getPatientId());
                                CancalAppointemnts(appointment, 1);
                                dialog.dismiss();
                                if (dialog != null) {

                                }
                                //Yes button clicked
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();

                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setMessage("Are you sure you want to cancel ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void CancalAppointemnts(Appointment dataSend, int type) {
        ApiUtils.getAPIService().CancelAppointment(dataSend, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Otprecvier>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Utiles.customMessage(getContext(), e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Otprecvier response) {
                        if (response.getStatus() == 101) {
                            return;
                        }
                        if (response.getStatus() == 200) {

                            //Toasty.normal(g, response.getMessage()).show();


                        }
                    }
                });
    }

}

