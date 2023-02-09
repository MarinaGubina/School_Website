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
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty){
        if(facultyRepository.existsById(faculty.getId())){
            return facultyRepository.save(faculty);
        }
        else{
            return null;}
    }

    public void deleteFaculty(Long id){
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findAllFaculties(){
        return facultyRepository.findAll();
    }

    public Collection<Faculty> filterFacultyByColor(String color){
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByColorOrName(String colorOrName){
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(colorOrName,colorOrName);
    }

    public Collection<Faculty> findByColorAndName(String name, String color){
        return facultyRepository.findByNameIgnoreCaseAndColorIgnoreCase(name,color);
    }
}
