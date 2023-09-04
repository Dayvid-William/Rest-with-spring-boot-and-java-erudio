package br.com.dayvid.apirestdocker;

import br.com.dayvid.apirestdocker.exceptions.UnsuportedMathOperationException;
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
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double subtraction(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                      @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double multiplication(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                       @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double division(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                       @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double mean(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                       @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }

    @RequestMapping(value = "/squareRoot/{number}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double squareRoot(@PathVariable(value = "number")String number //especifica que a variavel vem da url request
                                ) throws Exception {

        if(!isNumeric(number)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return Math.sqrt(convertToDouble(number)) ;
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
