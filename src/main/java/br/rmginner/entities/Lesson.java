package br.rmginner.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document
public class Lesson {

    @Id
    private String id;

    private String name;

    private LocalDateTime date;

    private String classId;

    private String buildingId;

    private List<Content> contents;

    private boolean isEnabled;
}
