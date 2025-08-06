package com.company.service;

import com.company.entity.Developer;

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

    List<Developer> filterByGender(String gender);

    List<Developer> filterByGenCity(String city, String gender);

}
