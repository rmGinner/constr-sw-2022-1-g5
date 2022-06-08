package br.rmginner.dtos;

import br.rmginner.remotes.buildingservice.dto.BuildingDto;
import br.rmginner.remotes.classservice.dto.ClassDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class LessonDto {

    private String id;

    @NotBlank(message = "Nome obrigatório")
    private String name;

    @NotNull(message = "Data obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    @NotNull(message = "O prédio deve ser informado.")
    @Valid
    private BuildingDto building;

    @JsonProperty("class")
    @NotNull(message = "A turma deve ser informada.")
    @Valid
    private ClassDto classDto;

    @NotEmpty(message = "Lista de conteúdos obrigatória")
    private List<@Valid ContentDto> contents;
}
