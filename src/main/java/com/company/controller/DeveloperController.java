package com.company.controller;

import com.company.entity.Developer;
import com.company.service.AdminService;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
         if(developer==null){
             return ResponseEntity.badRequest().body("Developer data is missing");
         }
         String msg = developerService.saveDeveloper(developer);
         return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    //get all data
    @GetMapping("/getAllData")
    public  ResponseEntity<List<Developer>> getAlldata(){
        List<Developer> developerList = developerService.getAllDev();

        if (developerList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(developerList, HttpStatus.OK);
    }

    //get by id
    @GetMapping("/getById/{id}")
    public ResponseEntity<Developer> getById(@PathVariable("id") int id){
    Developer developer = developerService.getDeveloperById(id);

    if(developer == null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return new ResponseEntity<>(developer, HttpStatus.OK);
    }

    //delete by id
    @DeleteMapping("/deleteById/{id}")
    public  ResponseEntity<String> deleteById(@PathVariable("id") int id){
       boolean deleted = Boolean.parseBoolean(developerService.deleteDev(id));
       if(!deleted){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Developer not found");
       }
        return ResponseEntity.ok("Deleted successfully");
    }

    //update data by id
    @PutMapping("/updateDev/{id}")
    public ResponseEntity<Developer> updateDev(@PathVariable("id") int id, @RequestBody Developer developer){
        Developer updatedDev = developerService.updateDev(id,developer);
        if(updatedDev == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(updatedDev,HttpStatus.OK);
    }

    //api to save list of dev
    @PostMapping("/addListData")
    public ResponseEntity<String> addListData(@RequestBody List<Developer> developerList ){
        developerService.saveListDev(developerList);
        return new ResponseEntity<>("List saved",HttpStatus.OK);
    }

    //api to filter by city , filter by gender.
    @GetMapping("/filter")
    public ResponseEntity<List<Developer>> filterByCity(@RequestParam(required = false) String city,@RequestParam(required = false) String gender){

        List<Developer> sortedList = new ArrayList<>();

        if (city != null && gender != null) {
            sortedList = developerService.filterByGenCity(city, gender);
        }
        else if(gender != null) {
            sortedList = developerService.filterByGender(gender);
        }
        else if(city != null) {
            sortedList = developerService.filterByCity(city);
        }
        else {
             sortedList = developerService.getAllDev();
        }

        if(sortedList.isEmpty()){
            String msg = "No data is found with the city :"+city +" and Gender "+gender;
            return new ResponseEntity(msg,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(sortedList,HttpStatus.OK);
    }

    //Api for uploading excel file and saving it in database
    @PostMapping(value = "/uploadExcel", consumes = "multipart/form-data")
    public ResponseEntity<String>  uploadExcelFile(@RequestParam("file")MultipartFile file){

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a Excel file!");
        }
        else {
            String msg = developerService.saveDeveloperFromExcel(file);
            return new ResponseEntity<>(msg,HttpStatus.OK);
        }
    }

    // API to download password-protected excel file
    @GetMapping("/downloadExcel/{adminid}")
    public ResponseEntity<?> downloadFile(@PathVariable("adminid") int adminid) throws IOException {

        List<Developer> developerList = developerService.getAllDev();
        if (developerList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("Message", "No content in Excel file").build();
        }

        // Pass password to service method
        String password = "dev123"; // you can make this dynamic
        ByteArrayInputStream in = developerService.exportDevelopersToExcel(adminid,password);

        if (in == null || in.available() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=DeveloperData.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }
}
