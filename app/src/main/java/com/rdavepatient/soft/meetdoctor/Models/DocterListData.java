package com.rdavepatient.soft.meetdoctor.Models;

import java.util.List;

public class DocterListData {


    /**
     * Status : 200
     * Message : Success
     * Docter : [{"Userid":1,"Rate":3,"UserName":"Deepak Jaiswal","StartTime":null,"EndTime":null,"Adress":"A408 mo Dahisar station","ProfileImage":null,"MobileNo":"7977805760","Fees":500,"Gender":"M","Specialisation":[{"Icon":null,"IconPath":null,"SpecialisationId":1,"SpecialisationName":"Anesthesiologists ","Description":null,"IsSet":true},{"Icon":null,"IconPath":null,"SpecialisationId":2,"SpecialisationName":"Cardiologists ","Description":null,"IsSet":true}],"EmailId":"deepak16195@gmail.com"}]
     */

    private int Status;
    private String Message;
    private List<DocterBean> Docter;

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

    public List<DocterBean> getDocter() {
        return Docter;
    }

    public void setDocter(List<DocterBean> Docter) {
        this.Docter = Docter;
    }

    public static class DocterBean {
        /**
         * Userid : 1
         * Rate : 3
         * UserName : Deepak Jaiswal
         * StartTime : null
         * EndTime : null
         * Adress : A408 mo Dahisar station
         * ProfileImage : null
         * MobileNo : 7977805760
         * Fees : 500
         * Gender : M
         * Specialisation : [{"Icon":null,"IconPath":null,"SpecialisationId":1,"SpecialisationName":"Anesthesiologists ","Description":null,"IsSet":true},{"Icon":null,"IconPath":null,"SpecialisationId":2,"SpecialisationName":"Cardiologists ","Description":null,"IsSet":true}]
         * EmailId : deepak16195@gmail.com
         */

        private int Userid;
        private int Rate;
        private String UserName;
        private Object StartTime;
        private Object EndTime;
        private String Adress;
        private Object ProfileImage;
        private String MobileNo;
        private int Fees;
        private int Exprience;
        private String Gender;
        private String EmailId;

        private List<SpecialisationBean> Specialisation;

        public int getUserid() {
            return Userid;
        }

        public int getExprience() {
            return Exprience;
        }

        public void setExprience(int exprience) {
            Exprience = exprience;
        }

        public void setUserid(int Userid) {
            this.Userid = Userid;
        }

        public int getRate() {
            return Rate;
        }

        public void setRate(int Rate) {
            this.Rate = Rate;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public Object getStartTime() {
            return StartTime;
        }

        public void setStartTime(Object StartTime) {
            this.StartTime = StartTime;
        }

        public Object getEndTime() {
            return EndTime;
        }

        public void setEndTime(Object EndTime) {
            this.EndTime = EndTime;
        }

        public String getAdress() {
            return Adress;
        }

        public void setAdress(String Adress) {
            this.Adress = Adress;
        }

        public Object getProfileImage() {
            return ProfileImage;
        }

        public void setProfileImage(Object ProfileImage) {
            this.ProfileImage = ProfileImage;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public int getFees() {
            return Fees;
        }

        public void setFees(int Fees) {
            this.Fees = Fees;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public String getEmailId() {
            return EmailId;
        }

        public void setEmailId(String EmailId) {
            this.EmailId = EmailId;
        }

        public List<SpecialisationBean> getSpecialisation() {
            return Specialisation;
        }

        public void setSpecialisation(List<SpecialisationBean> Specialisation) {
            this.Specialisation = Specialisation;
        }

        public static class SpecialisationBean {
            /**
             * Icon : null
             * IconPath : null
             * SpecialisationId : 1
             * SpecialisationName : Anesthesiologists
             * Description : null
             * IsSet : true
             */

            private Object Icon;
            private Object IconPath;
            private int SpecialisationId;
            private String SpecialisationName;
            private Object Description;
            private boolean IsSet;

            public Object getIcon() {
                return Icon;
            }

            public void setIcon(Object Icon) {
                this.Icon = Icon;
            }

            public Object getIconPath() {
                return IconPath;
            }

            public void setIconPath(Object IconPath) {
                this.IconPath = IconPath;
            }

            public int getSpecialisationId() {
                return SpecialisationId;
            }

            public void setSpecialisationId(int SpecialisationId) {
                this.SpecialisationId = SpecialisationId;
            }

            public String getSpecialisationName() {
                return SpecialisationName;
            }

            public void setSpecialisationName(String SpecialisationName) {
                this.SpecialisationName = SpecialisationName;
            }

            public Object getDescription() {
                return Description;
            }

            public void setDescription(Object Description) {
                this.Description = Description;
            }

            public boolean isIsSet() {
                return IsSet;
            }

            public void setIsSet(boolean IsSet) {
                this.IsSet = IsSet;
            }
        }
    }
}
