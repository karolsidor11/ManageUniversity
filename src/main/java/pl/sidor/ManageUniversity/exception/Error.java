package pl.sidor.ManageUniversity.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {

    private MessageException messageException;
    private String description;

    public static  Error createError(MessageException messageException, String description){
        Error error= new Error();
        error.setMessageException(messageException);
        error.setDescription(description);
        return  error;
    }
}
