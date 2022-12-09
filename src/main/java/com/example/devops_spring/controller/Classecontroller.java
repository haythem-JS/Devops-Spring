package com.example.devops_spring.controller;

import com.example.devops_spring.entity.ClasseEntity;
import com.example.devops_spring.repository.ClasseRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classe")
@CrossOrigin(origins = "*")
public class Classecontroller {

    @Autowired
    ClasseRepository classeRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ClasseEntity>> getClasses() {
        try {
            return new ResponseEntity<>(classeRepository.findAll(), HttpStatus.OK);
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
    public ResponseEntity<ClasseEntity> getClasseById(@PathVariable("id") long id) {
        try {
            // check if book exist in database
            ClasseEntity classeObj = getClasseRec(id);

            if (classeObj != null) {
                return new ResponseEntity<>(classeObj, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     *
     * @param classe
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity<ClasseEntity> newClasse(@RequestBody ClasseEntity classe) {
        ClasseEntity newClasse = classeRepository
                .save(ClasseEntity.builder()
                        .name(classe.getName())
                        .level(classe.getLevel())
                        .teacher(classe.getTeacher())
                        .build());
        return new ResponseEntity<>(newClasse, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @param classeEntity
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ClasseEntity> updateClasse(@PathVariable("id") long id, @RequestBody ClasseEntity classeEntity) {

        // check ifbook exist in database
        ClasseEntity classeObj = getClasseRec(id);

        if (classeObj != null) {
            classeObj.setName(classeEntity.getName());
            classeObj.setLevel(classeEntity.getLevel());
            classeObj.setTeacher(classeEntity.getTeacher());
            return new ResponseEntity<>(classeRepository.save(classeObj), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteClasseById(@PathVariable("id") long id) {
        try {
            // check if book exist in database
            ClasseEntity classe = getClasseRec(id);

            if (classe != null) {
                classeRepository.deleteById(id);
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
     * @return BookEntity
     */
    private ClasseEntity getClasseRec(long id) {
        Optional<ClasseEntity> classeObj = classeRepository.findById(id);

        if (classeObj.isPresent()) {
            return classeObj.get();
        }
        return null;
    }

}
