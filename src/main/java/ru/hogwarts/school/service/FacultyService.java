package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {

    private final Map<Long, Faculty> allFaculty = new HashMap();
    private Long generateId = 0L;

    public Faculty createFaculty(Faculty faculty){
        faculty.setId(++generateId);
        allFaculty.put(generateId,faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id){
        return allFaculty.get(id);
    }

    public Faculty updateFaculty(Faculty faculty){
        if(!allFaculty.containsKey(faculty.getId())){
            return null;
        }
        allFaculty.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id){
        return allFaculty.remove(id);
    }

    public Collection<Faculty> filterFacultyByColor(String color){
        return allFaculty.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();
    }
}
