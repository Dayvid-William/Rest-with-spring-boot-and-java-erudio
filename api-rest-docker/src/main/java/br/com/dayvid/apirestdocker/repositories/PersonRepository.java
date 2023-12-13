package br.com.dayvid.apirestdocker.repositories;

import br.com.dayvid.apirestdocker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository Indica que o objeto e um repositorio no database que posibilita fazer um CRUD completo no database
// A parti do spring boot 3.0.1 o spring boot entende como um repositorio só de extender o JPARepository
public interface PersonRepository extends JpaRepository<Person, Long> {}
