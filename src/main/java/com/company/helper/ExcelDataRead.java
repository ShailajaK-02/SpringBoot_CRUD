package com.company.helper;

import com.company.entity.Developer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Excel upload
public class ExcelDataRead {

    //check the file is of excel type or not method
    public static boolean checkExcelFormat(MultipartFile file){

        String contentType = file.getContentType();
        if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        }
        else {
            return false;
        }
    }

    //This method converts excel into list of dev
    public static List<Developer> convertExcelToListOfDev(InputStream is){

        List<Developer> list = new ArrayList<>();
        try{

            XSSFWorkbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0); //it will read the 1st sheet from your excel

            int rowNumber = 0;
            Iterator<Row> rows = sheet.iterator();

            while (rows.hasNext()){

               Row currentRow = rows.next();

               //skips the header row
               if(rowNumber == 0){
                    rowNumber++;
                    continue;
               }

                Developer dev = new Developer();

                dev.setFname(currentRow.getCell(0).getStringCellValue());
                dev.setLname(currentRow.getCell(1).getStringCellValue());
                dev.setAge((int) currentRow.getCell(2).getNumericCellValue());
                dev.setCity(currentRow.getCell(3).getStringCellValue());
                dev.setSalary((long) currentRow.getCell(4).getNumericCellValue());
                dev.setGender(currentRow.getCell(5).getStringCellValue());
                //dev.setYob((int) currentRow.getCell(6).getNumericCellValue());

                list.add(dev);
            }
            workbook.close();
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }

        return list;
    }
}
