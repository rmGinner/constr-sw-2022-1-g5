package br.rmginner.services.impl;

import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import br.rmginner.entities.Content;
import br.rmginner.entities.Lesson;
import br.rmginner.remotes.buildingservice.dto.BuildingDto;
import br.rmginner.remotes.classservice.dto.ClassDto;
import br.rmginner.repositories.LessonsRepository;
import br.rmginner.services.ContentService;
import br.rmginner.services.LessonService;
import br.rmginner.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonsRepository repository;

    @Autowired
    private ContentService contentService;

    @Override
    public LessonDto save(LessonDto dto) {
        final var lessonOpt = Objects.nonNull(dto.getId()) ? this.repository.findById(dto.getId()) : Optional.empty();

        if (lessonOpt.isEmpty()) {
            dto.setId(null);
        }

        List<ContentDto> savedContents = null;

        if (!CollectionUtils.isEmpty(dto.getContents())) {
            savedContents = this.contentService.saveAll(dto.getContents());
        }

        dto.setContents(savedContents);

        final var lessonEntity = this.repository.save(this.convertDtoToEntity(dto));

        dto.setId(lessonEntity.getId());
        dto.setClassDto(mockClassFromRemoteService(lessonEntity.getClassId()));
        dto.setBuilding(mockBuildingFromRemoteService(lessonEntity.getBuildingId()));

        return dto;
    }

    @Override
    public List<LessonDto> getBy(String classId) {
        final var example = Example.of(Lesson.builder().isEnabled(true).classId(classId).build());

        return repository.findAll(example)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LessonDto> getLessonById(String id) {
        return this.repository.findById(id).filter(Lesson::isEnabled).map(this::convertEntityToDto);
    }

    @Override
    public void deleteById(String id) {
        final var lessonOpt = this.repository.findById(id);

        if (lessonOpt.isPresent()) {
            final var updatedLesson = lessonOpt.get();
            updatedLesson.setEnabled(false);

            this.repository.save(updatedLesson);
        }
    }

    @Override
    public LessonDto patchUpdate(LessonDto lessonDto) {
        final var foundLessonOpt = this.repository.findOne(Example.of(Lesson.builder().isEnabled(true).id(lessonDto.getId()).build()));

        if (foundLessonOpt.isEmpty()) {
            throw new BusinessException("Nenhuma aula encontrada para este ID.");
        }

        var lessonUpdated = foundLessonOpt.get();

        this.updateEntityFromDto(lessonDto, lessonUpdated);

        lessonUpdated = this.repository.save(lessonUpdated);

        return convertEntityToDto(lessonUpdated);
    }

    private void updateEntityFromDto(LessonDto lessonDto, Lesson lesson) {
        lesson.setBuildingId(Objects.nonNull(lessonDto.getBuilding()) ? lessonDto.getBuilding().getId() : lesson.getBuildingId());
        lesson.setClassId(Objects.nonNull(lessonDto.getClassDto()) ? lessonDto.getClassDto().getId() : lesson.getClassId());
        lesson.setDate(Objects.nonNull(lessonDto.getDate()) ? lessonDto.getDate() : lesson.getDate());
        lesson.setName(StringUtils.isEmpty(lessonDto.getName()) ? lesson.getName() : lessonDto.getName());
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
                .map(this::convertContentDtoToEntity
                )
                .collect(Collectors.toList());

        return Lesson.builder()
                .id(dto.getId())
                .name(dto.getName())
                .date(dto.getDate())
                .classId(dto.getClassDto().getId())
                .buildingId(dto.getBuilding().getId())
                .contents(contentEntities)
                .isEnabled(true)
                .build();
    }

    private Content convertContentDtoToEntity(ContentDto contentDto) {
        return Content.builder()
                .name(contentDto.getName())
                .type(contentDto.getType())
                .link(contentDto.getLink())
                .build();
    }

}
