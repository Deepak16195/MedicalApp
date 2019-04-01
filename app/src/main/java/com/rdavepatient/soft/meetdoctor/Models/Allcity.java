package com.rdavepatient.soft.meetdoctor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class Allcity {


    @Expose
    @SerializedName("City")
    private List<CityEntity> City;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Status")
    private int Status;

    public List<CityEntity> getCity() {
        return City;
    }

    public void setCity(List<CityEntity> City) {
        this.City = City;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public static class CityEntity {
        @Expose
        @SerializedName("CityName")
        private String CityName;
        @Expose
        @SerializedName("Cityid")
        private int Cityid;

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public int getCityid() {
            return Cityid;
        }

        public void setCityid(int Cityid) {
            this.Cityid = Cityid;
        }
    }
}
