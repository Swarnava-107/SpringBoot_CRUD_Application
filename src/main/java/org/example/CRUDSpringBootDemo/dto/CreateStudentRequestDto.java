package org.example.CRUDSpringBootDemo.dto;

import jakarta.validation.constraints.*;

public class CreateStudentRequestDto {
    @NotBlank(message = "Name cannot be null/empty or blank")
    @Size(min = 2 , max = 50, message = "Name must be from 2 to 50 character")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "please put valid email")
    private String email;

    @NotNull(message = "Age cannot be blank")
    @Min(value = 18, message = "Student must be 18 or above")
    private Integer age;


    @NotNull(message = "roll no is required")
    private Integer rollNo;

    @NotBlank(message = "give a subject name")
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
