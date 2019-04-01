package com.rdavepatient.soft.meetdoctor.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.Activity.AddFamilymemberActivty;
import com.rdavepatient.soft.meetdoctor.Activity.Docter_details;
import com.rdavepatient.soft.meetdoctor.Models.Famelimember;
import com.rdavepatient.soft.meetdoctor.Models.Otprecvier;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.Utility.Utils;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class familyListAdapter extends RecyclerView.Adapter<familyListAdapter.QueslistMyView> {


    public  static  List<Famelimember.FamelyEntity> list = new ArrayList<>();
    Context mcontext;
    public TextView Tx_cancel, DeletePost, EditPost;

    public class QueslistMyView extends RecyclerView.ViewHolder {

        public TextView location, Menbername, Mo_number, blood_group, reelection, DOb, Age;
        public ImageView edit_family;
        AlertDialog dialog;
        public QueslistMyView(View view) {
            super(view);
            location = (TextView) view.findViewById(R.id.location);
            Menbername = (TextView) view.findViewById(R.id.Menbername);
            Mo_number = (TextView) view.findViewById(R.id.Mo_number);
            blood_group = (TextView) view.findViewById(R.id.blood_group);
            Age = (TextView) view.findViewById(R.id.Age);
            reelection = (TextView) view.findViewById(R.id.reelection);
            DOb = (TextView) view.findViewById(R.id.DOb);
            edit_family = (ImageView) view.findViewById(R.id.edit_family);
        }

        public void AlterdilogCreation(final int Postid) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            Tx_cancel = alertLayout.findViewById(R.id.Tx_cancel);
            DeletePost = alertLayout.findViewById(R.id.DeletePost);
            EditPost = alertLayout.findViewById(R.id.Edit);

            // disallow cancel of AlertDialog on click of back button and outside touch
            dialog = alert.create();
            dialog.show();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) mcontext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int displayWidth = displayMetrics.widthPixels;
            int displayHeight = displayMetrics.heightPixels;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            // Set the alert dialog window width and height
            // Set alert dialog width equal to screen width 90%
            // int dialogWindowWidth = (int) (displayWidth * 0.9f);
            // Set alert dialog height equal to screen height 90%
            // int dialogWindowHeight = (int) (displayHeight * 0.9f);
            // Set alert dialog width equal to screen width 70%
            int dialogWindowWidth = (int) (displayWidth * 0.51f);
            // Set alert dialog height equal to screen height 70%
            //int dialogWindowHeight = (int) (displayHeight * 0.5f);
            // Set the width and height for the layout parameters
            // This will bet the width and height of alert dialog
            layoutParams.width = dialogWindowWidth;
            //  layoutParams.height = dialogWindowHeight;
            // Apply the newly created layout parameters to the alert dialog window
            dialog.getWindow().setAttributes(layoutParams);


            Tx_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });



            DeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int postidtodelite = Postid;
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface Dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    DeleteFamelimember(postidtodelite);
                                    list.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    notifyItemRangeChanged(getAdapterPosition(), list.size());
                                    dialog.dismiss();
                                    if(Dialog!= null)
                                    {
                                        Dialog.dismiss();
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
                    builder.setMessage("Are you sure to Delete ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }
            });


            EditPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int post = Postid;
                    int pos= getAdapterPosition();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AddFamilymemberActivty.class);
                    intent.putExtra("Postid", pos+"");
                    context.startActivity(intent);
                }
            });


        };
    }


    public familyListAdapter(Context context, List<Famelimember.FamelyEntity> horizontalList) {
        this.list = horizontalList;
        this.mcontext = context;
    }

    @Override
    public QueslistMyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.familylist, parent, false);

        return new QueslistMyView(itemView);
    }

    @Override
    public void onBindViewHolder(final QueslistMyView holder, final int position) {
        holder.Age.setText("Age : " + list.get(position).getAge());
        holder.Menbername.setText(list.get(position).getFamelyMemberName());
        holder.Mo_number.setText(list.get(position).getMobileNo());
        holder.blood_group.setText(list.get(position).getBloodGrop());
        holder.location.setText(list.get(position).getFamelyMemberName());
        holder.reelection.setText(list.get(position).getRelation());
        holder.DOb.setText(list.get(position).getDOB());
        holder.edit_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Postid = Integer.valueOf(list.get(position).getFamelymemberId());
                holder.AlterdilogCreation(Postid);
            }
        });
        holder.Mo_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = holder.Mo_number.getText().toString();
                if(!number.equalsIgnoreCase("0"))
                {
                    Context context = view.getContext();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                    context.startActivity(intent);
                }
                else
                {
                    Utils.custoAlert(mcontext, number+"");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void DeleteFamelimember(int F_id) {
        ApiUtils.getAPIService().DeleteFamelimember(F_id).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
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


                        }
                    }
                });
    }

}

