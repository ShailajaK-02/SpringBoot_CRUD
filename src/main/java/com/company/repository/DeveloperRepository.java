package com.company.repository;

import com.company.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//Here we write custom methods in which we can perform filteration ,
public interface DeveloperRepository extends JpaRepository<Developer, Integer>
{

    //This is JPQL query
    @Query("SELECT d FROM Developer d WHERE d.age = :age")
    List<Developer> filterByAge(@Param("age") int age);
}
