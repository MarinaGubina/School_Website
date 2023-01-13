package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FacultyServiceTest {

    private final FacultyService facultyService = new FacultyService();
    private final Faculty faculty1 = new Faculty("Гриффиндор", "желтый",0L);
    private final Faculty faculty2 = new Faculty("Слизерин", "зеленый",0L);
    private final Faculty faculty3 = new Faculty("Пуффендуй", "желтый",0L);
    private final Faculty faculty4 = new Faculty("Когтевран", "синий",0L);

    @BeforeEach
    public void setUp(){
        facultyService.createFaculty(faculty1);
        facultyService.createFaculty(faculty2);
        facultyService.createFaculty(faculty3);
    }

    @Test
    public void shouldAddFaculty(){
        Faculty actualFaculty = facultyService.createFaculty(faculty4);
        assertEquals(new Faculty("Когтевран", "синий",4L),actualFaculty);
    }

    @Test
    public void shouldGetFacultyById(){
        assertEquals(faculty1,facultyService.getFacultyById(1L));
    }

    @Test
    public void shouldChangeFaculty(){
        Faculty newFaculty = facultyService.updateFaculty(new Faculty("Новый факультет","цвет",2L));
        assertEquals(new Faculty("Новый факультет","цвет",2L), newFaculty);
    }

    @Test
    public void shouldDeleteFaculty(){
        assertEquals(faculty2, facultyService.deleteFaculty(2L));
    }

    @Test
    public void shouldGetListFacultyByColor(){
        Collection<Faculty> expected1 = List.of(faculty1,faculty3);
        assertEquals(expected1,facultyService.filterFacultyByColor("желтый"));
        Collection<Faculty> expected2 = List.of(faculty2);
        assertEquals(expected2,facultyService.filterFacultyByColor("зеленый"));
    }
}
