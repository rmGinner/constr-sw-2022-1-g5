package br.rmginner.controllers.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.rmginner.controllers.ContentsController;
import br.rmginner.dtos.ContentDto;
import br.rmginner.services.ContentService;

@RestController
public class ContentsControllerImpl implements ContentsController{
	
	private final ContentService contentService;

    public ContentsControllerImpl(ContentService contentService) {
        this.contentService = contentService;
    }

	@Override
	public ResponseEntity<List<ContentDto>> getContents() {
		return ResponseEntity.ok(this.contentService.getAllContents());
	}

	@Override
	public ResponseEntity<ContentDto> createContent(ContentDto contentDto) {
		return ResponseEntity.ok(this.contentService.createContent(contentDto));
	}
}
