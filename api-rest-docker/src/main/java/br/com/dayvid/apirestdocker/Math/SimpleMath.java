package br.com.dayvid.apirestdocker.Math;

public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double subtraction ( Double numberOne, Double numberTwo ) {
        return numberOne - numberTwo;
    }

    public Double multiplication(Double numberOne, Double numberTwo){
        return numberOne * numberTwo;
    }

    public Double division(Double numberOne, Double numberTwo){
        return numberOne / numberTwo;
    }

    public Double mean(Double numberOne, Double numberTwo){
        return (numberOne + numberTwo) / 2;
    }

    public Double squareRoot (Double number ){
        return Math.sqrt(number);
    }
}
