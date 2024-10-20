package br.com.dayvid.apirestdocker.repositories;

import br.com.dayvid.apirestdocker.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// @Repository Indica que o objeto e um repositorio na base de dados que posibilita fazer um CRUD completo na base de dados
// A parti do spring boot 3.0.1 o spring boot entende como um repositorio só de extender o JPARepository

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Modifying // e nessario, pois não e o jpa que esta modificando foi customizado
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);

    @Query("SELECT p FROM Person p  WHERE p.firstName LIKE LOWER(CONCAT ('%', :firstName, '%'))") // LIKE pode gerar problema de performace pois considera todos os registros
    Page<Person> findPersonByName(@Param("firstName") String firstName, Pageable pageable);
}