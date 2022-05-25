package br.rmginner;

import br.rmginner.dtos.LessonDto;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest
class ClassesServiceApplicationTests {

    private static final String CLASSES_ENDPOINT = "/lessons";

    private static final String TEST_NAME = "Test";

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2022, 5, 4, 20, 13, 0);

    private static final String TEST_DATE_AS_STRING = "2022-05-04T20:13:00";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService service;

    @BeforeEach
    void setUp() {
        RestAssured.registerParser("null", Parser.JSON);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldReturnListOfClasses() {
        Mockito.when(service.getAll())
                .thenReturn(List.of(getTestLesson()));

        RestAssuredMockMvc.given()
                .auth().none()
                .and()
                .get(CLASSES_ENDPOINT)
                .then()
                .status(HttpStatus.OK)
                .body("$.size()", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo(TEST_NAME))
                .body("[0].date", Matchers.equalTo(TEST_DATE_AS_STRING));
    }

    @Test
    void shouldCreateClass() {
        Mockito.doReturn(getTestLesson())
                .when(service)
                .create(getTestLesson());

        RestAssuredMockMvc.given()
                .auth().none()
                .and()
                .body(getTestLesson())
                .post(CLASSES_ENDPOINT)
                .then()
                .apply(MockMvcResultHandlers.print())
                .status(HttpStatus.OK)
                .body("$.name", Matchers.equalTo(TEST_NAME))
                .body("$.date", Matchers.equalTo(TEST_DATE_AS_STRING));
    }

    private LessonDto getTestLesson() {
        return LessonDto.builder()
                .name(TEST_NAME)
                .date(TEST_DATE)
                .build();
    }

}
