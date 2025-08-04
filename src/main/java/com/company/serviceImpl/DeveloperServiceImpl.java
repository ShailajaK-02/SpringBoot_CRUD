package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public String saveDeveloper(Developer developer) {
        Developer saveDeveloper = developerRepository.save(developer);
        return "developer saved";
    }

    @Override
    public List<Developer> getAllDev() {
       List<Developer> developerList =  developerRepository.findAll();
       return  developerList;
    }

    @Override
    public Developer getDeveloperById(int id) {
        Developer developer =  developerRepository.findById(id).orElseThrow(() -> new NullPointerException("Developer with id not found" +id));
        return developer;
    }

    @Override
    public String deleteDev(int id) {
        developerRepository.deleteById(id);
        return  "Developer deleted";
    }

    @Override
    public Developer updateDev(int id, Developer newData) {

        Developer developer = developerRepository.findById(id).orElseThrow(()->new NullPointerException(("Data not found"+ id)));

        developer.setFname(newData.getFname());
        developer.setAge(newData.getAge());
        developer.setCity(newData.getCity());
        developer.setLname(newData.getLname());
        developer.setSalary(newData.getSalary());

        Developer updatedDev = developerRepository.save(developer);
        return  updatedDev;
    }

    @Override
    public String saveListDev(List<Developer> developers) {
        List<Developer> developerList = developerRepository.saveAll(developers);
        return "List saved";
    }
}
