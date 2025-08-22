package com.company.controller;

import com.company.entity.Developer;
import com.company.service.AdminService;
import com.company.service.DeveloperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

//For logger this annotation is use
@Slf4j
@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    //add single data
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Developer developer){
//         System.err.println(developer);
         log.info("Received request to add Developer :{}" , developer);

         if(developer==null){
             return ResponseEntity.badRequest().body("Developer data is missing");
         }
         String msg = developerService.saveDeveloper(developer);
         log.info("Add developer completed : {}", msg);
         return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    //get all data
    @GetMapping("/getAllData")
    public  ResponseEntity<List<Developer>> getAlldata(){
        log.info("Getting all developer data ");
        List<Developer> developerList = developerService.getAllDev();

        if (developerList.isEmpty()){
            log.warn("No developer data found");
            return ResponseEntity.noContent().build();
        }
        log.info("Fetched {} developers", developerList.size());
        return new ResponseEntity<>(developerList, HttpStatus.OK);
    }

    //get by id
    @GetMapping("/getById/{id}")
    public ResponseEntity<Developer> getById(@PathVariable("id") int id) {
        log.info("Fetching developer with ID: {}", id);

        Developer developer = developerService.getDeveloperById(id);

//        if (developer == null) {
//            log.warn("Developer with ID {} not found", id);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }

        log.info("Developer with ID {} fetched successfully", id);
        return new ResponseEntity<>(developer, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
        log.info("Attempting to delete developer with ID: {}", id);

        boolean deleted = developerService.deleteDev(id); // directly boolean

        if (!deleted) {
            log.warn("Delete failed - Developer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Developer not found");
        }

        log.info("Developer with ID {} deleted successfully", id);
        return ResponseEntity.ok("Deleted successfully");
    }



    //update data by id
    @PutMapping("/updateDev/{id}")
    public ResponseEntity<Developer> updateDev(@PathVariable("id") int id, @RequestBody Developer developer) {
        log.info("Updating developer with ID: {}", id);

        Developer updatedDev = developerService.updateDev(id, developer);

        if (updatedDev == null) {
            log.warn("Update failed - Developer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("Developer with ID {} updated successfully", id);
        return new ResponseEntity<>(updatedDev, HttpStatus.OK);
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

        ByteArrayInputStream in = developerService.exportDevelopersToExcel(adminid);

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

    //Query Filter by age using jpql query
    @GetMapping("/getByAge/{age}")
    public ResponseEntity<List<Developer>> getByAge(@PathVariable("age") int age){
        List<Developer> developers = developerService.getByAge(age);
        return new ResponseEntity<>(developers, HttpStatus.OK);
    }

    //write api to get the dev whose age > 25
    @GetMapping("/devMaxAge/{age}")
    public ResponseEntity<List<Developer>> maxAge(@PathVariable("age") int age){
        List<Developer> developers = developerService.maxAge(age);
        return new ResponseEntity<>(developers, HttpStatus.OK);
    }

    //api to get by name using native query
    @GetMapping("/devByName/{fname}")
    public ResponseEntity<Developer> devByName(@PathVariable("fname") String fname){
        Developer d = developerService.devByName(fname);
        return new ResponseEntity<>(d,HttpStatus.OK);
    }

}
