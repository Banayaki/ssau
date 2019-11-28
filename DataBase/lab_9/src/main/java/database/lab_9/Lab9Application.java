package database.lab_9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;


@SpringBootApplication
public class Lab9Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/application-context.xml");
        ArgParser argParser = (ArgParser) ctx.getBean("argParser");

        argParser.parse(args);
    }
}