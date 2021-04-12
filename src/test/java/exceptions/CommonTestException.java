package exceptions;

public class CommonTestException extends RuntimeException{
    public CommonTestException(String msg){
        super(msg);
    }
    public CommonTestException(String msg, Exception e){
        super(msg, e);
    }
}
