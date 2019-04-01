package com.rdavepatient.soft.meetdoctor.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;
import com.rdavepatient.soft.meetdoctor.Fragments.Homepage;
import com.rdavepatient.soft.meetdoctor.Models.Allcity;
import com.rdavepatient.soft.meetdoctor.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class All_CityAdapter extends RecyclerView.Adapter<All_CityAdapter.QueslistMyView> {


    private List<Allcity.CityEntity> list;
    TextView SelectedAddreess;
    Context mContext;

    public class QueslistMyView extends RecyclerView.ViewHolder {
        @BindView(R.id.Cattype)
        TextView Cattype;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.linearLayout4)
        RelativeLayout linearLayout4;
        @BindView(R.id.List)
        CardView List;

        public QueslistMyView(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


    public All_CityAdapter(Context Context ,TextView mSelectedAddreess, List<Allcity.CityEntity> mListData) {
        this.list = mListData;
        this.SelectedAddreess = mSelectedAddreess;
        this.mContext=Context;
    }

    @Override
    public QueslistMyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.citylist, parent, false);
        return new QueslistMyView(itemView);
    }

    @Override
    public void onBindViewHolder(final QueslistMyView holder, final int position) {

        holder.Cattype.setText(list.get(position).getCityName());
        holder.List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedAddreess.setText("       "+list.get(position).getCityName() + "");
                SharePrefarence.getmInstance(mContext).setAREANAME(list.get(position).getCityName());
            }
        });

//        Booknow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context = view.getContext();
//                Intent intent = new Intent(context, BookingActivity.class);
//                 intent.putExtra("DocterId", list.get(position).getPatientId());
////                intent.putExtra("Ranks", rank);
//                context.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

