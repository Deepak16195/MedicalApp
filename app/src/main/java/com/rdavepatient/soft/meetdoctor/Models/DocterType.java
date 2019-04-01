package com.rdavepatient.soft.meetdoctor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class DocterType {


    @Expose
    @SerializedName("DocterType")
    private List<DocterTypeEntity> DocterType;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Status")
    private int Status;

    public List<DocterTypeEntity> getDocterType() {
        return DocterType;
    }

    public void setDocterType(List<DocterTypeEntity> DocterType) {
        this.DocterType = DocterType;
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

    public static class DocterTypeEntity {
        @Expose
        @SerializedName("Description")
        private String Description;
        @Expose
        @SerializedName("DoctertypeName")
        private String DoctertypeName;
        @Expose
        @SerializedName("Doctertypeid")
        private int Doctertypeid;
        @Expose
        @SerializedName("IconPath")
        private String IconPath;
        @Expose
        @SerializedName("Icon")
        private String Icon;

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getDoctertypeName() {
            return DoctertypeName;
        }

        public void setDoctertypeName(String DoctertypeName) {
            this.DoctertypeName = DoctertypeName;
        }

        public int getDoctertypeid() {
            return Doctertypeid;
        }

        public void setDoctertypeid(int Doctertypeid) {
            this.Doctertypeid = Doctertypeid;
        }

        public String getIconPath() {
            return IconPath;
        }

        public void setIconPath(String IconPath) {
            this.IconPath = IconPath;
        }

        public String getIcon() {
            return Icon;
        }

        public void setIcon(String Icon) {
            this.Icon = Icon;
        }
    }
}
