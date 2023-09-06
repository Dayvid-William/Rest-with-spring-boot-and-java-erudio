package br.com.dayvid.apirestdocker.repositories;

import br.com.dayvid.apirestdocker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que o objeto e um repositorio no database que posibilita fazer um CRUD completo no database
public interface PersonRepository extends JpaRepository<Person, Long> {}
