package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id){
        logger.info("Was invoked method for get student by id");
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student){
        logger.info("Was invoked method for update student");
        if(studentRepository.findById(student.getId()).isPresent()){
        return studentRepository.save(student);}
        else{
            logger.error("There is not student with id = " + student.getId());
            return null;
        }
    }
    public void deleteStudent(Long id){
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public Collection<Student> filterStudentsByAge(int age){
        logger.info("Was invoked method for filter student by age");
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max){
        logger.info("Was invoked method for find student by age");
        return studentRepository.findByAgeBetween(min,max);
    }
    public Collection<Student> findAllStudents(){
        logger.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }
    public Integer getCountStudents(){
        logger.info("Was invoked method for count of student");
        return studentRepository.getCountOfAllStudents();
    }
    public Double getAverageAge(){
        logger.info("Was invoked method for get average age of student");
        return studentRepository.getAverageAge();
    }
    public Collection<Student> getLastFiveStudent(){
        logger.info("Was invoked method for get five last students");
        return studentRepository.getLastFiveStudents();
    }

    public List<Student> getStudentsByName(String name){
        logger.info("Was invoked method for get student by name");
        return studentRepository.getStudentsByName(name);
    }

    public Collection<String> getSortedStudentByName() {
        return  studentRepository.findAll()
                .stream()
                .parallel()
                .map((s) -> s.getName().toUpperCase())
                .filter(name -> name.startsWith("A"))
                .sorted()
                .toList();
    }

    public Double getAverageAge2(){
        return studentRepository.findAll()
                .stream()
                .parallel()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow(RuntimeException::new);
    }
     private void printStudentsName(List<Student> students){
        for(Student student: students){
            logger.info(student.getName());
        }
     }

    public void getAllStudentsInConsole(){

        List<Student> students = studentRepository.findAll(PageRequest.of(0,6)).getContent();

        printStudentsName(students.subList(0,2));
        new Thread(() -> printStudentsName(students.subList(2,4))).start();
        new Thread(() -> printStudentsName(students.subList(4,6))).start();
    }

    private synchronized void printStudentsNameSync(List<Student> students){
        for(Student student: students){
            logger.info(student.getName());
        }
    }
    public void getAllStudentsInConsoleSync(){

        List<Student> students = studentRepository.findAll(PageRequest.of(0,6)).getContent();

        printStudentsName(students.subList(0,2));
        new Thread(() -> printStudentsNameSync(students.subList(2,4))).start();
        new Thread(() -> printStudentsNameSync(students.subList(4,6))).start();
    }
}
