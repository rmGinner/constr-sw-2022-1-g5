package br.rmginner.configurations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.spi.DocumentationType;

@SpringBootTest
class SwaggerConfigTest {

    @Autowired
    private SwaggerConfig swaggerConfig;

    @Test
    void shouldReturnSwaggerCustomConfig() {
        final var swaggerDoc = swaggerConfig.api();

        Assertions.assertEquals(DocumentationType.SWAGGER_2, swaggerDoc.getDocumentationType());
    }

}