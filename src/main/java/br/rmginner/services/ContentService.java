package br.rmginner.services;

import java.util.List;
import java.util.Optional;

import br.rmginner.dtos.ContentDto;

public interface ContentService {

	ContentDto create(ContentDto dto);

    List<ContentDto> getBy(String contentId);

    Optional<ContentDto> getContentById(String id);
}