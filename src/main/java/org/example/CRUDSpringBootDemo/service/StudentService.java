package org.example.CRUDSpringBootDemo.service;

import org.example.CRUDSpringBootDemo.dto.CreateStudentRequestDto;
import org.example.CRUDSpringBootDemo.dto.CreateStudentResponseDto;
import org.example.CRUDSpringBootDemo.dto.UpdateStudentRequestDto;
import org.example.CRUDSpringBootDemo.dto.UpdateStudentResponseDto;
import org.example.CRUDSpringBootDemo.entity.Student;
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
        Student studentResp =  studentRepository.save(student);
        return mapToCreateDto(studentResp);
    }

    public CreateStudentResponseDto getStudent(Long id){
        Optional<Student> studentResponse = studentRepository.findById(id);
        if(studentResponse.isPresent()){
            return mapToCreateDto(studentResponse.get());
        }
        return null;
    }

    public List<CreateStudentResponseDto> getAllStudents(){
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToCreateDto)
                .toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentRequest){
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if(existingStudent.isEmpty()){
            return null;
        }
        Student studentToUpdate = existingStudent.get();

        studentToUpdate.setName(studentRequest.getName());
        studentToUpdate.setAge(studentRequest.getAge());
        studentToUpdate.setRollNo(studentRequest.getRollNo());
        studentToUpdate.setSubject(studentRequest.getSubject());
        studentToUpdate.setUpdatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(studentToUpdate);
        return mapToUpdateDto(savedStudent);
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
}
