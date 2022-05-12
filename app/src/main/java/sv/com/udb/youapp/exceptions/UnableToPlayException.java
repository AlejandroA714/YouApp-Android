package sv.com.udb.youapp.exceptions;

public class UnableToPlayException extends RuntimeException{

    public UnableToPlayException(String message,Throwable th){
        super(message,th);
    }

    public UnableToPlayException(Throwable th){
        super(th);
    }

}
