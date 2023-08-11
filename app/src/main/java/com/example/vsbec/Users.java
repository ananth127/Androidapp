package com.example.vsbec;

public class Users {
    String rollno;
    String profilepic,name,usersid,password,userId,lastMessage,status,lastlogin,certificate,batch_start,batch_end,department,mobileno;

    public Users(){

    }


    public Users(String userId, String name, String usersid,String rollno, String password, String profilepic, String status, String batch_start, String batch_end, String department, String mobileno){
        this.userId = userId;
        this.name =name ;
        this.usersid = usersid;
        this.password = password;
        this.profilepic = profilepic;
        this.batch_end=batch_end;
        this.batch_start=batch_start;
        this.department=department;
        this.mobileno=mobileno;
        this.status = status;
        this.rollno=rollno;

    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsersid() {
        return usersid;
    }

    public void setUsersid(String usersid) {
        this.usersid = usersid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getBatch_start() {
        return batch_start;
    }

    public void setBatch_start(String batch_start) {
        this.batch_start = batch_start;
    }

    public String getBatch_end() {
        return batch_end;
    }

    public void setBatch_end(String batch_end) {
        this.batch_end = batch_end;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}
