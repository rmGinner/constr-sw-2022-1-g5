package br.rmginner.controllers;

import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import io.swagger.annotations.Api;
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
    @GetMapping
    ResponseEntity<List<LessonDto>> getLessonsBy(@Nullable @RequestParam String classId);

    @ApiResponses({
            @ApiResponse(code = 204, message = "Lesson was not found."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @GetMapping("{id}")
    ResponseEntity<LessonDto> getLessonById(@PathVariable String id);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found route"),
    })
    @PostMapping
    ResponseEntity<LessonDto> createLesson(@Valid @RequestBody LessonDto classDto);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteLesson(@PathVariable String id);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @PatchMapping("{id}")
    ResponseEntity<LessonDto> patchLesson(@PathVariable String id, @RequestBody LessonDto classDto);

    @ApiResponses({
            @ApiResponse(code = 204, message = "No contents from lesson found."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @GetMapping("{lessonId}/contents")
    ResponseEntity<List<ContentDto>> getAllContentsFromLesson(@PathVariable String lessonId);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found route"),
    })
    @PostMapping("{lessonId}/contents")
    ResponseEntity<LessonDto> createLessonContent(@PathVariable String lessonId, @Valid @RequestBody ContentDto contentDto);

}
