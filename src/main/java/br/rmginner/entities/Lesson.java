package br.rmginner.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "lessons")
public class Lesson {

    @Id
    private String id;

    private String name;

    private LocalDateTime date;

    private String classId;

    private String buildingId;

    @Field("contents")
    @DBRef
    private List<Content> contents;

    private boolean isEnabled;
}
