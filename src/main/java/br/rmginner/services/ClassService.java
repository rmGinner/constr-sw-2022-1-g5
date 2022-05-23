package br.rmginner.services;

import br.rmginner.dtos.ClassDto;

import java.util.List;

public interface ClassService {

    ClassDto createClass(ClassDto classDto);

    List<ClassDto> getAllClasses();
}
