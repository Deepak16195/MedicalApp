package com.rdavepatient.soft.meetdoctor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Appointment {


    @Expose
    @SerializedName("AppointmentStatusName")
    private String AppointmentStatusName;
    @Expose
    @SerializedName("AppointmentStatusId")
    private int AppointmentStatusId;
    @Expose
    @SerializedName("Reason")
    private String Reason;
    @Expose
    @SerializedName("AppointmentTime")
    private String AppointmentTime;
    @Expose
    @SerializedName("AppointmentDate")
    private String AppointmentDate;
    @Expose
    @SerializedName("PatientName")
    private String PatientName;
    @Expose
    @SerializedName("Vist")
    private int Vist;
    @Expose
    @SerializedName("Age")
    private int Age;
    @Expose
    @SerializedName("PatientId")
    private int PatientId;
    @Expose
    @SerializedName("PatientAddress")
    private String PatientAddress;
    @Expose
    @SerializedName("DocterAddress")
    private String DocterAddress;
    @Expose
    @SerializedName("DoctorName")
    private String DoctorName;
    @Expose
    @SerializedName("DoctorId")
    private int DoctorId;
    @Expose
    @SerializedName("AppointmentId")
    private int AppointmentId;

    public String getAppointmentStatusName() {
        return AppointmentStatusName;
    }

    public void setAppointmentStatusName(String AppointmentStatusName) {
        this.AppointmentStatusName = AppointmentStatusName;
    }

    public int getAppointmentStatusId() {
        return AppointmentStatusId;
    }

    public void setAppointmentStatusId(int AppointmentStatusId) {
        this.AppointmentStatusId = AppointmentStatusId;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public String getAppointmentTime() {
        return AppointmentTime;
    }

    public void setAppointmentTime(String AppointmentTime) {
        this.AppointmentTime = AppointmentTime;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String AppointmentDate) {
        this.AppointmentDate = AppointmentDate;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public int getVist() {
        return Vist;
    }

    public void setVist(int Vist) {
        this.Vist = Vist;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int PatientId) {
        this.PatientId = PatientId;
    }

    public String getPatientAddress() {
        return PatientAddress;
    }

    public void setPatientAddress(String PatientAddress) {
        this.PatientAddress = PatientAddress;
    }

    public String getDocterAddress() {
        return DocterAddress;
    }

    public void setDocterAddress(String DocterAddress) {
        this.DocterAddress = DocterAddress;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int DoctorId) {
        this.DoctorId = DoctorId;
    }

    public int getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(int AppointmentId) {
        this.AppointmentId = AppointmentId;
    }
}
