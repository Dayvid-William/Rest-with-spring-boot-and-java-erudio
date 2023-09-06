package br.com.dayvid.apirestdocker.Controllers;

import br.com.dayvid.apirestdocker.model.Person;
import br.com.dayvid.apirestdocker.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //Funciona como @ResponseBody e @Controler cria uma map do model object e encontra uma viwer equivalente
@RequestMapping("/person") // Todas as operações em person vai ser neste controller
public class PersonController {
    //private PersonServices service = new PersonServices();

    @Autowired // O spring boot fica responsavel pela instanciação do objeto de forma dinamica (e preciso ter a anotação @Service ou outra que seja um Alias(ou seja anotações injetadas) no objeto origem)
    private PersonServices service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable(value = "id")String id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)// produz um JSON e consume JSON
    public Person create(@RequestBody Person person) { // Recebe o objeto person via body
        return service.create(person);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)// produz um JSON e consume JSON
    public Person update(@RequestBody Person person) { // Recebe o objeto person via body
        return service.update(person);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id")String id) {
        service.delete(id);
    }

}
