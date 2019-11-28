package database.lab_9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public class DatabaseService {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_ALL_EMP_ALL_INFO =
            "select emp.NUM, emp.FNAME, emp.NUM, emp.FNAME, emp.BDAY, emp.GENDER, emp.WAGE_RATE, emp.SDATE, " +
                    "emp.ADDRESS, jh.START_DATE, jh.END_DATE, jh.JOB, jh.NUM_DEPARTMENT " +
                    "from EMPLOYEES emp join JOB_HISTORY jh on emp.num = jh.num";
    private final String SQL_SELECT_ALL_EMP = "select * from EMPLOYEES";
    private final String SQL_UPDATE_EMP_JOB = "update EMPLOYEES set job = ? where num = ?";

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> selectAllEmpAllInfo() {
        return jdbcTemplate.queryForList(SQL_SELECT_ALL_EMP_ALL_INFO);
    }

    public List<Map<String, Object>> selectAllEmp() {
        return jdbcTemplate.queryForList(SQL_SELECT_ALL_EMP);
    }

    public int updateEmpJob(String job, int num) {
        Object[] params = new Object[]{job, num};
        return jdbcTemplate.update(SQL_UPDATE_EMP_JOB, params);
    }
}
