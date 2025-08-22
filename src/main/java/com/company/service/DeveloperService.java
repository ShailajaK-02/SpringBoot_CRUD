package com.company.service;

import com.company.entity.Developer;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface DeveloperService {

    //save dev
    String saveDeveloper(Developer developer);

    //get all dev
    List<Developer> getAllDev();

    //getdev by id
    Developer getDeveloperById(int id);

    //delete dev
    boolean deleteDev(int id);

    //update dev
    Developer updateDev(int id, Developer newData);

    //list of dev
    String saveListDev(List<Developer> developers);

    //filter by city
    List<Developer> filterByCity(String city);

    //filter by gender
    List<Developer> filterByGender(String gender);

    //filter by genderandcity
    List<Developer> filterByGenCity(String city, String gender);

    //save  Excel file data
    String saveDeveloperFromExcel(MultipartFile file);

    //Export data from database and add in excel
    ByteArrayInputStream exportDevelopersToExcel(int adminid) throws IOException;

    //get data by age
    List<Developer> getByAge(int age);

    //get dev greater than certain age
    List<Developer> maxAge(int age);

    //get by name
    Developer devByName(String fname);

}
