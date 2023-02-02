package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @AfterEach
    public void resetDb() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateStudent() throws Exception{
        Student student = new Student();
        student.setName("Jo");
        student.setAge(22);

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" +
                port + "/student", student,Student.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void shouldGetStudentById() throws Exception{
        Student student = createStudent("Michel",20);
        long id = student.getId();
        Student person = restTemplate.getForObject("http://localhost:" +
                port + "/student/" + id,Student.class);

        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person).isEqualTo(student);
    }

    @Test
    void shouldGetFacultyOfStudent() throws Exception{
        Faculty faculty = new Faculty();
        faculty.setName("griffindor");
        faculty.setColor("yellow");
        facultyRepository.save(faculty);

        Student student = createStudent("Michel",20);
        student.setFaculty(faculty);
        long id = student.getId();

        ResponseEntity<Faculty> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/student/faculty/" + id,Faculty.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldUpdateStudent() throws Exception{
        Student student = createStudent("Michel",20);

        student.setName("Jo");
        HttpEntity<Student> entity = new HttpEntity<>(student);

        ResponseEntity<Student> response= restTemplate.exchange("http://localhost:" +
                port + "/student/" + student, HttpMethod.PUT, entity, Student.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //Assertions.assertThat(response.getBody()).isNotNull();
        //Assertions.assertThat(response.getBody()).isEqualTo(student);
    }

    @Test
    void deleteStudentTest(){
        long id = createStudent("Michel",20).getId();

        restTemplate.delete("http://localhost:" + port + "/student/" + id, Student.class);

        Assertions.assertThat(this.restTemplate
                .getForObject("http://localhost:" + port + "/student/" + id,  String.class)).isNull();

    }

    @Test
    void shouldFindStudentByAge(){
        int age = createStudent("Bob", 20).getAge();
        createStudent("Tom", 32);
        ResponseEntity<Student[]> response  = restTemplate.exchange("http://localhost:"+ port + "/student/filter/{age}",
                HttpMethod.GET,null,Student[].class, age);

        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldReturnAllStudents(){
        createStudent("Tom",24);
        createStudent("Michel",20);

        ResponseEntity<Student[]> response = restTemplate.getForEntity("http://localhost:" + port + "/student",
                Student[].class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    private Student createStudent(String name, int age){
        Student s = new Student();
        s.setName(name);
        s.setAge(age);
        return studentRepository.save(s);
    }
}

