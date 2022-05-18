package br.rmginner.services.impl;

import br.rmginner.dtos.ClassDto;
import br.rmginner.services.ClassService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Override
    public ClassDto createClass(ClassDto classDto) {
        return classDto;
    }

    @Override
    public List<ClassDto> getAllClasses() {
        return List.of(ClassDto.builder().name("Test").date(LocalDateTime.now()).build());
    }
}
