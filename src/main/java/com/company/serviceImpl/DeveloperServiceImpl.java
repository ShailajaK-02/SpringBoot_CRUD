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
}
