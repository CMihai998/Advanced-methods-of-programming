package Model.Exceptions;

public class MyException extends Exception{
    public MyException(String not_enough_space) {
        super(not_enough_space);
    }
}
