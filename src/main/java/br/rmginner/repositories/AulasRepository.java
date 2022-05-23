package br.rmginner.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.rmginner.entities.Aula;

@Repository
public interface AulasRepository extends MongoRepository<Aula, String>{
	
}
