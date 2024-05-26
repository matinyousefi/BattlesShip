package mat.client.controller;

import java.io.Serializable;

public class ControllerException extends Exception implements Serializable {
    public ControllerException(String message){
        super(message);
    }
}
