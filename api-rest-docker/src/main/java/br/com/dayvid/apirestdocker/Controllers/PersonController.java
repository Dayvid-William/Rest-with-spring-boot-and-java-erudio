package br.com.dayvid.apirestdocker.Controllers;

import br.com.dayvid.apirestdocker.model.Person;
import br.com.dayvid.apirestdocker.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)// As chaves tornam os parametros obrigatorios, produz um JSON
    public Person findById(@PathVariable(value = "id")String id) throws Exception {  //especifica que a variavel vem da url request
        return service.findById(id);
    }
}
