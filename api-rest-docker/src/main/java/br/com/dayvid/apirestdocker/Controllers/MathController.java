package br.com.dayvid.apirestdocker.Controllers;

import br.com.dayvid.apirestdocker.Math.SimpleMath;
import br.com.dayvid.apirestdocker.converters.NumberConverter;
import br.com.dayvid.apirestdocker.exceptions.UnsuportedMathOperationException;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController //Funciona como @ResponseBody e @Controler cria uma map do model object e encontra uma viwer equivalente
public class MathController {

    private static final AtomicLong counter = new AtomicLong();

    private SimpleMath math = new SimpleMath();
    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double sum(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                      @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double subtraction(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                      @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return math.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double multiplication(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                       @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return math.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double division(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                       @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return math.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double mean(@PathVariable(value = "numberOne")String numberOne, //especifica que a variavel vem da url request
                       @PathVariable(value = "numberTwo")String numberTwo) throws Exception {

        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return math.mean(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/squareRoot/{number}", method = RequestMethod.GET)// As chaves tornam os parametros obrigatorios
    public Double squareRoot(@PathVariable(value = "number")String number //especifica que a variavel vem da url request
                                ) throws Exception {

        if(!NumberConverter.isNumeric(number)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }

        return math.squareRoot(NumberConverter.convertToDouble(number)) ;
    }
}
