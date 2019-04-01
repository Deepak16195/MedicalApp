package com.rdavepatient.soft.meetdoctor.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rdavepatient.soft.meetdoctor.Activity.BookingActivity;
import com.rdavepatient.soft.meetdoctor.R;
import com.rdavepatient.soft.meetdoctor.TagLayout.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;
    boolean isAvalble;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        T t = mDataList.get(position);

        if (t instanceof String) {
            textView.setText((String) t);
            isAvalble = BookingActivity.dataCategoryid.get(position);
           if(isAvalble == true)
           {
               textView.setBackground(mContext.getResources().getDrawable(R.color.Buttoncolor));
               textView.setEnabled(false);
               textView.setTextColor(Color.parseColor("#bdbdbd"));
           }

        }
        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void onlyAdd(T data) {
        mDataList.add(data);
        notifyDataSetChanged();
    }
    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
       // onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        if (position % 2 == 0) {
            return true;
        }
        return false;
    }
}
