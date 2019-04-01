package com.rdavepatient.soft.meetdoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.rdavepatient.soft.meetdoctor.Models.NotificationModel;
import com.rdavepatient.soft.meetdoctor.Models.Otprecvier;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.remote.ApiUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;


/**
 * Created by user on 01-12-2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private int user_id, story_id;
    private Intent mIntent;
    Context mContext;
    private ArrayList<NotificationModel.NotificationBean> mData;

    public NotificationAdapter(Context context, ArrayList<NotificationModel.NotificationBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationViewHolder holder, int position) {
        //holder.tv_notification_narration.setText(Html.fromHtml(mData.get(position).getNotification().trim()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tv_notification_narration.setText(Html.fromHtml(mData.get(position).getNotification(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tv_notification_narration.setText(Html.fromHtml(mData.get(position).getNotification()));
        }


        holder.tv_notification_date.setText(mData.get(position).getCreatedon().trim());
//        Glide.with(mContext).load(mData.get(position).getImageurl())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.user)
//                .override(180, 180)
//                .dontAnimate()
//                .signature(new StringSignature(mData.get(position).getImageurl()))
//                .into(holder.profile_icon);


        /*---------------- If notification not Viewed Change background ---------------------------*/
        if (mData.get(position).getStatus() == 0) {
            holder.notification_container.setBackgroundColor(Color.parseColor("#eeeeee"));
        } else {
            holder.notification_container.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        /*------------------------------------------------------------------------------------*/



        /*-------------------------- OnClick Profile Icon--------------------------------*/

//        holder.profile_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!mData.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("admin")) {
//                    user_id = mData.get(holder.getAdapterPosition()).getFrom_user();
//                    mIntent = new Intent(mContext, ViewProfileActivity.class);
//                    mIntent.putExtra("user_id", user_id);
//                    mContext.startActivity(mIntent);
//                }
//            }
//        });

        /*------------------------------------------------------------------------------*/



        /*-------------------------- OnClick Container click---------------------------------*/

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int postid= mData.get(position).getPostid();
//                Postviews(mData.get(position).getNotificationId());
//                Context context = view.getContext();
//                Intent intent = new Intent(mContext, FullPostActivity.class);
//                intent.putExtra("Postid",postid);
//                context.startActivity(intent);

            }
        });

        /*---------------------------------------------------------------------------------*/




    }







    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clearData() {
        int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }


    public void setData(ArrayList<NotificationModel.NotificationBean> data) {
        int count = getItemCount();
        mData.addAll(data);
        notifyItemRangeInserted(count, data.size());
    }

    public int getLastVisibleItemId() {
        if (mData.isEmpty()) {
            return 0;
        }
        return mData.get(mData.size() - 1).getRow_id();
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_icon;
        TextView tv_notification_narration, tv_notification_date;
        LinearLayout container_follow_unfollow, notification_container, container;
        ImageButton btn_follow_unfollow;


        public NotificationViewHolder(View itemView) {
            super(itemView);
            profile_icon = itemView.findViewById(R.id.profile_icon);
            tv_notification_date = itemView.findViewById(R.id.notification_date);
            tv_notification_narration = itemView.findViewById(R.id.notification_narration);
            container_follow_unfollow = itemView.findViewById(R.id.container_follow_unfollow);
            btn_follow_unfollow = itemView.findViewById(R.id.follow_btn);
            notification_container = itemView.findViewById(R.id.notification_container);
            container = itemView.findViewById(R.id.container);

        }
    }
    public void clearNotification() {
        for (int i = 0; i < getItemCount(); i++) {
            mData.get(i).setStatus(1);
        }
        notifyDataSetChanged();
    }



    private void Postviews(int Postid) {
        ApiUtils.getAPIService().Viewnotification(Postid).subscribeOn(rx.schedulers.Schedulers.io()).observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
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
