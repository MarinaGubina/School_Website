package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService,
                             StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Student student = studentService.getStudentById(id);
        if(student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Faculty> getFacultyStudent(@PathVariable Long id){
        Student student = studentService.getStudentById(id);
        if(student == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Faculty faculty = student.getFaculty();
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student updateStudent = studentService.updateStudent(student);
        if(updateStudent == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Collection<Student>> findStudentByAge(@RequestParam(required = false, defaultValue = "0") int age,
                                                                   @RequestParam(required = false, defaultValue = "0") int min,
                                                                   @RequestParam(required = false, defaultValue = "0") int max){
        if(age > 0){
            return ResponseEntity.ok(studentService.filterStudentsByAge(age));
        }
        if(max > min && max > 0 && min > 0 ){
            return ResponseEntity.ok(studentService.findByAgeBetween(min,max));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> findAllStudents(){
        return ResponseEntity.ok(studentService.findAllStudents());
    }
    @GetMapping("/count")
    public ResponseEntity<Integer> getCountStudents(){
        return ResponseEntity.ok(studentService.getCountStudents());
    }
    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge(){
        return ResponseEntity.ok(studentService.getAverageAge());
    }
    @GetMapping("/last-student")
    public ResponseEntity<Collection<Student>> getLastStudents(){
        return ResponseEntity.ok(studentService.getLastFiveStudent());
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Student>> getStudentByName(@PathVariable String name){
        List<Student> students = studentService.getStudentsByName(name);
        return ResponseEntity.ok(students);
    }
}
