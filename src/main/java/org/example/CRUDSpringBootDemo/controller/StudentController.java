package org.example.CRUDSpringBootDemo.controller;

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
    public ResponseEntity<Student> createStudent(@RequestBody Student student){

        Student cretaedStudent = studentService.createStudent(student);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cretaedStudent);
    }


    // read one student
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Student studentResponse = studentService.getStudent(id);
        if(studentResponse == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
    }


    // read all student
    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> studentList = studentService.getAllStudents();
        if(studentList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentList);
    }


    // update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable Long id,
                                                     @RequestBody Student student){
        Student studentResponse = studentService.updateStudent(id, student);
        if(studentResponse == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
    }


    // delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deleteStudentById(@PathVariable Long id){
        Boolean isDeleted = studentService.deleteStudent(id);
        if(!isDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nothing found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }
}
