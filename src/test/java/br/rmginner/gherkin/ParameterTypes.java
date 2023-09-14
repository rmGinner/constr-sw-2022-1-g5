package br.rmginner.gherkin;

import br.rmginner.entities.enums.ContentType;
import io.cucumber.java.ParameterType;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

public class ParameterTypes {

    @ParameterType(".*")
    public ContentType contentType(String contentType) {
        return ContentType.valueOf(contentType);
    }
}