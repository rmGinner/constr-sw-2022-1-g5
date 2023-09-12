package br.rmginner.gherkin;

import java.util.Map;

import br.rmginner.dtos.ContentDto;
import br.rmginner.entities.enums.ContentType;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;

public class ContentStepDefinitions {

    private String content;

    private ContentType type;

    private String link;

    private static final String CONTENTS_ENDPOINT = "/contents";

    private MockMvcResponse mockMvcResponse;

    @ParameterType(".*")
    public ContentType contentTypeEntry(String contentType) {
        return ContentType.valueOf(contentType);
    }

    @Given("Content {string}")
    public void content(String string) {
        this.content = string;
    }

    @Given("type {contentType}")
    public void type(ContentType contentType) {
        this.type = contentType;
    }

    @Given("link {string}")
    public void link(String string) {
        this.link = string;
    }

    @When("Teacher makes call to POST \\/contents endpoint")
    public void teacher_makes_call_to_post_contents_endpoint() {
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
                .body("type", Matchers.equalTo(type))
                .body("link", Matchers.equalTo(link));
    }

    private ContentDto getTestContent() {
        return ContentDto.builder()
                .name(content)
                .type(ContentType.DOC)
                .build();
    }

}
