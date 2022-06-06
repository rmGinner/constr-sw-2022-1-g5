package br.rmginner.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.rmginner.dtos.LessonDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Lessons Service", value = "test", description = "Service to manage lessons and their contents.")
@RequestMapping
public interface LessonsController {

    @ApiOperation("Get all lessons")
    @GetMapping
    ResponseEntity<List<LessonDto>> getLessonsBy(@Nullable @RequestParam String classId);

    @ApiOperation("Get lesson by ID")
    @GetMapping("{id}")
    ResponseEntity<LessonDto> getLessonById(@PathVariable String id);

    @ApiOperation("Create a new lesson.")
    @PostMapping
    ResponseEntity<LessonDto> createLesson(LessonDto classDto);
    
    @ApiOperation("Delete a lesson by ID.")
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteLesson(@PathVariable String id);
    
    @ApiOperation("Patch a lesson by ID.")
    @PatchMapping("{id}")
    ResponseEntity<LessonDto> PatchLesson(@PathVariable String id);   
    
}
