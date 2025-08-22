package com.company.serviceImpl;

import com.company.entity.Admin;
import com.company.entity.Developer;
//import com.company.exception.DeveloperNotFoundException;
import com.company.exception.DeveloperNotFoundException;
import com.company.helper.AddDataInExcel;
import com.company.helper.ExcelDataRead;
import com.company.helper.GenerateDeveloperId;
import com.company.repository.AdminRepository;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j   //for logger
@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private AdminRepository adminRepository;

    //save devloper
    @Override
    public String saveDeveloper(Developer developer) {

        //calculate age of developer
        int age = Period.between(developer.getDob(), LocalDate.now()).getYears();
        developer.setAge(age);
        //call method from here
        String devId = GenerateDeveloperId.generateId(developer);

        developer.setDevloperId(devId);
        log.debug("Saving developer :{}",developer);

        Developer saveDeveloper = developerRepository.save(developer);

        log.info("Developer saved with fname={} lname={}", developer.getFname(), developer.getLname());

        return " Hii "+ developer.getFname()+ "Your developer Id is : " +  developer.getDevloperId();//on postman
    }

    //get all data
    @Override
    public List<Developer> getAllDev() {
       List<Developer> developerList =  developerRepository.findAll();
       return  developerList;
    }

    //get by id
    @Override
    public Developer getDeveloperById(int id) {
        //custom exception
        Developer developer =  developerRepository.findById(id).orElseThrow(() -> new DeveloperNotFoundException("Developer with id not found " + id));

        return developer;
    }

    @Override
    public boolean deleteDev(int id) {
        Optional<Developer> developer = developerRepository.findById(id);
        if (developer.isPresent()) {
            developerRepository.deleteById(id);
            return true;  // return boolean
        }
        return false;
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
        //calculate age of developer
        for(Developer developer : developers){
            int age = Period.between(developer.getDob(), LocalDate.now()).getYears();
            developer.setAge(age);
            //generate their dev id
            String devId = GenerateDeveloperId.generateId(developer);
            developer.setDevloperId(devId);
        }
        List<Developer> developerList = developerRepository.saveAll(developers);
        return "List saved";
    }

    //filter by city method
    @Override
    public List<Developer> filterByCity(String city) {
        List<Developer> devList = developerRepository.findAll();

        List<Developer> filterdList = devList.stream().filter(developer -> developer.getCity() !=null && developer.getCity().equalsIgnoreCase(city)).collect(Collectors.toList());

        return filterdList;
    }

    //filter by gender
    @Override
    public List<Developer> filterByGender(String gender) {
        List<Developer> devList = developerRepository.findAll();

        List<Developer> filterdList = devList.stream().filter(developer -> developer.getGender() != null && developer.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());

        return filterdList;
    }

    //filter by gender and city
    @Override
    public List<Developer> filterByGenCity(String city, String gender) {
        List<Developer> devList = developerRepository.findAll();

        List<Developer> filterdList = devList.stream().filter(developer
                ->developer.getGender() != null && developer.getGender().equalsIgnoreCase(gender)
                        && developer.getCity() !=null && developer.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());

        //dev.city!=null to handle null pointer exception
        return filterdList;
    }

    @Override
    //savedevfromexcel method implemented
    public String saveDeveloperFromExcel(MultipartFile file) {

        try {
            List<Developer> developers = ExcelDataRead.convertExcelToListOfDev(file.getInputStream());

            //use Generatedev id helper for creating dev id for each developer
            for( Developer dev : developers){
                dev.setDevloperId(GenerateDeveloperId.generateId(dev));
            }

            developerRepository.saveAll(developers);
            return "Excel data uploaded successfully!";
        }
        catch (IOException e){
            throw new RuntimeException("Failed to store Excel data: " + e.getMessage());
        }
    }

    //exportdevtoexcel added
    @Override
    public ByteArrayInputStream exportDevelopersToExcel(int adminid) throws IOException {

        //validate the admin if admin exist then only download otherwise dont
        Optional<Admin> adminOpt = adminRepository.findById(adminid);
        if (adminOpt.isEmpty()) {
            return null;
        }
        Admin admin = adminOpt.get(); //get admin object
        List<Developer> developers = developerRepository.findAll();

        // Step 1: Create normal Excel file
        ByteArrayInputStream normalExcel = AddDataInExcel.developersToExcel(developers);

        //Dynamaic password for admin

        int digit = admin.getAdminid() % 100;

        String dynamicpassword = admin.getLname().substring(0,1) + admin.getFname() + digit ; //kshailaja05

        // Step 2: Encrypt with password
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
        Encryptor encryptor = info.getEncryptor();
        encryptor.confirmPassword(dynamicpassword); // set password here

        try (OPCPackage opc = OPCPackage.open(normalExcel);
             OutputStream os = encryptor.getDataStream(fs)) {
            opc.save(os);
        } catch (Exception e) {
            throw new IOException("Error encrypting Excel file", e);
        }

        // Step 3: Return encrypted file as ByteArrayInputStream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        fs.writeFilesystem(bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }

    @Override
    public List<Developer> getByAge(int age) {
        List<Developer> developers = developerRepository.filterByAge(age);
        return developers;
    }

    @Override
    public List<Developer> maxAge(int age) {
        List<Developer> developerList = developerRepository.getDevByMaxAge(age);
        return developerList;
    }

    @Override
    public Developer devByName(String fname) {
        Developer developer = developerRepository.devByName(fname);
        return developer;
    }

}
