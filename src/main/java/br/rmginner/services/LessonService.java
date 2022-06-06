package br.rmginner.services;

import br.rmginner.dtos.LessonDto;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    LessonDto save(LessonDto dto);

    List<LessonDto> getBy(String classId);

    Optional<LessonDto> getLessonById(String id);

    void deleteById(String id);

    LessonDto patchUpdate(LessonDto patchLessonDto);
}

