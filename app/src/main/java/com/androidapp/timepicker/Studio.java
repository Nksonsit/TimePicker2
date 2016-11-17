package com.androidapp.timepicker;

/**
 * Created by ISHAN on 11/12/2016.
 */
public class Studio {
    String id;
    String date;
    String stime;
    String etime;
    String emaild;
    String cno;
    String day;
    String month;
    String year;

    public Studio(String stime, String etime) {
        this.stime = stime;
        this.etime = etime;
    }

    public Studio() {
    }

    @Override
    public String toString() {
        return "Studio{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", stime='" + stime + '\'' +
                ", etime='" + etime + '\'' +
                ", emaild='" + emaild + '\'' +
                ", cno='" + cno + '\'' +
                ", day='" + day + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getEmaild() {
        return emaild;
    }

    public void setEmaild(String emaild) {
        this.emaild = emaild;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Studio(String id, String date, String stime, String etime, String emaild, String cno, String day, String month, String year) {

        this.id = id;
        this.date = date;
        this.stime = stime;
        this.etime = etime;
        this.emaild = emaild;
        this.cno = cno;
        this.day = day;
        this.month = month;
        this.year = year;
    }
}
