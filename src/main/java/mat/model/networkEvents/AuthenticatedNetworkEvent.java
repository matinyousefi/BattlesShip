package mat.model.networkEvents;

import mat.model.authentification.AuthToken;

import java.io.Serializable;

public abstract class AuthenticatedNetworkEvent implements Serializable {

    private final AuthToken authToken;

    public AuthenticatedNetworkEvent(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
