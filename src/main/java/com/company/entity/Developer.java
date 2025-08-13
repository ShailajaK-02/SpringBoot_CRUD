package com.company.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Entity
//lombok dependecy generate setter,getter,tostring
@Setter
@Getter
@ToString
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    //it ignore id while adding data over swagger and include id when getting data.
    //other way using schema
    private int id;

    private String fname;

    private String lname;

    private int age;

    private String city;

    private long salary;

    private  String gender;

    private String devloperId;

    private int yob;

}
