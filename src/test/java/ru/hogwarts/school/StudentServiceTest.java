package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentServiceTest {

    private final StudentService studentService = new StudentService();
    private final Student student1 = new Student("Гарри", 12,0L);
    private final Student student2 = new Student("Гермиона", 11,0L);
    private final Student student3 = new Student("Рон", 12,0L);
    private final Student student4 = new Student("Полумна", 10,0L);

    @BeforeEach
    public void setUp(){
        studentService.createStudent(student1);
        studentService.createStudent(student2);
        studentService.createStudent(student3);
    }

    @Test
    public void shouldAddFaculty(){
        Student actual = studentService.createStudent(student4);
        assertEquals(new Student("Полумна", 10,4L),actual);
    }

    @Test
    public void shouldGetFacultyById(){
        assertEquals(student1,studentService.getStudentById(1L));
    }

    @Test
    public void shouldChangeFaculty(){
        Student newStudent = studentService.updateStudent(new Student("Имя",15,2L));
        assertEquals(new Student("Имя",15,2L), newStudent);
    }

    @Test
    public void shouldDeleteFaculty(){
        assertEquals(student2, studentService.deleteStudent(2L));
    }

    @Test
    public void shouldGetListFacultyByColor(){
        Collection<Student> expected1 = List.of(student1, student3);
        assertEquals(expected1,studentService.filterStudentsByAge(12));
        Collection<Student> expected2 = List.of(student2);
        assertEquals(expected2,studentService.filterStudentsByAge(11));
    }
}
