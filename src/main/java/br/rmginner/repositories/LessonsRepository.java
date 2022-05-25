package br.rmginner.repositories;

import br.rmginner.entities.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonsRepository extends MongoRepository<Lesson, String> {

}
