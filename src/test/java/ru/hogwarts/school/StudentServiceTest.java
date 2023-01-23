package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    private Student s1 = new Student();
    private Student s2 = new Student();

    @BeforeEach
    public void setUp(){
        s1.setStudentId(1L);
        s1.setName("Test1");
        s1.setAge(20);

        s2.setStudentId(2L);
        s2.setName("Test2");
        s2.setAge(22);
    }
    @Test
    public void shouldAddFaculty(){
        when(repository.save(s1)).thenReturn(s1);
        assertEquals(s1, service.createStudent(s1));
    }
    @Test
    public void shouldGetExistFacultyById(){
        when(repository.findById(2L)).thenReturn(Optional.of(s2));
        assertEquals(s2, service.getStudentById(2L));
    }
    @Test
    public void shouldGetNotExistFacultyById(){
        Student expected = new Student();
        when(repository.findById(anyLong())).thenReturn(Optional.of(expected));
        assertEquals(expected, service.getStudentById(1L));
    }
    @Test
    public void shouldChangeExistFaculty(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(s1));
        when(repository.save(s1)).thenReturn(s1);
        assertEquals(s1, service.updateStudent(s1));
    }
    @Test
    public void shouldChangeNotExistFaculty(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertEquals(null, service.updateStudent(s2));
    }

    @Test
    public void shouldGetListFacultyByColor(){
        Collection<Student> filter = List.of(s1);
        when(repository.findAllByAge(20)).thenReturn(List.of(s1));
        assertEquals(filter, service.filterStudentsByAge(20));
    }
}
