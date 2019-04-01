package com.rdavepatient.soft.meetdoctor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class Famelimember {


    @Expose
    @SerializedName("Famely")
    private List<FamelyEntity> Famely;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Status")
    private int Status;

    public List<FamelyEntity> getFamely() {
        return Famely;
    }

    public void setFamely(List<FamelyEntity> Famely) {
        this.Famely = Famely;
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

    public static class FamelyEntity {
        @Expose
        @SerializedName("Relation")
        private String Relation;
        @Expose
        @SerializedName("Gender")
        private String Gender;
        @Expose
        @SerializedName("BloodGrop")
        private String BloodGrop;
        @Expose
        @SerializedName("Age")
        private int Age;
        @Expose
        @SerializedName("DOB")
        private String DOB;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("FamelyMemberName")
        private String FamelyMemberName;
        @Expose
        @SerializedName("FamelymemberId")
        private int FamelymemberId;
        @Expose
        @SerializedName("Userid")
        private int Userid;

        public String getRelation() {
            return Relation;
        }

        public void setRelation(String Relation) {
            this.Relation = Relation;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public String getBloodGrop() {
            return BloodGrop;
        }

        public void setBloodGrop(String BloodGrop) {
            this.BloodGrop = BloodGrop;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public String getFamelyMemberName() {
            return FamelyMemberName;
        }

        public void setFamelyMemberName(String FamelyMemberName) {
            this.FamelyMemberName = FamelyMemberName;
        }

        public int getFamelymemberId() {
            return FamelymemberId;
        }

        public void setFamelymemberId(int FamelymemberId) {
            this.FamelymemberId = FamelymemberId;
        }

        public int getUserid() {
            return Userid;
        }

        public void setUserid(int Userid) {
            this.Userid = Userid;
        }
    }
}
