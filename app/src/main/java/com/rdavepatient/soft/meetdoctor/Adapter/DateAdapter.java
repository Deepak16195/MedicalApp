package com.rdavepatient.soft.meetdoctor.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rdavepatient.soft.meetdoctor.Models.DateSliderData;
import com.rdavepatient.soft.meetdoctor.R;


public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {


    private int row_index = 1;
    private Context mContext;


    public DateAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dat_depature_layout, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DateViewHolder holder, final int position) {
        holder.txDate.setText(DateSliderData.getData().get(position).getDate());
        holder.txAmount.setText(DateSliderData.getData().get(position).getPrice());
        holder.li_layoyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        if (row_index == position) {
            holder.li_layoyt.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            holder.txDate.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.txAmount.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            holder.hcSelector.setVisibility(View.VISIBLE);
        } else {
            holder.hcSelector.setVisibility(View.INVISIBLE);
            holder.li_layoyt.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.txDate.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            holder.txAmount.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return DateSliderData.getData().size();
    }


    public class DateViewHolder extends RecyclerView.ViewHolder {

        protected View hcSelector;
        protected TextView txDate;
        protected TextView txAmount;
        LinearLayout li_layoyt;

        public DateViewHolder(View itemView) {
            super(itemView);
            hcSelector = itemView.findViewById(R.id.hc_selector);
            txDate = itemView.findViewById(R.id.tx_date);
            txAmount = itemView.findViewById(R.id.tx_amount);
            li_layoyt = itemView.findViewById(R.id.li_layoyt);
        }
    }

//    private void FragmentCall() {
//        AppCompatActivity activity = (AppCompatActivity) mContext;
//        Fragment myFragment = new FragmentFlightBookingFour();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
//    }
}
