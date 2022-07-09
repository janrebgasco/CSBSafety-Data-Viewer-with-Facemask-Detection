package com.ucc.csbsafetydataviewer;

public class User {
    public String LName;
    public String FName;
    public String status;
    public String userImg;
    public String Usertype;
    public String StudentNum;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String LName, String FName,String status,String userImg,String StudentNum, String Usertype) {
        this.LName = LName;
        this.FName = FName;
        this.status = status;
        this.userImg = userImg;
        this.StudentNum = StudentNum;
        this.Usertype = Usertype;
    }
}
