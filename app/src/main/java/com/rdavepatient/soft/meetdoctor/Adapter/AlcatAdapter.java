package com.rdavepatient.soft.meetdoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rdavepatient.soft.meetdoctor.Activity.All_Doctors;
import com.rdavepatient.soft.meetdoctor.Activity.SelectCatgaryforDocter;
import com.rdavepatient.soft.meetdoctor.Models.DocterSpecialisation;
import com.rdavepatient.soft.meetdoctor.Models.DocterType;
import com.rdavepatient.soft.meetdoctor.R;

import java.util.ArrayList;
import java.util.List;

public class AlcatAdapter extends RecyclerView.Adapter<AlcatAdapter.QueslistMyView> implements Filterable {


    private List<DocterType.DocterTypeEntity> list;
    private List<DocterType.DocterTypeEntity> ListFiltered;
    Context mcontext;

    public class QueslistMyView extends RecyclerView.ViewHolder {

        public TextView Cattype,location;
        public CardView List;
        public ImageView imageView_icon;

        public QueslistMyView(View view) {
            super(view);
            Cattype = (TextView) view.findViewById(R.id.Cattype);
            location = (TextView) view.findViewById(R.id.location);
            List = (CardView) view.findViewById(R.id.List);
            imageView_icon = (ImageView) view.findViewById(R.id.imageView_icon);

        }
    }


    public AlcatAdapter(Context context, List<DocterType.DocterTypeEntity> horizontalList) {
        this.list = horizontalList;
        this.ListFiltered=horizontalList;
        this.mcontext=context;
    }

    @Override
    public QueslistMyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.catlist, parent, false);

        return new QueslistMyView(itemView);
    }

    @Override
    public void onBindViewHolder(final QueslistMyView holder, final int position) {

        Glide.with(mcontext).load(ListFiltered.get(position).getIconPath()).centerCrop().thumbnail(0.5f).crossFade().placeholder(R.drawable.doctors).error(R.drawable.doctors).into(holder.imageView_icon);
        holder.Cattype.setText(ListFiltered.get(position).getDoctertypeName());
        holder.location.setText(ListFiltered.get(position).toString());
        holder.List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view .getContext();
                Intent intent = new Intent(context, All_Doctors.class);
                All_Doctors.DocterType=ListFiltered.get(position).getDoctertypeid();
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return ListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ListFiltered = list;
                } else {
                    List<DocterType.DocterTypeEntity> filteredList = new ArrayList<>();
                    for (DocterType.DocterTypeEntity row : list) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getDoctertypeName().toString().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    ListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ListFiltered = (ArrayList<DocterType.DocterTypeEntity>) filterResults.values;
//                if(ListFiltered.size() == 0)
//                {
//                    mrecyclerView.setVisibility(View.GONE);
//                    mnodataGif.setVisibility(View.VISIBLE);
//                }
//                else {
//                    mrecyclerView.setVisibility(View.VISIBLE);
//                    mnodataGif.setVisibility(View.GONE);
//                }
                notifyDataSetChanged();
            }
        };
    }

}

