package com.company.controller;

import com.company.entity.Admin;
import com.company.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    //inject service in this
    @Autowired
    private AdminService adminService;

    //post list of admin indatabase
    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody List<Admin> admins){
        adminService.saveAdmin(admins);
        return new ResponseEntity<>("Admin added", HttpStatus.OK);
    }

    //get all admin
    @GetMapping("/getAllAdmin")
    public ResponseEntity<List<Admin>> getallAdmin(){
        List<Admin> adminList = adminService.getallAdminData();
        return new ResponseEntity<>(adminList,HttpStatus.OK);
    }

}
