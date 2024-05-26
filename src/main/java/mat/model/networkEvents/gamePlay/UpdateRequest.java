package mat.model.networkEvents.gamePlay;

import mat.model.authentification.AuthToken;
import mat.model.networkEvents.AuthenticatedNetworkEvent;

public class UpdateRequest extends AuthenticatedNetworkEvent {
    public UpdateRequest(AuthToken authToken) {
        super(authToken);
    }
}
