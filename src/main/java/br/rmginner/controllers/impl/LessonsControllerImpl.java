package br.rmginner.controllers.impl;

import br.rmginner.controllers.LessonsController;
import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import br.rmginner.services.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class LessonsControllerImpl implements LessonsController {

    private final LessonService service;

    public LessonsControllerImpl(LessonService classService) {
        this.service = classService;
    }

    @Override
    public ResponseEntity<List<LessonDto>> getLessonsBy(String classId) {
        final var foundLessons = this.service.getBy(classId);
        return CollectionUtils.isEmpty(foundLessons) ? ResponseEntity.noContent().build() : ResponseEntity.ok(foundLessons);
    }

    @Override
    public ResponseEntity<LessonDto> getLessonById(String id) {
        final var lessonFound = this.service.getLessonById(id);

        return lessonFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto classDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.save(classDto));
    }

    @Override
    public ResponseEntity<Void> deleteLesson(String id) {
        this.service.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<LessonDto> patchLesson(String id, LessonDto classDto) {
        classDto.setId(id);

        return ResponseEntity
                .ok(this.service.patchUpdate(classDto));
    }

    @Override
    public ResponseEntity<LessonDto> getAllContentsFromLesson(String lessonId) {
        final var foundLesson = this.service.getAllContentsFromLesson(lessonId);

        return Objects.isNull(foundLesson) ? ResponseEntity.noContent().build() : ResponseEntity.ok(foundLesson);
    }

    @Override
    public ResponseEntity<LessonDto> createLessonContent(String lessonId, ContentDto contentDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.saveContentForLesson(lessonId, contentDto));
    }
}
