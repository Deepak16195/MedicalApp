package com.rdavepatient.soft.meetdoctor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Registers {


    @Expose
    @SerializedName("user")
    private UserEntity user;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Status")
    private int Status;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public static  class UserEntity {


        @Expose
        @SerializedName("BloodGroup")
        private String BloodGroup;
        @Expose
        @SerializedName("Gender")
        private String Gender;
        @Expose
        @SerializedName("DOB")
        private String DOB;
        @Expose
        @SerializedName("Password")
        private String Password;
        @Expose
        @SerializedName("Email")
        private String Email;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("UserTypeName")
        private int UserTypeName;
        @Expose
        @SerializedName("UserType")
        private int UserType;
        @Expose
        @SerializedName("UserId")
        private int UserId;
        @Expose
        @SerializedName("LastName")
        private String LastName;
        @Expose
        @SerializedName("FirstName")
        private String FirstName;
        @Expose
        @SerializedName("UserName")
        private String UserName;

        public String getBloodGroup() {
            return BloodGroup;
        }

        public void setBloodGroup(String BloodGroup) {
            this.BloodGroup = BloodGroup;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public int getUserTypeName() {
            return UserTypeName;
        }

        public void setUserTypeName(int UserTypeName) {
            this.UserTypeName = UserTypeName;
        }

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int UserType) {
            this.UserType = UserType;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String LastName) {
            this.LastName = LastName;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String FirstName) {
            this.FirstName = FirstName;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }
    }
}
