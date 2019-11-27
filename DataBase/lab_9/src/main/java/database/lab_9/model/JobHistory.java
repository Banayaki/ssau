package database.lab_9.model;

import java.io.Serializable;
import java.sql.Date;

public class JobHistory implements Serializable {

    private Long num;
    private Date startDate;
    private Date endDate;
    private String job;
    private Integer numDepartment;

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getNumDepartment() {
        return numDepartment;
    }

    public void setNumDepartment(Integer numDepartment) {
        this.numDepartment = numDepartment;
    }

    @Override
    public String toString() {
        return "JobHistory{" +
                "num=" + num +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", job='" + job + '\'' +
                ", numDepartment=" + numDepartment +
                '}';
    }
}
