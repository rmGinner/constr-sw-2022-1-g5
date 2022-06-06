package br.rmginner.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.rmginner.entities.Content;

@Repository
public interface ContentsRepository extends MongoRepository<Content, String> {

}
