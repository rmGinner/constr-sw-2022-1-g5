package br.rmginner.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentDto {

	private String id;

	private String name;
	
	private String type;

	private String link;
}
