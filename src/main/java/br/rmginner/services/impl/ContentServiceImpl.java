package br.rmginner.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rmginner.dtos.ContentDto;
import br.rmginner.entities.Conteudo;
import br.rmginner.repositories.ConteudosRepository;
import br.rmginner.services.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ConteudosRepository conteudosRepository;

    @Override
    public ContentDto createContent(ContentDto contentDto) {
        final var contentEntity = Conteudo.builder()
                .link(contentDto.getLink())
                .nome(contentDto.getName())
                .id(contentDto.getId())
                .build();

        this.conteudosRepository.save(contentEntity);

        return contentDto;
    }

    @Override
    public List<ContentDto> getAllContents() {
        return conteudosRepository
                .findAll()
                .stream()
                .map(conteudo -> ContentDto.builder().name(conteudo.getNome()).type(conteudo.getTipo())
                        .link(conteudo.getLink()).build())
                .collect(Collectors.toList());
    }
}
