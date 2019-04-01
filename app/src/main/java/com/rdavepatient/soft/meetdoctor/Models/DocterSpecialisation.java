package com.rdavepatient.soft.meetdoctor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class DocterSpecialisation {


    @Expose
    @SerializedName("Specialisation")
    private List<Specialisation> Specialisation;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Status")
    private int Status;

    public List<Specialisation> getSpecialisation() {
        return Specialisation;
    }

    public void setSpecialisation(List<Specialisation> Specialisation) {
        this.Specialisation = Specialisation;
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

    public static class Specialisation {
        @Expose
        @SerializedName("Description")
        private String Description;
        @Expose
        @SerializedName("SpecialisationName")
        private String SpecialisationName;
        @Expose
        @SerializedName("SpecialisationId")
        private int SpecialisationId;
        @Expose
        @SerializedName("IconPath")
        private String IconPath;

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
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

        public String getIconPath() {
            return IconPath;
        }

        public void setIconPath(String IconPath) {
            this.IconPath = IconPath;
        }
    }
}
