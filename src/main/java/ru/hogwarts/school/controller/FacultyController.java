package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RequestMapping("/faculty")
@RestController
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Collection<Student>> getFaculty(@PathVariable Long id){
        Faculty faculty = facultyService.getFacultyById(id);
        Collection<Student> students = faculty.getStudents();
        if(faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty){
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if(updateFaculty == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping ("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> filterFacultyByColor(@RequestParam(required = false) String color,
                                                                    @RequestParam(required = false) String colorOrName){
        if(color != null && !color.isBlank() ){
            return ResponseEntity.ok(facultyService.filterFacultyByColor(color));
        }
        if(colorOrName != null && !colorOrName.isBlank()){
            return ResponseEntity.ok(facultyService.findByColorOrName(colorOrName));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
