package com.example.readdata;

public class AdminHistory {

    private String Date;
    private String Order0;
    private String Order1;
    private String Order2;
    private String Total;
    private String Status;
    private String User;
    private boolean isSelected;
    private String UserId;
    private String UUID;

    public AdminHistory(String UUID1,String date, String order0, String order1, String order2, String total, String status, String user, boolean isSelected, String id) {
        Date = date;
        Order0 = order0;
        Order1 = order1;
        Order2 = order2;
        Total = total;
        Status = status;
        User = user;
        UserId=id;
        UUID=UUID1;
        this.isSelected = isSelected;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setOrder0(String order0) {
        Order0 = order0;
    }

    public void setOrder1(String order1) {
        Order1 = order1;
    }

    public void setOrder2(String order2) {
        Order2 = order2;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getDate() {
        return Date;
    }

    public String getOrder0() {
        return Order0;
    }

    public String getOrder1() {
        return Order1;
    }

    public String getOrder2() {
        return Order2;
    }

    public String getTotal() {
        return Total;
    }

    public String getStatus() {
        return Status;
    }

    public AdminHistory() {
    }

    public String getUser() {
        return User;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
