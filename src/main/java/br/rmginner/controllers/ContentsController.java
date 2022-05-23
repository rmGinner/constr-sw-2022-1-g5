package br.rmginner.controllers;

import br.rmginner.dtos.ContentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "contents")
public interface ContentsController {
	
	@GetMapping
    ResponseEntity<List<ContentDto>> getContents();

    @PostMapping
    ResponseEntity<ContentDto> createContent(ContentDto contentDto);
}
