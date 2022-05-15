package sv.com.udb.youapp.exceptions;

public class SongAlreadyOnQueueException extends RuntimeException{

    public SongAlreadyOnQueueException(String message,Throwable th){
        super(message,th);
    }

    public SongAlreadyOnQueueException(String message){
        super(message);
    }


    public SongAlreadyOnQueueException(Throwable th){
        super(th);
    }

}
