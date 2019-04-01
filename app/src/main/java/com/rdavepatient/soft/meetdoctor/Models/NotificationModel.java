package com.rdavepatient.soft.meetdoctor.Models;

import java.util.ArrayList;

/**
 * Created by user on 01-12-2017.
 */

public class NotificationModel {


    /**
     * Status : 200
     * Message : Svae Successfully!
     * notification : [{"NotificationId":39,"To_user":4,"from_user":1,"postid":6,"notification":"Deepak Jaiswal  has commented on your Post ","createdon":"Oct 23 2018 11:10AM","status":0,"NotificType":"C         ","Imageurl":"http://comapi.mdsoftech.in/Profile/1636758359736380823.jpg","row_id":1,"total_rows":2,"fromUser_Name":"Deepak Jaiswal"},{"NotificationId":40,"To_user":4,"from_user":1,"postid":6,"notification":"Deepak Jaiswal  has commented on your Post ","createdon":"Oct 23 2018 11:10AM","status":0,"NotificType":"C         ","Imageurl":"http://comapi.mdsoftech.in/Profile/1636758359736380823.jpg","row_id":2,"total_rows":2,"fromUser_Name":"Deepak Jaiswal"}]
     */

    private int Status;
    private String Message;
    private ArrayList<NotificationBean> notification;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public ArrayList<NotificationBean> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<NotificationBean> notification) {
        this.notification = notification;
    }

    public static class NotificationBean {
        /**
         * NotificationId : 39
         * To_user : 4
         * from_user : 1
         * postid : 6
         * notification : Deepak Jaiswal  has commented on your Post
         * createdon : Oct 23 2018 11:10AM
         * status : 0
         * NotificType : C
         * Imageurl : http://comapi.mdsoftech.in/Profile/1636758359736380823.jpg
         * row_id : 1
         * total_rows : 2
         * fromUser_Name : Deepak Jaiswal
         */

        private int NotificationId;
        private int To_user;
        private int from_user;
        private int postid;
        private String notification;
        private String createdon;
        private int status;
        private String NotificType;
        private String Imageurl;
        private int row_id;
        private int total_rows;
        private String fromUser_Name;

        public int getNotificationId() {
            return NotificationId;
        }

        public void setNotificationId(int NotificationId) {
            this.NotificationId = NotificationId;
        }

        public int getTo_user() {
            return To_user;
        }

        public void setTo_user(int To_user) {
            this.To_user = To_user;
        }

        public int getFrom_user() {
            return from_user;
        }

        public void setFrom_user(int from_user) {
            this.from_user = from_user;
        }

        public int getPostid() {
            return postid;
        }

        public void setPostid(int postid) {
            this.postid = postid;
        }

        public String getNotification() {
            return notification;
        }

        public void setNotification(String notification) {
            this.notification = notification;
        }

        public String getCreatedon() {
            return createdon;
        }

        public void setCreatedon(String createdon) {
            this.createdon = createdon;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getNotificType() {
            return NotificType;
        }

        public void setNotificType(String NotificType) {
            this.NotificType = NotificType;
        }

        public String getImageurl() {
            return Imageurl;
        }

        public void setImageurl(String Imageurl) {
            this.Imageurl = Imageurl;
        }

        public int getRow_id() {
            return row_id;
        }

        public void setRow_id(int row_id) {
            this.row_id = row_id;
        }

        public int getTotal_rows() {
            return total_rows;
        }

        public void setTotal_rows(int total_rows) {
            this.total_rows = total_rows;
        }

        public String getFromUser_Name() {
            return fromUser_Name;
        }

        public void setFromUser_Name(String fromUser_Name) {
            this.fromUser_Name = fromUser_Name;
        }
    }
}
