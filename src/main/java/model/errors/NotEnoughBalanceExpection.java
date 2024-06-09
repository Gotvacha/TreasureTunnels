package model.errors;

public class NotEnoughBalanceExpection extends Exception{
    public NotEnoughBalanceExpection(){
        super("ERROR: Not enough balance to do this operation");
    }
}
