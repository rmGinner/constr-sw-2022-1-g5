package br.rmginner.remotes.contentservice.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentDto {

    @NotBlank(message = "O ID do conteúdo deve ser informado.")
    private String id;

    private String name;

}
