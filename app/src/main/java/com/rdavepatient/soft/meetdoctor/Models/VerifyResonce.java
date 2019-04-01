package com.rdavepatient.soft.meetdoctor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class VerifyResonce {


    @Expose
    @SerializedName("User")
    private User User;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Status")
    private int Status;

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
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

    public static class User {
        @Expose
        @SerializedName("Timeing")
        private Timeing Timeing;
        @Expose
        @SerializedName("Profesion")
        private Profesion Profesion;
        @Expose
        @SerializedName("BasicDetails")
        private BasicDetails BasicDetails;

        public Timeing getTimeing() {
            return Timeing;
        }

        public void setTimeing(Timeing Timeing) {
            this.Timeing = Timeing;
        }

        public Profesion getProfesion() {
            return Profesion;
        }

        public void setProfesion(Profesion Profesion) {
            this.Profesion = Profesion;
        }

        public BasicDetails getBasicDetails() {
            return BasicDetails;
        }

        public void setBasicDetails(BasicDetails BasicDetails) {
            this.BasicDetails = BasicDetails;
        }
    }

    public static class Timeing {
        @Expose
        @SerializedName("Slot")
        private String Slot;
        @Expose
        @SerializedName("UserId")
        private int UserId;
        @Expose
        @SerializedName("Days")
        private String Days;
        @Expose
        @SerializedName("EveningTo")
        private String EveningTo;
        @Expose
        @SerializedName("MornigTo")
        private String MornigTo;
        @Expose
        @SerializedName("EveningFrom")
        private String EveningFrom;
        @Expose
        @SerializedName("MornigFrom")
        private String MornigFrom;

        public String getSlot() {
            return Slot;
        }

        public void setSlot(String Slot) {
            this.Slot = Slot;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getDays() {
            return Days;
        }

        public void setDays(String Days) {
            this.Days = Days;
        }

        public String getEveningTo() {
            return EveningTo;
        }

        public void setEveningTo(String EveningTo) {
            this.EveningTo = EveningTo;
        }

        public String getMornigTo() {
            return MornigTo;
        }

        public void setMornigTo(String MornigTo) {
            this.MornigTo = MornigTo;
        }

        public String getEveningFrom() {
            return EveningFrom;
        }

        public void setEveningFrom(String EveningFrom) {
            this.EveningFrom = EveningFrom;
        }

        public String getMornigFrom() {
            return MornigFrom;
        }

        public void setMornigFrom(String MornigFrom) {
            this.MornigFrom = MornigFrom;
        }
    }

    public static class Profesion {
        @Expose
        @SerializedName("UserId")
        private int UserId;
        @Expose
        @SerializedName("PostalCode")
        private String PostalCode;
        @Expose
        @SerializedName("Contry")
        private String Contry;
        @Expose
        @SerializedName("State")
        private String State;
        @Expose
        @SerializedName("City")
        private String City;
        @Expose
        @SerializedName("Latitude")
        private String Latitude;
        @Expose
        @SerializedName("Longitude")
        private String Longitude;
        @Expose
        @SerializedName("MapAddress")
        private String MapAddress;
        @Expose
        @SerializedName("Address")
        private String Address;
        @Expose
        @SerializedName("SpecialisationName")
        private String SpecialisationName;
        @Expose
        @SerializedName("SpecialisationId")
        private int SpecialisationId;
        @Expose
        @SerializedName("DoctertypeName")
        private String DoctertypeName;
        @Expose
        @SerializedName("DoctertypeId")
        private int DoctertypeId;
        @Expose
        @SerializedName("ClincName")
        private String ClincName;

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getPostalCode() {
            return PostalCode;
        }

        public void setPostalCode(String PostalCode) {
            this.PostalCode = PostalCode;
        }

        public String getContry() {
            return Contry;
        }

        public void setContry(String Contry) {
            this.Contry = Contry;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }

        public String getMapAddress() {
            return MapAddress;
        }

        public void setMapAddress(String MapAddress) {
            this.MapAddress = MapAddress;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getSpecialisationName() {
            return SpecialisationName;
        }

        public void setSpecialisationName(String SpecialisationName) {
            this.SpecialisationName = SpecialisationName;
        }

        public int getSpecialisationId() {
            return SpecialisationId;
        }

        public void setSpecialisationId(int SpecialisationId) {
            this.SpecialisationId = SpecialisationId;
        }

        public String getDoctertypeName() {
            return DoctertypeName;
        }

        public void setDoctertypeName(String DoctertypeName) {
            this.DoctertypeName = DoctertypeName;
        }

        public int getDoctertypeId() {
            return DoctertypeId;
        }

        public void setDoctertypeId(int DoctertypeId) {
            this.DoctertypeId = DoctertypeId;
        }

        public String getClincName() {
            return ClincName;
        }

        public void setClincName(String ClincName) {
            this.ClincName = ClincName;
        }
    }

    public static class BasicDetails {
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
