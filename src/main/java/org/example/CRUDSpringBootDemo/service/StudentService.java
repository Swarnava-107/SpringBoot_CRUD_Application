package org.example.CRUDSpringBootDemo.service;

import org.example.CRUDSpringBootDemo.dto.CreateStudentRequestDto;
import org.example.CRUDSpringBootDemo.dto.CreateStudentResponseDto;
import org.example.CRUDSpringBootDemo.dto.UpdateStudentRequestDto;
import org.example.CRUDSpringBootDemo.dto.UpdateStudentResponseDto;
import org.example.CRUDSpringBootDemo.entity.Student;
import org.example.CRUDSpringBootDemo.exception.DuplicateResourceException;
import org.example.CRUDSpringBootDemo.exception.ResourceNotFoundException;
import org.example.CRUDSpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // passing request from controller to db
    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto){
        Student student = mapToEntity(studentReqDto);

        if(emailExists(student)){
            throw new DuplicateResourceException("Student with email "+student.getEmail()+" already exists");
        }

        Student studentResp =  studentRepository.save(student);
        return mapToCreateDto(studentResp);
    }

    public CreateStudentResponseDto getStudent(Long id){
        Student studentResp = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id - "+ id));

        return mapToCreateDto(studentResp);
    }

    public List<CreateStudentResponseDto> getAllStudents(){
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToCreateDto)
                .toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentRequest){
        Student existingStudent = studentRepository
                                        .findByIdAndDeletedIsFalse(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id - "+ id));

        existingStudent.setName(studentRequest.getName());
        existingStudent.setAge(studentRequest.getAge());
        existingStudent.setRollNo(studentRequest.getRollNo());
        existingStudent.setSubject(studentRequest.getSubject());
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(existingStudent);
        return mapToUpdateDto(savedStudent);
    }

    public void deleteStudent(Long id){

        Student studentToDelete = studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id - "+ id));
        studentRepository.delete(studentToDelete);

    }

    public void deleteStudentSoftly(Long id){

        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id - "+ id));

        existingStudent.setDeleted(true);
        studentRepository.save(existingStudent);

        // above three line can be converted into ------
//        existingStudent.get().setDeleted(true);
//        studentRepository.save(existingStudent.get());
    }

    private Student mapToEntity(CreateStudentRequestDto createStudentRequestDto){
        Student student = new Student();

        student.setName(createStudentRequestDto.getName());
        student.setAge(createStudentRequestDto.getAge());
        student.setEmail(createStudentRequestDto.getEmail());
        student.setRollNo(createStudentRequestDto.getRollNo());
        student.setSubject(createStudentRequestDto.getSubject());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setDeleted(false);
        return student;

        // builder design pattern
    }

    private CreateStudentResponseDto mapToCreateDto(Student student){
        CreateStudentResponseDto createStudentResponseDto = new CreateStudentResponseDto();

        createStudentResponseDto.setId(student.getId());
        createStudentResponseDto.setName(student.getName());
        createStudentResponseDto.setAge(student.getAge());
        createStudentResponseDto.setEmail(student.getEmail());
        createStudentResponseDto.setRollNo(student.getRollNo());
        createStudentResponseDto.setSubject(student.getSubject());
        createStudentResponseDto.setMessage("Student saved successfully");
        createStudentResponseDto.setCreatedAt(student.getCreatedAt());
        createStudentResponseDto.setUpdatedAt(student.getUpdatedAt());

        return createStudentResponseDto;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student savedStudent) {
        UpdateStudentResponseDto updateStudentResponseDto = new UpdateStudentResponseDto();

        updateStudentResponseDto.setId(savedStudent.getId());
        updateStudentResponseDto.setName(savedStudent.getName());
        updateStudentResponseDto.setAge(savedStudent.getAge());
        updateStudentResponseDto.setEmail(savedStudent.getEmail());
        updateStudentResponseDto.setRollNo(savedStudent.getRollNo());
        updateStudentResponseDto.setSubject(savedStudent.getSubject());
        updateStudentResponseDto.setMessage("Student updated successfully");
        updateStudentResponseDto.setUpdatedAt(LocalDateTime.now());

        return updateStudentResponseDto;
    }

    private Boolean emailExists(Student student){
        return studentRepository.existsByEmail(student.getEmail());
    }
}
