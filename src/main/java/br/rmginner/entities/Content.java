package br.rmginner.entities;

import br.rmginner.entities.enums.ContentType;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "contents")
public class Content {

    @Id
    private String id;

    private String name;

    private ContentType type;

    private String link;
    
    private boolean isEnabled;
}
