package database.lab_9;

import database.lab_9.model.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;


@SpringBootApplication
public class Lab9Application {


    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/application-context.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");


        List<Map<String, Object>> allEmployees =
                jdbcTemplate.queryForList("select * from employees emp join job_history jh on emp.num = jh.num");
x
        System.out.println(allEmployees);
    }

}