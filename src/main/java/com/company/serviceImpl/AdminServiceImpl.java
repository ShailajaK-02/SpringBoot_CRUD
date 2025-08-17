package com.company.serviceImpl;

import com.company.entity.Admin;
import com.company.repository.AdminRepository;
import com.company.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    //inject repo in this
    @Autowired
    private AdminRepository repository;

    @Override
    public String saveAdmin(List<Admin> admins) {
        List<Admin> adminList = repository.saveAll(admins);
        return "List saved";
    }

    @Override
    public List<Admin> getallAdminData() {
        List<Admin> adminList = repository.findAll();
        return adminList;
    }

    @Override
    public String deleteAdmin(int id) {
        repository.deleteById(id);
        return "Admin deleted";
    }
}
