package com.rdavepatient.soft.meetdoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdavepatient.soft.meetdoctor.Activity.BookingActivity;
import com.rdavepatient.soft.meetdoctor.Activity.Docter_details;
import com.rdavepatient.soft.meetdoctor.Models.DocterListData;
import com.rdavepatient.soft.meetdoctor.R;

import java.util.List;

import butterknife.BindView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class All_docterAdapter extends RecyclerView.Adapter<All_docterAdapter.QueslistMyView> {


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
    @BindView(R.id.readmore)
    TextView readmore;
    @BindView(R.id.linearLayout4)
    RelativeLayout linearLayout4;
    @BindView(R.id.Doctors)
    CardView Doctors;
    private List<DocterListData.DocterBean> list;
    String price;
    Context mContext;

    public class QueslistMyView extends RecyclerView.ViewHolder {

        public TextView Address, readmore, Doctername, Docter_Time, Gender, Exprience,Specilist;
        MaterialRatingBar Reating;
        ImageView img_home_profile_pic;

        public QueslistMyView(View view) {
            super(view);
            price = view.getResources().getString(R.string.currency);
            Doctername = (TextView) view.findViewById(R.id.Doctername);
            Address = (TextView) view.findViewById(R.id.Address);
            Docter_Time = (TextView) view.findViewById(R.id.Docter_Time);
            readmore = (TextView) view.findViewById(R.id.readmore);
            Specilist = (TextView) view.findViewById(R.id.Specilist);
            Gender = (TextView) view.findViewById(R.id.Gender);
            Booknow = (TextView) view.findViewById(R.id.Booknow);
            Exprience = (TextView) view.findViewById(R.id.Exprience);
            Reating = (MaterialRatingBar) view.findViewById(R.id.Reating);
            img_home_profile_pic = (ImageView) view.findViewById(R.id.img_home_profile_pic);
            readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(context, Docter_details.class);
                    Docter_details.Docterid = list.get(pos).getUserid();
                    context.startActivity(intent);
                }
            });

        }
    }


    public All_docterAdapter(Context context, List<DocterListData.DocterBean> mListData) {
        this.list = mListData;
        this.mContext = context;
    }

    @Override
    public QueslistMyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.docters_list, parent, false);
        return new QueslistMyView(itemView);
    }

    @Override
    public void onBindViewHolder(final QueslistMyView holder, final int position) {

        holder.Address.setText(list.get(position).getAdress() + "");
        holder.Doctername.setText(list.get(position).getUserName());
        holder.Docter_Time.setText(list.get(position).getStartTime() + " - " + list.get(position).getEndTime());
        holder.Docter_Time.setText(price + " " + list.get(position).getFees());
        holder.Reating.setProgress(list.get(position).getRate());
        String GenderFull = list.get(position).getGender();
        if (GenderFull.equalsIgnoreCase("M")) {
            GenderFull = "Male";
        } else {
            GenderFull = "FeMale";
        }
        holder.Gender.setText(GenderFull);
        holder.Exprience.setText(list.get(position).getExprience() + " Years");

        String Specilists="";
        for(int i=0;i<list.get(position).getSpecialisation().size();i++)
        {
            Specilists=Specilists + list.get(position).getSpecialisation().get(i).getSpecialisationName()+"  ";
        }
        holder.Specilist.setText(Specilists);

        Booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("DocterId", list.get(position).getUserid());
                BookingActivity.DocternameData = list.get(position).getUserName();
                context.startActivity(intent);

            }
        });
        Glide.with(mContext).load(list.get(position).getProfileImage()).override(300, 300).centerCrop().thumbnail(0.5f).crossFade().placeholder(R.drawable.user).error(R.drawable.user).into(holder.img_home_profile_pic);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

