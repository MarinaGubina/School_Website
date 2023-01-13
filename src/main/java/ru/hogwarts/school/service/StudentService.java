package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private final Map<Long, Student> allStudent = new HashMap();
    private Long generateId = 0L;

    public Student createStudent(Student student){
        student.setStudentId(++generateId);
        allStudent.put(generateId,student);
        return student;
    }

    public Student getStudentById(Long id){
        return allStudent.get(id);
    }

    public Student updateStudent(Student student){
        if(!allStudent.containsKey(student.getStudentId())){
            return null;
        }
        allStudent.put(student.getStudentId(), student);
        return student;
    }

    public Student deleteStudent(Long id){
        return allStudent.remove(id);
    }

    public Collection<Student> filterStudentsByAge(int age){
        return allStudent.values().stream()
                .filter(student -> student.getAge() == age )
                .toList();
    }
}
