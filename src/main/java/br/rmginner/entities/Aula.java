package br.rmginner.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class Aula {
	
	@Id
	private String _id;	
	private String nome;
	private LocalDateTime data;
}
