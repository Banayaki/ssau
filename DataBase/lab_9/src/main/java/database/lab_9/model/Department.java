package database.lab_9.model;

import java.io.Serializable;
import java.sql.Date;

public class Department implements Serializable {

    private Integer numDepartment;
    private String property;

    public Integer getNumDepartment() {
        return numDepartment;
    }

    public void setNumDepartment(Integer numDepartment) {
        this.numDepartment = numDepartment;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Department{" +
                "numDepartment=" + numDepartment +
                ", property='" + property + '\'' +
                '}';
    }
}