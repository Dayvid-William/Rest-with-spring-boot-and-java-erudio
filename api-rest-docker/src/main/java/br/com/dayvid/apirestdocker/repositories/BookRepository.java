package br.com.dayvid.apirestdocker.repositories;

import br.com.dayvid.apirestdocker.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
