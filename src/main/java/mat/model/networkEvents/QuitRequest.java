package mat.model.networkEvents;

import mat.model.authentification.AuthToken;

public class QuitRequest extends AuthenticatedNetworkEvent {
    public QuitRequest(AuthToken authToken) {
        super(authToken);
    }
}
