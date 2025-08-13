package com.company.service;

import com.company.entity.Developer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DeveloperService {

    //save dev
    String saveDeveloper(Developer developer);

    //get all dev
    List<Developer> getAllDev();

    //getdev by id
    Developer getDeveloperById(int id);

    //delete dev
    String deleteDev(int id);

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

}
