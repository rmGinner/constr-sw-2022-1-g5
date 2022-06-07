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

        List<ContentDto> savedContents = dto.getContents();

        dto.setContents(null);

        final var lessonEntity = this.repository.save(this.convertDtoToEntity(dto));


        if (!CollectionUtils.isEmpty(savedContents)) {
            dto.setContents(this.contentService.saveAll(savedContents));
        }

        dto.setId(lessonEntity.getId());

        this.repository.save(this.convertDtoToEntity(dto));


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
        final var example = Example.of(Lesson.builder().isEnabled(true).id(id).build());

        return this.repository.findOne(example).map(this::convertEntityToDto);
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

    @Override
    public LessonDto getAllContentsFromLesson(String lessonId) {
        final var foundLesson = this.repository.findOne(Example.of(Lesson.builder().isEnabled(true).id(lessonId).build()));

        if (foundLesson.isPresent() && !CollectionUtils.isEmpty(foundLesson.get().getContents())) {
            final var lesson = foundLesson.get();
            final var lessonEnabledContents = foundLesson.get().getContents().stream().filter(Content::isEnabled).collect(Collectors.toList());

            lesson.setContents(lessonEnabledContents);

            return convertEntityToDto(lesson);
        }

        return null;
    }

    @Override
    public LessonDto saveContentForLesson(String lessonId, ContentDto lessonContent) {
        final var foundLessonOpt = this.repository.findOne(Example.of(Lesson.builder().isEnabled(true).id(lessonId).build()));

        if (Objects.nonNull(lessonContent.getId()) && Objects.nonNull(contentService.getContentById(lessonContent.getId()))) {
            throw new BusinessException("Já existe um conteúdo com este ID.");
        }

        if (foundLessonOpt.isPresent()) {
            final var foundLesson = foundLessonOpt.get();
            final var savedContent = contentService.saveAll(List.of(lessonContent));

            foundLesson.getContents().add(convertContentDtoToEntity(savedContent.get(0)));

            final var savedLesson = this.repository.save(foundLesson);

            return convertEntityToDto(savedLesson);
        }

        return null;
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
                                        .id(content.getId())
                                        .name(content.getName())
                                        .type(content.getType())
                                        .link(content.getLink())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    private Lesson convertDtoToEntity(LessonDto dto) {
        final List<Content> contentEntities = CollectionUtils.isEmpty(dto.getContents()) ? List.of() :
                dto.getContents()
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
                .id(contentDto.getId())
                .name(contentDto.getName())
                .type(contentDto.getType())
                .link(contentDto.getLink())
                .build();
    }
}
