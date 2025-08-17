package com.company.service;

import com.company.entity.Admin;

import java.util.List;

public interface AdminService {

    //add admin
    String saveAdmin(List<Admin> admins);

    //get all data
    List<Admin> getallAdminData();
}
