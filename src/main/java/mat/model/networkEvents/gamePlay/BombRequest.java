package mat.model.networkEvents.gamePlay;

import mat.model.authentification.AuthToken;
import mat.model.game.Cell;
import mat.model.networkEvents.AuthenticatedNetworkEvent;

public class BombRequest extends AuthenticatedNetworkEvent {

    private final Cell cell;
    public BombRequest(AuthToken authToken, Cell cell) {
        super(authToken);
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}
