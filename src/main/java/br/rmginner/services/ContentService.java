package br.rmginner.services;

import br.rmginner.dtos.ContentDto;

import java.util.List;
import java.util.Optional;

public interface ContentService {

    ContentDto create(ContentDto dto);

    List<ContentDto> getBy(String contentId);

    Optional<ContentDto> getContentById(String id);

    List<ContentDto> saveAll(List<ContentDto> contentDtoList);
}
