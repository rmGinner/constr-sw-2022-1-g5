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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private static final String MOCK_BUILDING_ID = "testeasd";

    private static final String MOCK_CLASS_ID = "testeasd";

    @Autowired
    private LessonsRepository aulasRepository;

    @Override
    public LessonDto create(LessonDto dto) {
        final var lessonEntity = this.aulasRepository.save(this.convertDtoToEntity(dto));

        dto.setId(lessonEntity.getId());
        dto.setClassDto(mockClassFromRemoteService());
        dto.setBuilding(mockBuildingFromRemoteService());

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

    private BuildingDto mockBuildingFromRemoteService() {
        return BuildingDto.builder()
                .id(MOCK_BUILDING_ID)
                .name("Test")
                .build();
    }

    private ClassDto mockClassFromRemoteService() {
        return ClassDto.builder()
                .id(MOCK_CLASS_ID)
                .name("Test")
                .build();
    }

    private LessonDto convertEntityToDto(Lesson lesson) {
        return LessonDto.builder()
                .id(lesson.getId())
                .name(lesson.getName())
                .date(lesson.getDate())
                .building(mockBuildingFromRemoteService())
                .classDto(mockClassFromRemoteService())
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
                .contents(contentEntities)
                .build();
    }

}
