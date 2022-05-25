package br.rmginner.remotes.buildingservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class BuildingDto {

    @NotBlank(message = "O ID do prédio deve ser informado.")
    private String id;

    private String name;

}
