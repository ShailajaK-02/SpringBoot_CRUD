package com.company.schedulers;

import com.company.entity.Developer;
import com.company.helper.GenerateDeveloperId;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleSchedulers {
//
    @Autowired
    private DeveloperRepository repository;

    @Autowired
    private GenerateDeveloperId genId;

//    //simple method to print message after 5 sec
//    @Scheduled(fixedDelay = 5000)
//    public void simpleprint(){
//        System.err.println("Today is friday");
//    }

    // create a schedular to check if developer_id is missing
    //if it is missing then create again and store it in database
    //use native query to update field

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkDevIdMissing(){
        //call findDevmiss id to get list of dev whose id is missing and store it
        List<Developer> devList = repository.findDevWithMissId();

        //loop to generate id
        for(Developer d : devList){
            //we can reuse generatedevid logic here by injecting service in this class
            String devId = genId.generateId(d);
            repository.updateIfDevIdMiss(d.getId(),devId);
        }
    }
}
