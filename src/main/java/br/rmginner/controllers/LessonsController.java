package br.rmginner.controllers;

import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Lessons Service", value = "lessons", description = "Service to manage lessons and their contents.")
@RequestMapping("/lessons")
public interface LessonsController {

    @ApiResponses({
            @ApiResponse(code = 204, message = "No lessons found."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @ResponseStatus(code = HttpStatus.OK, reason = "Get all lessons")
    @GetMapping
    ResponseEntity<List<LessonDto>> getLessonsBy(
            @ApiParam(name = "classId",value = "ID of class")
            @Nullable
            @RequestParam(required = false) String classId
    );

    @ApiResponses({
            @ApiResponse(code = 204, message = "Lesson was not found."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @ResponseStatus(code = HttpStatus.OK, reason = "Get lesson by ID")
    @GetMapping("{id}")
    ResponseEntity<LessonDto> getLessonById(@PathVariable String id);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found route"),
    })
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Create a new lesson.")
    @PostMapping
    ResponseEntity<LessonDto> createLesson(@Valid @RequestBody LessonDto lessonDto);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Delete a lesson by ID.")
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteLesson(@PathVariable String id);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @ResponseStatus(code = HttpStatus.OK, reason = "Patch a lesson by ID.")
    @PatchMapping("{id}")
    ResponseEntity<LessonDto> patchLesson(@PathVariable String id, @RequestBody LessonDto lessonDto);

    @ApiResponses({
            @ApiResponse(code = 204, message = "No contents from lesson found."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @ResponseStatus(code = HttpStatus.OK, reason = "Get all contents from lesson.")
    @GetMapping("{lessonId}/contents")
    ResponseEntity<List<ContentDto>> getAllContentsFromLesson(@PathVariable String lessonId);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found route"),
    })
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Create a new content for a lesson.")
    @PostMapping("{lessonId}/contents")
    ResponseEntity<LessonDto> createLessonContent(@PathVariable String lessonId, @Valid @RequestBody ContentDto contentDto);

}
