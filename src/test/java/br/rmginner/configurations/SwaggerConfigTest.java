package br.rmginner.configurations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.spi.DocumentationType;

@SpringBootTest
class SwaggerConfigTest {

    private SwaggerConfig swaggerConfig;

    @BeforeEach
    void setUp() {
        swaggerConfig = new SwaggerConfig();
    }

    @Test
    void shouldReturnSwaggerCustomConfig() {
        final var swaggerDoc = swaggerConfig.api();

        Assertions.assertEquals(DocumentationType.SWAGGER_2, swaggerDoc.getDocumentationType());
    }

}