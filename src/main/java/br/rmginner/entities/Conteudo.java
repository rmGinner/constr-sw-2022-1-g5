package br.rmginner.entities;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;


@Document
@Data
@Builder
public class Conteudo {
	
	@Id
	private String id;

	private String nome;
	
	private String tipo;

	private String link;
}
