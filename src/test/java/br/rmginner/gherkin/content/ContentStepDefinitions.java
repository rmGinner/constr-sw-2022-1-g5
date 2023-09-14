package br.rmginner.gherkin.content;

import br.rmginner.controllers.ContentsController;
import br.rmginner.dtos.ContentDto;
import br.rmginner.entities.enums.ContentType;
import br.rmginner.repositories.ContentsRepository;
import br.rmginner.services.ContentService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

public class ContentStepDefinitions {

    private String content;

    private ContentType contentType;

    private String link;

    private static final String CONTENTS_ENDPOINT = "/contents";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContentService contentService;

    @MockBean
    private ContentsRepository contentsRepository;

    @Autowired
    private ContentsController contentsController;

    private MockMvcResponse mockMvcResponse;

    @BeforeAll
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

    }

    @Given("Content {string}")
    public void content(String content) {
        this.content = content;
    }

    @Given("type {contentType}")
    public void type(ContentType contentType) {
        this.contentType = contentType;

    }

    @Given("link {string}")
    public void link(String link) {
        this.link = link;
    }

    @When("Teacher makes call to POST \\/contents endpoint")
    public void teacher_makes_call_to_post_contents_endpoint() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        mockMvcResponse = RestAssuredMockMvc.given()
                .auth().none()
                .and()
                .body(getTestContent())
                .and()
                .contentType(io.restassured.http.ContentType.JSON)
                .post(CONTENTS_ENDPOINT);
    }

    @Then("content should be saved")
    public void content_should_be_saved() {
        mockMvcResponse.then()
                .status(HttpStatus.CREATED)
                .body("name", Matchers.equalTo(content))
                .body("type", Matchers.equalTo(contentType.name()))
                .body("link", Matchers.equalTo(link));
       final var createdId = mockMvcResponse.body().jsonPath().getString("id");

        final var result = contentService.getContentById(createdId);

        Assertions.assertTrue(result.isPresent());
    }

    private ContentDto getTestContent() {
        return ContentDto.builder()
                .name(content)
                .type(ContentType.DOC)
                .link(link)
                .build();
    }

}
