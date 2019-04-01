package com.rdavepatient.soft.meetdoctor.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.R;

import java.util.List;

public class All_Photo_Adapter extends RecyclerView.Adapter<All_Photo_Adapter.QueslistMyView> {


    private List<String> list;
    Context context;
    AlertDialog dialog;

    public class QueslistMyView extends RecyclerView.ViewHolder {

        public TextView commentcounter,comment;
        public ImageView edit_post;

        public QueslistMyView(View view) {
            super(view);

//            Doctername = (TextView) view.findViewById(R.id.Doctername);
            commentcounter = (TextView) view.findViewById(R.id.commentcounter);
        }

    }


    public All_Photo_Adapter(Context context, List<String> horizontalList) {
        this.list = horizontalList;
        this.context =context;
    }

    @Override
    public QueslistMyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myphotolist, parent, false);

        return new QueslistMyView(itemView);
    }

    @Override
    public void onBindViewHolder(final QueslistMyView holder, final int position) {

//        holder.Doctername.setText(list.get(position));

        holder.commentcounter.setText(list.get(position));

    }




    @Override
    public int getItemCount() {
        return list.size();
    }

}

