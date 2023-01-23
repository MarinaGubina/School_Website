package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student){
        if(studentRepository.findById(student.getStudentId()).isPresent()){
        return studentRepository.save(student);}
        else{
            return null;
        }
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

    public Collection<Student> filterStudentsByAge(int age){
        return studentRepository.findAllByAge(age);
    }
}
