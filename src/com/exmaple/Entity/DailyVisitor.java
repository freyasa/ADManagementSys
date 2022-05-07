package com.exmaple.Entity;

import java.util.Date;

public class DailyVisitor implements BaseEntity{
    private int recordId;
    private String webpage;
    private Date date;
    private int visitor;

    public DailyVisitor() {
    }

    public DailyVisitor(int recordId, String webpage, Date date, int visitor) {
        this.recordId = recordId;
        this.webpage = webpage;
        this.date = date;
        this.visitor = visitor;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }
}
