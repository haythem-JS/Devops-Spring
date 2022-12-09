package com.example.devops_spring.controller;


import com.example.devops_spring.entity.TeacherEntity;
import com.example.devops_spring.repository.TeacherRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "*")
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/all")
    public ResponseEntity<List<TeacherEntity>> getTeachers() {
        try {
            return new ResponseEntity<>(teacherRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeacherEntity> getTeacherById(@PathVariable("id") long id) {
        try {
            // check if book exist in database
            TeacherEntity teacherObj = getTeacherRec(id);

            if (teacherObj != null) {
                return new ResponseEntity<>(teacherObj, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param teacher
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity<TeacherEntity> newTeacher(@RequestBody TeacherEntity teacher) {
        TeacherEntity newTeacher = teacherRepository
                .save(TeacherEntity.builder()
                        .name(teacher.getName())
                        .subject(teacher.getSubject())
                        .build());
        return new ResponseEntity<>(newTeacher, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @param teacherEntity
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<TeacherEntity> updateTeacher(@PathVariable("id") long id, @RequestBody TeacherEntity teacherEntity) {

        // check ifbook exist in database
        TeacherEntity teacherObj = getTeacherRec(id);

        if (teacherObj != null) {
            teacherObj.setName(teacherEntity.getName());
            teacherObj.setSubject(teacherEntity.getSubject());
            return new ResponseEntity<>(teacherRepository.save(teacherObj), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTeacherById(@PathVariable("id") long id) {
        try {
            // check if book exist in database
            TeacherEntity classe = getTeacherRec(id);

            if (classe != null) {
                teacherRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param id
     * @return TeacherEntity
     */
    private TeacherEntity getTeacherRec(long id) {
        Optional<TeacherEntity> teacherObj = teacherRepository.findById(id);

        if (teacherObj.isPresent()) {
            return teacherObj.get();
        }
        return null;
    }
}
