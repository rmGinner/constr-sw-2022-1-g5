package br.rmginner.services;

import java.util.List;
import java.util.Optional;

import br.rmginner.dtos.ContentDto;

public interface ContentService {

    ContentDto create(ContentDto dto);

    List<ContentDto> saveAll(List<ContentDto> contentDtoList);
    
    List<ContentDto> getBy();

    Optional<ContentDto> getContentById(String id);
    
    void deleteById(String id);

    ContentDto patchUpdate(ContentDto patchContentDto);
}
