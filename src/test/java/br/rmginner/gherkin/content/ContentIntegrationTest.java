package br.rmginner.gherkin.content;

import br.rmginner.BaseTestContainers;
import br.rmginner.controllers.ContentsController;
import br.rmginner.repositories.ContentsRepository;
import br.rmginner.services.ContentService;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/gherkin/content.feature", glue = {"br.rmginner.gherkin"}
)
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@WebMvcTest({ContentsController.class, ContentService.class, ContentsRepository.class})
@CucumberContextConfiguration
public class ContentIntegrationTest extends BaseTestContainers {
}