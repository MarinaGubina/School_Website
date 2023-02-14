package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

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
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        Faculty faculty = facultyService.getFacultyById(id);
        if(faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getAllStudentsFromFaculty(@PathVariable Long id){
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

    @GetMapping("/all")
    public ResponseEntity<Collection<Faculty>> findAllFaculties(){
        return ResponseEntity.ok(facultyService.findAllFaculties());
    }

    @GetMapping("/filter")
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

    @GetMapping("/{name}/{color}")
    public ResponseEntity<Collection<Faculty>> findByNameAndColor(@PathVariable String name,
                                                                  @PathVariable String color){
        return ResponseEntity.ok(facultyService.findByColorAndName(name,color));
    }

    @GetMapping("/longest-name-faculty")
    public ResponseEntity<String> getLongestNameOfTheFaculty(){
        return ResponseEntity.ok(facultyService.getLongestNameOfFaculty());
    }

    @GetMapping("/sum")
    public ResponseEntity<Integer> getSum(){
        int sum = Stream.iterate(1,a -> a+1)
                .limit(1_000_000)
                .parallel()
                .reduce(0,(a,b) -> a + b);
        return ResponseEntity.ok(sum);
    }
}
