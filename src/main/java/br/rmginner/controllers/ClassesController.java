package br.rmginner.controllers;

import br.rmginner.dtos.ClassDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "classes")
public interface ClassesController {

    @GetMapping
    ResponseEntity<List<ClassDto>> getClasses();

    @PostMapping
    ResponseEntity<ClassDto> createClass(ClassDto classDto);
}
