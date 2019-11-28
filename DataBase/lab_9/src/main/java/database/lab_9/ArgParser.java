package database.lab_9;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class ArgParser {

    private static final String SELECT_ALL_EMP = "emp";
    private static final String SELECT_ALL_EMP_JH = "emp_jh";
    private static final String UPDATE_NUM_JOB = "upd_num_job";

    private DatabaseService service;

    public void setService(DatabaseService service) {
        this.service = service;
    }

    public void parse(String[] args) {
        try {
            if (args.length == 0)
                throw new IllegalArgumentException("Illegal arguments");

            switch (args[0].toLowerCase()) {
                case SELECT_ALL_EMP:
                    selectAllEmp(args[1]);
                    break;
                case SELECT_ALL_EMP_JH:
                    selectAllEmpAndJh(args[1]);
                    break;
                case UPDATE_NUM_JOB:
                    updateNumJob(args[1], args[2]);
                    break;
                default:
                    throw new IllegalArgumentException("Illegal argument (args[0])");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void selectAllEmp(String toExcelPath) throws IOException {
        List queryResult = service.selectAllEmp();
        createExcelTable(queryResult, toExcelPath);
    }

    private void selectAllEmpAndJh(String toExcelPath) throws IOException {
        List queryResult = service.selectAllEmpAllInfo();
        createExcelTable(queryResult, toExcelPath);
    }

    private void updateNumJob(String num, String job) {
        System.out.println("Updated rows = " + service.updateEmpJob(job, Integer.parseInt(num)));
    }

    private void createExcelTable(List<Map> queryResult, String toExcelPath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");
        int rowsCounter = 0;

        for (Map selectRow : queryResult) {
            Row row = sheet.createRow(rowsCounter++);
            AtomicInteger i = new AtomicInteger(0);
            selectRow.forEach((k, v) -> {
                Cell cell = row.createCell(i.getAndIncrement());
                if (v == null)
                    cell.setCellValue("Still working");
                else
                    cell.setCellValue(v.toString());
            });
        }

        workbook.write(new FileOutputStream(new File(toExcelPath)));
        workbook.close();
    }
}
