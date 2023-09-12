package br.rmginner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.integration.test.context.SpringIntegrationTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(features = "src/test/resources/gherkin")
@SpringIntegrationTest
public class CucumberIntegrationTest {
}