package com.example.devops_spring.controller;


import com.example.devops_spring.entity.StudentEntity;
import com.example.devops_spring.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class Studentcontroller {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/all")
    public ResponseEntity<List<StudentEntity>> getStudents() {
        try {
            return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
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
    public ResponseEntity<StudentEntity> getStudentById(@PathVariable("id") long id) {
        try {
            // check if book exist in database
            StudentEntity studentObj = getStudentRec(id);

            if (studentObj != null) {
                return new ResponseEntity<>(studentObj, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param student
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity<StudentEntity> newStudent(@RequestBody StudentEntity student) {
        StudentEntity newStudent = studentRepository
                .save(StudentEntity.builder()
                        .name(student.getName())
                        .age(student.getAge())
                        .classe(student.getClasse())
                        .build());
        return new ResponseEntity<>(newStudent, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @param studentEntity
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable("id") long id, @RequestBody StudentEntity studentEntity) {

        // check ifbook exist in database
        StudentEntity studentObj = getStudentRec(id);

        if (studentObj != null) {
            studentObj.setName(studentEntity.getName());
            studentObj.setAge(studentEntity.getAge());
            studentObj.setClasse(studentEntity.getClasse());
            return new ResponseEntity<>(studentRepository.save(studentObj), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable("id") long id) {
        try {
            // check if book exist in database
            StudentEntity student = getStudentRec(id);

            if (student != null) {
                studentRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @return StudentEntity
     */
    private StudentEntity getStudentRec(long id) {
        Optional<StudentEntity> studentObj = studentRepository.findById(id);

        if (studentObj.isPresent()) {
            return studentObj.get();
        }
        return null;
    }
}
