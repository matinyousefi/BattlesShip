package mat.model.networkEvents.stats;

import mat.model.authentification.AuthToken;
import mat.model.networkEvents.AuthenticatedNetworkEvent;

import java.io.Serializable;

public class StatsRequest extends AuthenticatedNetworkEvent {
    public StatsRequest(AuthToken authToken) {
        super(authToken);
    }
}
