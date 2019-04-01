package com.rdavepatient.soft.meetdoctor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class GetSlot {


    @Expose
    @SerializedName("Appointments")
    private AppointmentsEntity Appointments;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Status")
    private int Status;

    public AppointmentsEntity getAppointments() {
        return Appointments;
    }

    public void setAppointments(AppointmentsEntity Appointments) {
        this.Appointments = Appointments;
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

    public static class AppointmentsEntity {
        @Expose
        @SerializedName("TimeSlot")
        private TimeSlotEntity TimeSlot;
        @Expose
        @SerializedName("SlotList")
        private List<SlotListEntity> SlotList;

        public TimeSlotEntity getTimeSlot() {
            return TimeSlot;
        }

        public void setTimeSlot(TimeSlotEntity TimeSlot) {
            this.TimeSlot = TimeSlot;
        }

        public List<SlotListEntity> getSlotList() {
            return SlotList;
        }

        public void setSlotList(List<SlotListEntity> SlotList) {
            this.SlotList = SlotList;
        }
    }

    public static class TimeSlotEntity {
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

    public static class SlotListEntity {
        @Expose
        @SerializedName("Status")
        private boolean Status;
        @Expose
        @SerializedName("Slot")
        private String Slot;

        public boolean getStatus() {
            return Status;
        }

        public void setStatus(boolean Status) {
            this.Status = Status;
        }

        public String getSlot() {
            return Slot;
        }

        public void setSlot(String Slot) {
            this.Slot = Slot;
        }
    }
}
