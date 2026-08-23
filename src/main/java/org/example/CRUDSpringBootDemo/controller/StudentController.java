package org.example.CRUDSpringBootDemo.controller;

import jakarta.validation.Valid;
import org.example.CRUDSpringBootDemo.dto.CreateStudentRequestDto;
import org.example.CRUDSpringBootDemo.dto.CreateStudentResponseDto;
import org.example.CRUDSpringBootDemo.dto.UpdateStudentRequestDto;
import org.example.CRUDSpringBootDemo.dto.UpdateStudentResponseDto;
import org.example.CRUDSpringBootDemo.entity.Student;
import org.example.CRUDSpringBootDemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // create student
    @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(
            @Valid @RequestBody CreateStudentRequestDto createStudentRequestDto){

        CreateStudentResponseDto createdStudent = studentService.createStudent(createStudentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }


    // read one student
    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudentById(@PathVariable Long id){

        CreateStudentResponseDto studentResponse = studentService.getStudent(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
    }

    // select * from student where id = 1 and deleted = false

    // get/read all student
    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent(){
        List<CreateStudentResponseDto> studentList = studentService.getAllStudents();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentList);
    }


    // update student
    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudentById(@RequestParam Long id,
                                                     @RequestBody UpdateStudentRequestDto studentRequestDto){
        UpdateStudentResponseDto studentResponse = studentService.updateStudent(id, studentRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
    }


    // delete student
    @DeleteMapping
    public ResponseEntity<String>  deleteStudentById(@RequestParam Long id){
        studentService.deleteStudent(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    // soft-delete
    @PatchMapping("/delete-soft")
    public ResponseEntity<String>  deleteStudentSoftly(@RequestParam Long id){
        studentService.deleteStudentSoftly(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
