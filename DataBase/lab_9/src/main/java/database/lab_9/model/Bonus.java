package database.lab_9.model;

public class Bonus {

    private Long num;
    private Integer rise;

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Integer getRise() {
        return rise;
    }

    public void setRise(Integer rise) {
        this.rise = rise;
    }

    @Override
    public String toString() {
        return "Bonus{" +
                "num=" + num +
                ", rise=" + rise +
                '}';
    }
}
