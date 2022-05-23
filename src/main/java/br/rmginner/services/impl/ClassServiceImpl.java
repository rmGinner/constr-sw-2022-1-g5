package br.rmginner.services.impl;

import br.rmginner.dtos.ClassDto;
import br.rmginner.dtos.ContentDto;
import br.rmginner.entities.Aula;
import br.rmginner.entities.Conteudo;
import br.rmginner.repositories.AulasRepository;
import br.rmginner.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private AulasRepository aulasRepository;

    @Override
    public ClassDto createClass(ClassDto classDto) {
        final var contentEntity = Conteudo.builder()
                .nome(classDto.getContent().getName())
                .tipo(classDto.getContent().getType())
                .link(classDto.getContent().getLink())
                .build();

        final var classEntity = Aula.builder()
                .id(classDto.getId())
                .nome(classDto.getName())
                .data(classDto.getDate())
                .conteudo(contentEntity)
                .build();

        this.aulasRepository.save(classEntity);

        classDto.setId(classEntity.getId());

        return classDto;
    }

    @Override
    public List<ClassDto> getAllClasses() {
        return aulasRepository.findAll()
                .stream()
                .map(aula -> ClassDto.builder()
                        .id(aula.getId())
                        .name(aula.getNome())
                        .date(aula.getData())
                        .content(
                                ContentDto.builder()
                                        .name(aula.getConteudo().getNome())
                                        .type(aula.getConteudo().getTipo())
                                        .link(aula.getConteudo().getLink())
                                        .build()
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }

}
