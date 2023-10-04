package br.com.dayvid.apirestdocker.services;

import br.com.dayvid.apirestdocker.Controllers.PersonController;
import br.com.dayvid.apirestdocker.data.vo.v1.PersonVO;
import br.com.dayvid.apirestdocker.exceptions.ResourceNotFoundException;
import br.com.dayvid.apirestdocker.mapper.DozerMapper;
import br.com.dayvid.apirestdocker.model.Person;
import br.com.dayvid.apirestdocker.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service //Serve para o spring boot veja esse como objeto que sera injetado em run time em outras classes da aplicação
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired //injeta o repository funciona como o server pois ambos são alias de @componety
    PersonRepository repository;
    
    public List<PersonVO> findAll(){
        logger.info("Finding all people!");

        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel()); //link o methodo findbyid do controler diretamente ao obj vo criado com auto relacionamento (cria endereço para ele mesmo)
        return vo;
    }

    public PersonVO create(PersonVO person){

        logger.info("Creating one person!");

        var entity = DozerMapper.parseObject(person, Person.class);

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        return vo;
    }

    public PersonVO update(PersonVO person){

        logger.info("Updating one person!");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        return vo;
    }

    public void delete(Long id){

        logger.info("deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID!"));

        repository.delete(entity);
    }
}
