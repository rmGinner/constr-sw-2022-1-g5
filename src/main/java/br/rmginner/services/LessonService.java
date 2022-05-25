package br.rmginner.services;

import br.rmginner.dtos.LessonDto;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    LessonDto create(LessonDto dto);

    List<LessonDto> getAll();

    Optional<LessonDto> getLessonById(String id);
}
