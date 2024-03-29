package DigitalWallet.Exceptions;

public class InsufficientAmountException extends Exception{
    public InsufficientAmountException(String msg){
        super(msg);
    }
}
