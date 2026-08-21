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
    @PostMapping("/create")
    public ResponseEntity<CreateStudentResponseDto> createStudent(
            @Valid @RequestBody CreateStudentRequestDto createStudentRequestDto){

        CreateStudentResponseDto createdStudent = studentService.createStudent(createStudentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }


    // read one student
    @GetMapping("/get")
    public ResponseEntity<CreateStudentResponseDto> getStudentById(@RequestParam Long id){

        CreateStudentResponseDto studentResponse = studentService.getStudent(id);
        if(studentResponse == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
    }

    // select * from student where id = 1 and deleted = false

    // get/read all student
    @GetMapping("/getAll")
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent(){
        List<CreateStudentResponseDto> studentList = studentService.getAllStudents();
        if(studentList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentList);
    }


    // update student
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateStudentResponseDto> updateStudentById(@PathVariable Long id,
                                                     @RequestBody UpdateStudentRequestDto studentRequestDto){
        UpdateStudentResponseDto studentResponse = studentService.updateStudent(id, studentRequestDto);
        if(studentResponse == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
    }


    // delete student
    @DeleteMapping("/delete")
    public ResponseEntity<String>  deleteStudentById(@RequestParam Long id){
        Boolean isDeleted = studentService.deleteStudent(id);
        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nothing found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }


    // soft-delete
    @PatchMapping("/delete-soft")
    public ResponseEntity<String>  deleteStudentSoftly(@RequestParam Long id){
        Boolean isDeleted = studentService.deleteStudentSoftly(id);
        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nothing found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("record deleted softly");
    }
}
