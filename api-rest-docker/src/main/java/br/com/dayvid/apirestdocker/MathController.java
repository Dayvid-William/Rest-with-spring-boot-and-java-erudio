package br.com.dayvid.apirestdocker;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController //Funciona como @ResponseBody e @Controler cria uma map do model object e encontra uma viwer equivalente
public class MathController {

    private static final String template = "Hello, %s!";
    private static final AtomicLong counter = new AtomicLong();
    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double sum(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                      @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new Exception();
        }

        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    private Double convertToDouble(String strNumber) {
        return null;
    }

    private boolean isNumeric(String strNumber) {
        return false;
    }
}
