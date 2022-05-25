package br.rmginner.remotes.classservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ClassDto {

    @NotBlank(message = "O ID da turma deve ser informado.")
    private String id;

    private String name;

}
