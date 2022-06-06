package br.rmginner.dtos;

import br.rmginner.entities.enums.ContentType;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ContentDto {

	private String id;
	
    @NotBlank(message = "Nome do conteúdo obrigatório")
    private String name;

    @NotNull(message = "Tipo do conteúdo obrigatório")
    private ContentType type;

    @URL(message = "O link deve ser uma URL válida.")
    private String link;
}
