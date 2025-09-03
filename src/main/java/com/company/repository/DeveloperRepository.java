package com.company.repository;

import com.company.entity.Developer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//Here we write custom methods in which we can perform filteration ,
//jpa is an interface
public interface DeveloperRepository extends JpaRepository<Developer, Integer>
{

    //This is JPQL query for age
    @Query("SELECT d FROM Developer d WHERE d.age = :age")
    List<Developer> filterByAge(@Param("age") int age);

    //This jpql query finds developer whose age is greater > certain age
    @Query("SELECT d FROM Developer d WHERE d.age > :age ")
    List<Developer> getDevByMaxAge(@Param("age") int age);

    //Native Query get dev by name
    //native query -> sql query
    @Query(value = "SELECT * FROM Developer WHERE fname = :fname", nativeQuery = true)
    Developer devByName(@Param("fname") String fname);

    //Custom query  delete using modifying + transactional annotation
    @Modifying
    @Query("DELETE FROM Developer d WHERE d.id = :id ")
    String deleteDevQuery(int id);

    //to update first find all dev whose devid is null from db
    @Query(value = "SELECT * FROM Developer WHERE devloper_Id IS NULL",nativeQuery = true)
    List<Developer>  findDevWithMissId();

    //now here update the devid using annotation modifying and transactional
    @Transactional
    @Modifying
    @Query(value = "UPDATE Developer SET devloper_Id = :devId WHERE id = :id",nativeQuery = true)
    void updateIfDevIdMiss(@Param("id") int id , @Param("devId") String devId);

    //Update the age of dev if today is dev's birthday using curdate
    @Query(value = "SELECT * FROM Developer" + " WHERE MONTH(dob) = MONTH(CURDATE())" +
            " AND DAY(dob) = DAY(CURDATE())",nativeQuery = true)
    List<Developer> findTodaysBirthdays();

    //Now update the birthday for update we need id of developer
    @Transactional
    @Modifying
    @Query(value = "UPDATE Developer SET age = :age WHERE id = :id",nativeQuery = true)
    void updateAgeBYBirthdate(@Param("id") int id, @Param("age") int age);
}
