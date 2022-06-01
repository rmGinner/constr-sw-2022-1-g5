package br.rmginner.controllers;

import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Contents Service", value = "test", description = "Service to manage contents and their contents.")
@RequestMapping("{id}/contents")
public interface ContentsController {

    @ApiOperation("Get all contents")
    @GetMapping
    ResponseEntity<List<ContentDto>> getAllContents();

    @ApiOperation("Get content by ID")
    @GetMapping("{id}")
    ResponseEntity<ContentDto> getContentById(@PathVariable String id);

    @ApiOperation("Create a new content.")
    @PostMapping
    ResponseEntity<ContentDto> createContent(ContentDto contentDto);
    
    @ApiOperation("Delete a content by ID.")
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteContent(@PathVariable String id);
    
    @ApiOperation("Patch a content by ID.")
    @PatchMapping("{id}")
    ResponseEntity<ContentDto> PatchContent(@PathVariable String id);
    
    
}
