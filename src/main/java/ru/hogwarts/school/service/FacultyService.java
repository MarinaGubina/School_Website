package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id){
        logger.info("Was invoked method for get faculty by id");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty){
        logger.info("Was invoked method for update faculty");
        if(facultyRepository.existsById(faculty.getId())){
            return facultyRepository.save(faculty);
        }
        else{
            logger.error("There is not faculty with id = " + faculty.getId());
            return null;}
    }

    public void deleteFaculty(Long id){
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findAllFaculties(){
        logger.info("Was invoked method for find all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> filterFacultyByColor(String color){
        logger.info("Was invoked method for filter faculties by color");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByColorOrName(String colorOrName){
        logger.info("Was invoked method for find faculties by color or name");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(colorOrName,colorOrName);
    }

    public Collection<Faculty> findByColorAndName(String name, String color){
        logger.info("Was invoked method for find faculties by color and name");
        return facultyRepository.findByNameIgnoreCaseAndColorIgnoreCase(name,color);
    }

    public String getLongestNameOfFaculty() {
        return facultyRepository.findAll()
                .stream()
                .parallel()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }
}
