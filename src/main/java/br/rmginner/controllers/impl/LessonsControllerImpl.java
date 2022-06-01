package br.rmginner.controllers.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.rmginner.controllers.LessonsController;
import br.rmginner.dtos.LessonDto;
import br.rmginner.services.LessonService;

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
    public ResponseEntity<LessonDto> createLesson(@Valid @RequestBody LessonDto classDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.create(classDto));
    }

	@Override
	public ResponseEntity<Void> deleteLesson(String id) {
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@Override
	public ResponseEntity<LessonDto> PatchLesson(String id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.build();
	}
}
