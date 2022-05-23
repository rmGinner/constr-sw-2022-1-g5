package br.rmginner.services;

import br.rmginner.dtos.ContentDto;

import java.util.List;

public interface ContentService {
	ContentDto createContent(ContentDto contentDto);

    List<ContentDto> getAllContents();
}
