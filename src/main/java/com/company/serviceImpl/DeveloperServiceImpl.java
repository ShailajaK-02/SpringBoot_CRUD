package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.helper.ExcelDataRead;
import com.company.helper.GenerateDeveloperId;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    //save devloper
    @Override
    public String saveDeveloper(Developer developer) {

        //call method from here
        String devId = GenerateDeveloperId.generateId(developer);

        developer.setDevloperId(devId);
        Developer saveDeveloper = developerRepository.save(developer);
        return " Hii "+ developer.getFname()+ "Your developer Id is : " +  developer.getDevloperId();
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
}
