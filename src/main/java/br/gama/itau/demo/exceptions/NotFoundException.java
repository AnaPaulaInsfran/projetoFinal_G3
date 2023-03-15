package br.gama.itau.demo.exceptions;

public class NotFoundException extends RuntimeException{
        
    public NotFoundException(String msg) {
        super(msg);
    }
}
