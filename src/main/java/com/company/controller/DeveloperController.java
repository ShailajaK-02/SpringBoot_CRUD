package com.company.controller;

import com.company.entity.Developer;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    //add single data
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Developer developer){
        System.err.println(developer);
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>("Developer added", HttpStatus.CREATED);
    }

    //get all data
    @GetMapping("/getAllData")
    public  ResponseEntity<List<Developer>> getAlldata(){
        List<Developer> developerList = developerService.getAllDev();
        return new ResponseEntity<>(developerList, HttpStatus.OK);
    }

    //get by id
    @GetMapping("/getById/{id}")
    public ResponseEntity<Developer> getById(@PathVariable("id") int id){
    Developer developer = developerService.getDeveloperById(id);
    return new ResponseEntity<>(developer, HttpStatus.OK);
    }

    //delete by id
    @DeleteMapping("/deleteById/{id}")
    public  ResponseEntity<String> deleteById(@PathVariable("id") int id){
        String msg = developerService.deleteDev(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    //update data by id
    @PutMapping("/updateDev/{id}")
    public ResponseEntity<Developer> updateDev(@PathVariable("id") int id, @RequestBody Developer developer){
        Developer updatedDev = developerService.updateDev(id,developer);
        return new ResponseEntity<>(updatedDev,HttpStatus.OK);
    }

    //api to save list of dev
    @PostMapping("/addListData")
    public ResponseEntity<String> addListData(@RequestBody List<Developer> developerList ){
        developerService.saveListDev(developerList);
        return new ResponseEntity<>("List saved",HttpStatus.OK);
    }
}
