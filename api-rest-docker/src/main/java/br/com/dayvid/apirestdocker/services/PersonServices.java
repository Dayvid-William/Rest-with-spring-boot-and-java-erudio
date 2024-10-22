package br.com.dayvid.apirestdocker.services;

import br.com.dayvid.apirestdocker.Controllers.PersonController;
import br.com.dayvid.apirestdocker.data.vo.v1.PersonVO;
import br.com.dayvid.apirestdocker.exceptions.RequiredObjectIsNullException;
import br.com.dayvid.apirestdocker.exceptions.ResourceNotFoundException;
import br.com.dayvid.apirestdocker.mapper.DozerMapper;
import br.com.dayvid.apirestdocker.model.Person;
import br.com.dayvid.apirestdocker.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service //Serve para o spring boot veja esse como objeto que sera injetado em run time em outras classes da aplicação
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired //injeta o repository funciona como o server pois ambos são alias de @componety
    PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;
    
    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable){
        logger.info("Finding all people!");

        var personPage = repository.findAll(pageable);

        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVosPage.map(
                p -> p.add(
                        linkTo(methodOn(PersonController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(PersonController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();
        return assembler.toModel( personVosPage, link);
    }

    public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstName ,Pageable pageable){
        logger.info("Finding all people!");

        var personPage = repository.findPersonsByName(firstName, pageable);

        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVosPage.map(
                p -> p.add(
                        linkTo(methodOn(PersonController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(PersonController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();
        return assembler.toModel( personVosPage, link);
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel()); //link o methodo findbyid do controler diretamente ao obj vo criado com auto relacionamento (cria endereço para ele mesmo)
        return vo;
    }

    public PersonVO create(PersonVO person){

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person!");

        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel()); //link o methodo findbyid do controler diretamente ao obj vo criado com auto relacionamento (cria endereço para ele mesmo)
        return vo;
    }

    public PersonVO update(PersonVO person){

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person!");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel()); //link o methodo findbyid do controler diretamente ao obj vo criado com auto relacionamento (cria endereço para ele mesmo)
        return vo;
    }

    @Transactional//só e nessecesario pos o method não e controlado pelo spring e faz alterações de dados
    public PersonVO disablePerson(Long id) {

        logger.info("Disabling one person!");
        repository.disablePerson(id);

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id){

        logger.info("deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));

        repository.delete(entity);
    }
}
