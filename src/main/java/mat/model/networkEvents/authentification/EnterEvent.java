package mat.model.networkEvents.authentification;

import mat.model.authentification.AuthToken;
import mat.model.networkEvents.AuthenticatedNetworkEvent;

public class EnterEvent extends AuthenticatedNetworkEvent {
    private final String username;
    private final char[] password;

    public EnterEvent(String username, char[] password, AuthToken authToken) {
        super(authToken);
        this.username = username;
        this.password = password;
    }

    public char[] getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
