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
        if(strNumber == null) return 0D;
        // BR 10,25 US 10.25
        String number = strNumber.replaceAll(",", ".");
        if (isNumeric(number)) return Double.parseDouble(number);
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if(strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
