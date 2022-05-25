package br.rmginner.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotEmpty(message = "Lista de conteúdos obrigatória")
    private List<@Valid ContentDto> contents;
}
