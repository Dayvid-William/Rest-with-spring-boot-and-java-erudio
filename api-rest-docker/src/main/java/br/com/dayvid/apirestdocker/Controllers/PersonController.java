package br.com.dayvid.apirestdocker.Controllers;

import br.com.dayvid.apirestdocker.data.vo.v1.PersonVO;
import br.com.dayvid.apirestdocker.services.PersonServices;
import br.com.dayvid.apirestdocker.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //Funciona como @ResponseBody e @Controler cria uma map do model object e encontra uma viwer equivalente
@RequestMapping("/api/person/v1") // Todas as operações em personVO vai ser neste controller
public class PersonController {
    //private PersonServices service = new PersonServices();

    @Autowired // O spring boot fica responsável pela instanciação do objeto de forma dinâmica (e preciso ter a anotação @Service ou outra que seja um Alias(ou seja, anotações injetadas) no objeto origem)
    private PersonServices service;

    @GetMapping (produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_XML })
    public List<PersonVO> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_XML })
    public PersonVO findById(@PathVariable(value = "id")Long id) {
        return service.findById(id);
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_XML },
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_XML })// Produz um JSON/XML e consume JSON/XML
    public PersonVO create(@RequestBody PersonVO person) { // Recebe o objeto personVO via body
        return service.create(person);
    }

    @PutMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_XML },
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_YML, MediaType.APPLICATION_XML })//  Não é obrigatório especificar que produz json porem para trabalhar com swager e necessário
    public PersonVO update(@RequestBody PersonVO person) { // Recebe o objeto personVO via body
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id")Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
