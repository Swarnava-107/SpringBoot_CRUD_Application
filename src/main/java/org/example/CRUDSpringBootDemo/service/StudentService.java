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
        Optional<Student> studentResponse = studentRepository.findByIdAndDeletedIsFalse(id);
        if(studentResponse.isPresent()){
            return studentResponse.get();
        }
        return null;
    }

    public List<Student> getAllStudents(){
        List<Student> studentList = studentRepository.findByDeletedIsFalse();
        return studentList;
    }

    public Student updateStudent(Long id, Student studentRequest){
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if(existingStudent.isEmpty()){
            return null;
        }
        Student studentToUpdate = existingStudent.get();

        studentToUpdate.setName(studentRequest.getName());
        studentToUpdate.setAge(studentRequest.getAge());
        studentToUpdate.setEmail(studentRequest.getEmail());
        studentToUpdate.setRollNo(studentRequest.getRollNo());
        studentToUpdate.setSubject(studentRequest.getSubject());
        studentToUpdate.setDeleted(false);

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

    public Boolean deleteStudentSoftly(Long id){

        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if(existingStudent.isEmpty()){
            return false;
        }
        Student studentToSave = existingStudent.get();
        studentToSave.setDeleted(true);
        studentRepository.save(studentToSave);

        // above three line can be converted into ------
//        existingStudent.get().setDeleted(true);
//        studentRepository.save(existingStudent.get());

        return true;
    }
}
