package br.rmginner.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ContentDto {

    @NotBlank(message = "Nome do conteúdo obrigatório")
    private String name;

    @NotBlank(message = "Tipo do conteúdo obrigatório")
    private String type;

    private String link;
}
