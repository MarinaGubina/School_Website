package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

    private Faculty f1 = new Faculty();
    private Faculty f2 = new Faculty();

    @BeforeEach
    public void setUp(){
        f1.setId(1L);
        f1.setName("Test1");
        f1.setColor("green");

        f2.setId(2L);
        f2.setName("Test2");
        f2.setColor("green");
    }
    @Test
    public void shouldAddFaculty(){
        when(facultyRepository.save(f1)).thenReturn(f1);
        assertEquals(f1,facultyService.createFaculty(f1));
    }
    @Test
    public void shouldGetExistFacultyById(){
        when(facultyRepository.findById(2L)).thenReturn(Optional.of(f2));
        assertEquals(f2,facultyService.getFacultyById(2L));
    }
    @Test
    public void shouldGetNotExistFacultyById(){
        Faculty expected = new Faculty();
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        assertEquals(expected,facultyService.getFacultyById(1L));
    }
    @Test
    public void shouldChangeExistFaculty(){
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(f1));
        when(facultyRepository.save(f1)).thenReturn(f1);
        assertEquals(f1,facultyService.updateFaculty(f1));
    }
    @Test
    public void shouldChangeNotExistFaculty(){
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertEquals(null,facultyService.updateFaculty(f2));
    }

    @Test
    public void shouldGetListFacultyByColor(){
        Collection<Faculty> filter = List.of(f1,f2);
        when(facultyRepository.findByColor("green")).thenReturn(List.of(f1,f2));
        assertEquals(filter,facultyService.filterFacultyByColor("green"));
    }
}