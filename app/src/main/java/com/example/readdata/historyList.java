package com.example.readdata;

import java.util.Date;

public class historyList {

    private String Date;
    private String Order0;
    private String Order1;
    private String Order2;
    private String Total;
    private String Status;

    public historyList() {
    }

    public historyList(String date, String order0, String order1, String order2, String total, String status) {
        Date = date;
        Order0 = order0;
        Order1 = order1;
        Order2 = order2;
        Total = total;
        Status = status;
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

}
