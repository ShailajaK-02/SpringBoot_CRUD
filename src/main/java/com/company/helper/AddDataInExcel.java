package com.company.helper;

import com.company.entity.Developer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
public class AddDataInExcel {

   //method to get data from db
    public static ByteArrayInputStream developersToExcel(List<Developer> developers) throws IOException {
        String[] columns = {"ID", "First name", "Last name", "Age", "City", "YearofBirth", "Dev id", "Gender", "Salary"};

        try {
            //create workbook i.e create excel file
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            //create sheet
            Sheet sheet = workbook.createSheet("DeveloperData");

            //create row header row
            Row row = sheet.createRow(0);

            //create cell
            for (int i = 0; i < columns.length; i++) {

                Cell cell = row.createCell(i);
                cell.setCellValue(columns[i]);
            }

            //Add data rows
            int rowIdx = 1;
            for (Developer dev : developers) {
                Row row1 = sheet.createRow(rowIdx++);

                row1.createCell(0).setCellValue(dev.getId());
                row1.createCell(1).setCellValue(dev.getFname());
                row1.createCell(2).setCellValue(dev.getLname());
                row1.createCell(3).setCellValue(dev.getAge());
                row1.createCell(4).setCellValue(dev.getCity());
                row1.createCell(5).setCellValue(dev.getDob());
                row1.createCell(6).setCellValue(dev.getDevloperId());
                row1.createCell(7).setCellValue(dev.getGender());
                row1.createCell(8).setCellValue(dev.getSalary());

            }
            //write data in output stream
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        }catch (IOException e) {
            e.printStackTrace();
            throw e; // rethrow if needed
        }
    }
}
