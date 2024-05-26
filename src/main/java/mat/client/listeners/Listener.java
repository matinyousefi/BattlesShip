package mat.client.listeners;

import mat.client.controller.ControllerException;

public interface Listener<T> {
    void listen(T t) throws ControllerException;
}
