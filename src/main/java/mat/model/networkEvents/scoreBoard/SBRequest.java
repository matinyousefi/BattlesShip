package mat.model.networkEvents.scoreBoard;

import mat.model.authentification.AuthToken;
import mat.model.networkEvents.AuthenticatedNetworkEvent;

public class SBRequest extends AuthenticatedNetworkEvent {
    public SBRequest(AuthToken authToken) {
        super(authToken);
    }
}
