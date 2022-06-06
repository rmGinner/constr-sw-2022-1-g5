package br.rmginner.controllers.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.rmginner.controllers.ContentsController;
import br.rmginner.dtos.ContentDto;
import br.rmginner.services.ContentService;

@RestController
public class ContentsControllerImpl implements ContentsController {

    private final ContentService service;

    public ContentsControllerImpl(ContentService contentService) {
        this.service = contentService;
    }

    @Override
    public ResponseEntity<List<ContentDto>> getContentsBy(String contentId) {
        final var foundContents = this.service.getBy(contentId);
        return CollectionUtils.isEmpty(foundContents) ? ResponseEntity.noContent().build() : ResponseEntity.ok(foundContents);
    }

    @Override
    public ResponseEntity<ContentDto> getContentById(String id) {
        final var contentFound = this.service.getContentById(id);

        return contentFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<ContentDto> createContent(@Valid @RequestBody ContentDto contentDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.create(contentDto));
    }

	@Override
	public ResponseEntity<Void> deleteContent(String id) {
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@Override
	public ResponseEntity<ContentDto> patchContent(String id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.build();
	}
}
