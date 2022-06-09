package br.rmginner.controllers;

import br.rmginner.dtos.ContentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Contents Service", value = "test", description = "Service to manage lesson contents.")
@RequestMapping("/contents")
public interface ContentsController {

    @ApiResponses({
            @ApiResponse(code = 204, message = "No contents found."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @ResponseStatus(code = HttpStatus.OK, reason = "Get all contents")
    @GetMapping
    ResponseEntity<List<ContentDto>> getContentsBy();

    @ApiResponses({
            @ApiResponse(code = 204, message = "Content was not found."),
            @ApiResponse(code = 401, message = "Unauthorized."),
            @ApiResponse(code = 404, message = "Not found route."),
    })
    @ResponseStatus(code = HttpStatus.OK, reason = "Get content by ID")
    @GetMapping("{id}")
    ResponseEntity<ContentDto> getContentById(@PathVariable String id);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found route"),
    })
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Create a new content.")
    @PostMapping
    ResponseEntity<ContentDto> createContent(@Valid @RequestBody ContentDto contentDto);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found route"),
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Delete a content by ID.")
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteContent(@PathVariable String id);

    @ApiResponses({
            @ApiResponse(code = 400, message = "Business rule error."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found route"),
    })
    @ResponseStatus(code = HttpStatus.OK, reason = "Patch a content by ID.")
    @PatchMapping("{id}")
    ResponseEntity<ContentDto> patchContent(@PathVariable String id, @Valid @RequestBody ContentDto contentDto);

}
