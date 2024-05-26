package mat.client.apps.gamePlay.controller;

import mat.model.game.GameState;

public interface ControllerListener {
    void listen(GameState gameState);
}
