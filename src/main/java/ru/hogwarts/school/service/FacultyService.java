package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id){
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty){
        if(facultyRepository.findById(faculty.getId()).isPresent()){
            return facultyRepository.save(faculty);
        }
        else{
            return null;}
    }

    public void deleteFaculty(Long id){
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterFacultyByColor(String color){
        return facultyRepository.findByColor(color);
    }
}
