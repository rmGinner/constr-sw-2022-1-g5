package br.rmginner.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.rmginner.BaseTestContainers;
import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import br.rmginner.entities.Content;
import br.rmginner.entities.Lesson;
import br.rmginner.entities.enums.ContentType;
import br.rmginner.remotes.buildingservice.dto.BuildingDto;
import br.rmginner.remotes.classservice.dto.ClassDto;
import br.rmginner.repositories.LessonsRepository;
import br.rmginner.services.LessonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

@SpringBootTest
class LessonServiceImplTest extends BaseTestContainers {

    private static final String TEST_NAME = "Test";

    private static final String TEST_ID = "102asd12nasd12e19e1";

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2022, 5, 4, 20, 13, 0);

    private static final String MOCK_BUILDING_ID = "testeasd";

    private static final String MOCK_CLASS_ID = "testeasd";

    @MockBean
    private LessonsRepository repository;

    @Autowired
    private LessonService service;

    @Test
    void shouldCreateLesson() {
        final var savedLesson = getTestLesson();

        savedLesson.setId(TEST_ID);

        Mockito.when(repository.save(ArgumentMatchers.any(Lesson.class)))
                .thenReturn(savedLesson);

        final var createdLessonDto = service.save(getTestLessonDto());

        Assertions.assertEquals(TEST_ID, createdLessonDto.getId());
    }

    @Test
    void shouldGetAllLessons() {
        final var lessons = List.of(getTestLesson());
        final var lessonDtoListToReturn = List.of(getTestLessonDto());
        final var example = Example.of(Lesson.builder().classId(MOCK_CLASS_ID).build());

        Mockito.when(repository.findAll(ArgumentMatchers.any(Example.class)))
                .thenReturn(lessons);

        final var lessonDtoList = service.getBy(MOCK_CLASS_ID);

        Assertions.assertEquals(1, lessonDtoList.size());
        Assertions.assertEquals(lessonDtoListToReturn, lessonDtoList);
    }

    @Test
    void shouldGetLessonById() {
        final var lesson = getTestLesson();

        Mockito.when(repository.findOne(ArgumentMatchers.any(Example.class)))
                .thenReturn(Optional.of(lesson));

        final var lessonDtoOptional = service.getLessonById(TEST_ID);

        Assertions.assertTrue(lessonDtoOptional.isPresent());
    }

    private Lesson getTestLesson() {
        return Lesson.builder()
                .name(TEST_NAME)
                .date(TEST_DATE)
                .buildingId(MOCK_BUILDING_ID)
                .classId(MOCK_CLASS_ID)
                .contents(
                        List.of(
                                Content.builder()
                                        .name(TEST_NAME)
                                        .type(ContentType.DOC)
                                        .build()
                        )
                )
                .isEnabled(true)
                .build();
    }

    private LessonDto getTestLessonDto() {
        return LessonDto.builder()
                .name(TEST_NAME)
                .date(TEST_DATE)
                .building(mockBuildingFromRemoteService())
                .classDto(mockClassFromRemoteService())
                .contents(
                        List.of(
                                ContentDto.builder()
                                        .name(TEST_NAME)
                                        .type(ContentType.DOC)
                                        .build()
                        )
                )
                .build();
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

}