package com.company.service;

import com.company.entity.Developer;

import java.util.List;

public interface DeveloperService {

    String saveDeveloper(Developer developer);

    List<Developer> getAllDev();

    Developer getDeveloperById(int id);

    String deleteDev(int id);

    Developer updateDev(int id, Developer newData);

    String saveListDev(List<Developer> developers);
}
