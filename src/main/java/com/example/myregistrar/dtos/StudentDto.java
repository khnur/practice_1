package com.example.myregistrar.dtos;

import com.example.myregistrar.models.Course;
import com.example.myregistrar.util.DateMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Integer age;
    private String gender;
    private String email;
    @ToString.Exclude
    @JsonIgnore
    private List<CourseDto> courseDtoList = new ArrayList<>();

    public StudentDto(String firstName, String lastName, Date birthDate, String gender) {
        firstName = firstName.trim();
        lastName = lastName.trim();

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.age = DateMapper.GET_AGE(birthDate);
        this.gender = gender.trim();
        this.email = firstName.toLowerCase() + '.' + lastName.toLowerCase();
    }
}
