package com.rdavepatient.soft.meetdoctor.Models;

public class BookDocter {


    /**
     * AppointmentId : 1
     * DoctorId : 2
     * DoctorName : sample string 3
     * PatientId : 4
     * PatientName : sample string 5
     * AppointmentDate : sample string 6
     * AppointmentTime : sample string 7
     * Reason : sample string 8
     * AppointmentStatusId : 9
     * AppointmentStatusName : sample string 10
     */

    private int AppointmentId;
    private int DoctorId;
    private String DoctorName;
    private int PatientId;
    private String PatientName;
    private String AppointmentDate;
    private String AppointmentTime;
    private String Reason;
    private int AppointmentStatusId;
    private String AppointmentStatusName;

    public int getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(int AppointmentId) {
        this.AppointmentId = AppointmentId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int DoctorId) {
        this.DoctorId = DoctorId;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int PatientId) {
        this.PatientId = PatientId;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String AppointmentDate) {
        this.AppointmentDate = AppointmentDate;
    }

    public String getAppointmentTime() {
        return AppointmentTime;
    }

    public void setAppointmentTime(String AppointmentTime) {
        this.AppointmentTime = AppointmentTime;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public int getAppointmentStatusId() {
        return AppointmentStatusId;
    }

    public void setAppointmentStatusId(int AppointmentStatusId) {
        this.AppointmentStatusId = AppointmentStatusId;
    }

    public String getAppointmentStatusName() {
        return AppointmentStatusName;
    }

    public void setAppointmentStatusName(String AppointmentStatusName) {
        this.AppointmentStatusName = AppointmentStatusName;
    }
}
