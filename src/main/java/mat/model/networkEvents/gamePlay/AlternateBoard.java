package mat.model.networkEvents.gamePlay;

import mat.model.authentification.AuthToken;
import mat.model.networkEvents.AuthenticatedNetworkEvent;

public class AlternateBoard extends AuthenticatedNetworkEvent {
    public AlternateBoard(AuthToken authToken) {
        super(authToken);
    }
}
