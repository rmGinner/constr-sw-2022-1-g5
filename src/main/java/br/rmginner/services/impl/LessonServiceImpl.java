package br.rmginner.services.impl;

import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import br.rmginner.entities.Content;
import br.rmginner.entities.Lesson;
import br.rmginner.remotes.buildingservice.dto.BuildingDto;
import br.rmginner.remotes.classservice.dto.ClassDto;
import br.rmginner.repositories.LessonsRepository;
import br.rmginner.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonsRepository repository;

    @Override
    public LessonDto create(LessonDto dto) {
        final var lessonEntity = this.repository.save(this.convertDtoToEntity(dto));

        dto.setId(lessonEntity.getId());
        dto.setClassDto(mockClassFromRemoteService(lessonEntity.getClassId()));
        dto.setBuilding(mockBuildingFromRemoteService(lessonEntity.getBuildingId()));

        return dto;
    }

    @Override
    public List<LessonDto> getBy(String classId) {
        final var example = Example.of(Lesson.builder().classId(classId).build());

        return repository.findAll(example)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LessonDto> getLessonById(String id) {
        return this.repository.findById(id).map(this::convertEntityToDto);
    }

    private BuildingDto mockBuildingFromRemoteService(String buildingId) {
        return BuildingDto.builder()
                .id(buildingId)
                .name("Test")
                .build();
    }

    private ClassDto mockClassFromRemoteService(String classId) {
        return ClassDto.builder()
                .id(classId)
                .name("Test")
                .build();
    }

    private LessonDto convertEntityToDto(Lesson lesson) {
        return LessonDto.builder()
                .id(lesson.getId())
                .name(lesson.getName())
                .date(lesson.getDate())
                .building(mockBuildingFromRemoteService(lesson.getBuildingId()))
                .classDto(mockClassFromRemoteService(lesson.getClassId()))
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

    private Lesson convertDtoToEntity(LessonDto dto) {
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
                .classId(dto.getClassDto().getId())
                .buildingId(dto.getBuilding().getId())
                .contents(contentEntities)
                .build();
    }

}
