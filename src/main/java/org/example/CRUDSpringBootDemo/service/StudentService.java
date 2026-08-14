package org.example.CRUDSpringBootDemo.service;

import org.example.CRUDSpringBootDemo.entity.Student;
import org.example.CRUDSpringBootDemo.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // passing request from controller to db
    public Student createStudent(Student studentRequest){
        Student studentResponse = studentRepository.save(studentRequest);
        return studentResponse;
    }

    public Student getStudent(Long id){
        Optional<Student> studentResponse = studentRepository.findById(id);
        if(studentResponse.isPresent()){
            return studentResponse.get();
        }
        return null;
    }

    public List<Student> getAllStudents(){
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    public Student updateStudent(Long id, Student studentRequest){
        Optional<Student> existingStudent = studentRepository.findById(id);
        if(existingStudent.isEmpty()){
            return null;
        }
        Student studentToUpdate = existingStudent.get();

        studentToUpdate.setName(studentRequest.getName());
        studentToUpdate.setAge(studentRequest.getAge());
        studentRequest.setEmail(studentRequest.getEmail());
        studentRequest.setRollNo(studentRequest.getRollNo());
        studentRequest.setSubject(studentRequest.getSubject());

        return studentRepository.save(studentToUpdate);
    }

    public Boolean deleteStudent(Long id){
        Boolean isStudent = studentRepository.existsById(id);

        if(!isStudent){
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}
