package br.rmginner.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rmginner.dtos.ClassDto;
import br.rmginner.repositories.AulasRepository;
import br.rmginner.services.ClassService;

@Service
public class ClassServiceImpl implements ClassService {
    
	@Autowired
	private AulasRepository aulasRepository;
	
	@Override
    public ClassDto createClass(ClassDto classDto) {
		return classDto;
	}

    @Override
    public List<ClassDto> getAllClasses() {
        return aulasRepository.findAll().stream().map(aula -> ClassDto.builder().name(aula.getNome()).date(aula.getData()).build()).collect(Collectors.toList());
    }
    
    
    
}
