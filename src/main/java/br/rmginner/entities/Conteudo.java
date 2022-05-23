package br.rmginner.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Conteudo {

    @Id
    private String id;

    private String nome;

    private String tipo;

    private String link;
}
