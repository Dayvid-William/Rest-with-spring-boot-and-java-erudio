package br.com.dayvid.apirestdocker.Controllers;

import br.com.dayvid.apirestdocker.data.vo.v1.BookVO;
import br.com.dayvid.apirestdocker.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

public class BookControler {

    @Autowired
    private BookServices service;

    public BookVO findById(@PathVariable(value = "id")Long id) { return null; }
}
