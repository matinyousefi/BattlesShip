package mat.model.networkEvents.gamePlay;

import mat.model.authentification.AuthToken;
import mat.model.networkEvents.AuthenticatedNetworkEvent;


public class Ready extends AuthenticatedNetworkEvent {
    public Ready(AuthToken authToken) {
        super(authToken);
    }
}
