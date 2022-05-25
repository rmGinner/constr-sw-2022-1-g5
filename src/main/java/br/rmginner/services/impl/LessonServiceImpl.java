package br.rmginner.services.impl;

import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import br.rmginner.entities.Content;
import br.rmginner.entities.Lesson;
import br.rmginner.repositories.LessonsRepository;
import br.rmginner.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonsRepository aulasRepository;

    @Override
    public LessonDto create(LessonDto dto) {
        final var lessonEntity = this.aulasRepository.save(this.convertDtoToEntity(dto));

        dto.setId(lessonEntity.getId());

        return dto;
    }

    @Override
    public List<LessonDto> getAll() {
        return aulasRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LessonDto> getLessonById(String id) {
        return this.aulasRepository.findById(id).map(this::convertEntityToDto);
    }

    public LessonDto convertEntityToDto(Lesson lesson) {
        return LessonDto.builder()
                .id(lesson.getId())
                .name(lesson.getName())
                .date(lesson.getDate())
                .contents(
                        lesson.getContents()
                                .stream()
                                .map(content -> ContentDto.builder()
                                        .name(content.getName())
                                        .type(content.getType())
                                        .link(content.getLink())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    public Lesson convertDtoToEntity(LessonDto dto) {
        final var contentEntities = dto.getContents()
                .stream()
                .map(contentDto -> Content.builder()
                        .name(contentDto.getName())
                        .type(contentDto.getType())
                        .link(contentDto.getLink())
                        .build()
                )
                .collect(Collectors.toList());

        return Lesson.builder()
                .id(dto.getId())
                .name(dto.getName())
                .date(dto.getDate())
                .contents(contentEntities)
                .build();
    }

}
