package br.com.dayvid.apirestdocker.services;

import br.com.dayvid.apirestdocker.Controllers.BookControler;
import br.com.dayvid.apirestdocker.data.vo.v1.BookVO;
import br.com.dayvid.apirestdocker.exceptions.RequiredObjectIsNullException;
import br.com.dayvid.apirestdocker.exceptions.ResourceNotFoundException;
import br.com.dayvid.apirestdocker.mapper.DozerMapper;
import br.com.dayvid.apirestdocker.model.Book;
import br.com.dayvid.apirestdocker.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookVO> findAll(){
        logger.info("Finding all books!");

        var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books
                .stream()
                .forEach(b -> b.add(linkTo(methodOn(BookControler.class).findById(b.getKey())).withSelfRel()));
        return books;
    }

    public BookVO findById(Long id) {
        logger.info("Finding one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookControler.class).findById(id)).withSelfRel());
        return vo;

    }

    public BookVO create(BookVO book) {
        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book!");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookControler.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public BookVO update(BookVO book) {
        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book!");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunch_date(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookControler.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one book");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));

        repository.delete(entity);
    }

}
