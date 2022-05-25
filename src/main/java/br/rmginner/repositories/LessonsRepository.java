package br.rmginner.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.rmginner.entities.Lesson;

@Repository
public interface LessonsRepository extends MongoRepository<Lesson, String>{
	
}
