package br.rmginner.controllers.impl;

import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import br.rmginner.entities.enums.ContentType;
import br.rmginner.remotes.buildingservice.dto.BuildingDto;
import br.rmginner.remotes.classservice.dto.ClassDto;
import br.rmginner.services.ContentService;
import br.rmginner.services.LessonService;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.parsing.Parser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebMvcTest
class LessonsControllerImplTest {
    private static final String LESSONS_ENDPOINT = "/lessons";

    private static final String TEST_NAME = "Test";

    private static final String TEST_ID = "102asd12nasd12e19e1";

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2022, 5, 4, 20, 13, 0);

    private static final String TEST_DATE_AS_STRING = "2022-05-04T20:13:00";

    private static final String TEST_REQUIRED_DATE_MSG = "Data obrigatória";

    private static final String TEST_REQUIRED_NAME_MSG = "Nome obrigatório";

    private static final String TEST_INVALID_JSON_MSG = "Invalid request body format.";

    private static final String TEST_INVALID_JSON = "{ \"date\":\"tests\"}";

    private static final String MOCK_BUILDING_ID = "testeasd";

    private static final String MOCK_CLASS_ID = "testeasd";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService service;

    @MockBean
    private ContentService contentService;

    @BeforeEach
    void setUp() {
        RestAssured.registerParser("null", Parser.JSON);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldReturnListOfLessons() {
        Mockito.when(service.getBy(null))
                .thenReturn(List.of(getTestLesson()));

        RestAssuredMockMvc.given()
                .auth().none()
                .and()
                .get(LESSONS_ENDPOINT)
                .then()
                .status(HttpStatus.OK)
                .body("$.size()", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo(TEST_NAME))
                .body("[0].date", Matchers.equalTo(TEST_DATE_AS_STRING));
    }

    @Test
    void shouldReturnOneLesson() {
        Mockito.when(service.getLessonById(TEST_ID)).thenReturn(Optional.of(getTestLesson()));

        RestAssuredMockMvc.given()
                .auth().none()
                .and()
                .get(LESSONS_ENDPOINT + TEST_ID)
                .then()
                .status(HttpStatus.OK);
    }

    @Test
    void shouldCreateLesson() {
        Mockito.doReturn(getTestLesson())
                .when(service)
                .save(getTestLesson());

        RestAssuredMockMvc.given()
                .auth().none()
                .and()
                .body(getTestLesson())
                .and()
                .contentType(io.restassured.http.ContentType.JSON)
                .post(LESSONS_ENDPOINT)
                .then()
                .status(HttpStatus.CREATED)
                .body("name", Matchers.equalTo(TEST_NAME))
                .body("date", Matchers.equalTo(TEST_DATE_AS_STRING))
                .body("contents[0].name", Matchers.equalTo(TEST_NAME))
                .body("contents[0].type", Matchers.equalTo(ContentType.DOC.name()));
    }

    @Test
    void shouldReturnManyErrors_WhenTryToCreateLesson() {
        final var lesson = getTestLesson();

        Mockito.doReturn(lesson)
                .when(service)
                .save(lesson);

        lesson.setDate(null);
        lesson.setName(null);

        RestAssuredMockMvc.given()
                .auth().none()
                .and()
                .body(lesson)
                .and()
                .contentType(io.restassured.http.ContentType.JSON)
                .post(LESSONS_ENDPOINT)
                .then()
                .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnManyErrors_WhenTryToCreateLessonWithInvalidJSON() {
        RestAssuredMockMvc.given()
                .auth().none()
                .and()
                .body(TEST_INVALID_JSON)
                .and()
                .contentType(io.restassured.http.ContentType.JSON)
                .post(LESSONS_ENDPOINT)
                .then()
                .status(HttpStatus.BAD_REQUEST);

    }

    private LessonDto getTestLesson() {
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