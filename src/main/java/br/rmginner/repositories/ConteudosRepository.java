package br.rmginner.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.rmginner.entities.Conteudo;

@Repository
public interface ConteudosRepository extends MongoRepository<Conteudo, String>{

}
