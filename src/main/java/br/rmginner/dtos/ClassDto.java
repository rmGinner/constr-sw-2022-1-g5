package br.rmginner.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class ClassDto {

    private String id;

    @NotBlank(message = "Nome obrigatório")
    private String name;

    @NotNull(message = "Data obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    @NotNull(message = "Conteúdo obrigatório")
    @Valid
    private ContentDto content;
}
