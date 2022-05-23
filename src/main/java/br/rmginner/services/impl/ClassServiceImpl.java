package br.rmginner.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rmginner.dtos.ClassDto;
import br.rmginner.entities.Aula;
import br.rmginner.repositories.AulasRepository;
import br.rmginner.services.ClassService;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private AulasRepository aulasRepository;

    @Override
    public ClassDto createClass(ClassDto classDto) {
        final var classEntity = Aula.builder()
                ._id(classDto.getId())
                .nome(classDto.getName())
                .data(classDto.getDate())
                .build();

        this.aulasRepository.save(classEntity);

        return classDto;
    }

    @Override
    public List<ClassDto> getAllClasses() {
        return aulasRepository.findAll()
                .stream()
                .map(aula -> ClassDto.builder().name(aula.getNome()).date(aula.getData()).build())
                .collect(Collectors.toList());
    }

}
