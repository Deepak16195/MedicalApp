package com.rdavepatient.soft.meetdoctor.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arun on 16-08-2017.
 */

public class DateModel implements Parcelable {

    private String Date;
    private String Price;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Date);
        dest.writeString(this.Price);
    }

    public DateModel() {
    }

    protected DateModel(Parcel in) {
        this.Date = in.readString();
        this.Price = in.readString();
    }

    public static final Parcelable.Creator<DateModel> CREATOR = new Parcelable.Creator<DateModel>() {
        @Override
        public DateModel createFromParcel(Parcel source) {
            return new DateModel(source);
        }

        @Override
        public DateModel[] newArray(int size) {
            return new DateModel[size];
        }
    };
}
