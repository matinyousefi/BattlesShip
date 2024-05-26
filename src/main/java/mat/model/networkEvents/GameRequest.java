package mat.model.networkEvents;

import mat.model.authentification.AuthToken;

import java.io.Serializable;

public class GameRequest extends AuthenticatedNetworkEvent implements Serializable {
    public GameRequest(AuthToken authToken) {
        super(authToken);
    }
}
