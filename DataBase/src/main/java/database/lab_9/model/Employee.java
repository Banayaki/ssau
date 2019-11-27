package database.lab_9.model;

import java.sql.Date;

public class Employee {

    private Long num;
    private String fName;
    private Date bDay;
    private String gender;
    private String job;
    private Double wageRate;
    private Date sDate;
    private String address;

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public Date getbDay() {
        return bDay;
    }

    public void setbDay(Date bDay) {
        this.bDay = bDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Double getWageRate() {
        return wageRate;
    }

    public void setWageRate(Double wageRate) {
        this.wageRate = wageRate;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Department{" +
                "num=" + num +
                ", fName='" + fName + '\'' +
                ", bDay=" + bDay +
                ", gender='" + gender + '\'' +
                ", job='" + job + '\'' +
                ", wageRate=" + wageRate +
                ", sDate=" + sDate +
                ", address='" + address + '\'' +
                '}';
    }
}
