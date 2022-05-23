package br.rmginner.controllers.impl;

import br.rmginner.controllers.ClassesController;
import br.rmginner.dtos.ClassDto;
import br.rmginner.services.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClassesControllerImpl implements ClassesController {

    private final ClassService classService;

    public ClassesControllerImpl(ClassService classService) {
        this.classService = classService;
    }

    @Override
    public ResponseEntity<List<ClassDto>> getClasses() {
        return ResponseEntity.ok(this.classService.getAllClasses());
    }

    @Override
    public ResponseEntity<ClassDto> createClass(@Valid @RequestBody ClassDto classDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.classService.createClass(classDto));
    }
}
