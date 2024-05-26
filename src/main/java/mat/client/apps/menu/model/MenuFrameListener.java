package mat.client.apps.menu.model;

import mat.client.apps.menu.controller.Controller;
import mat.client.apps.menu.view.MenuFrame;
import mat.model.authentification.AuthToken;

import java.awt.event.ActionListener;

public class MenuFrameListener {

    private final Controller controller;

    public MenuFrameListener(AuthToken authToken, MenuFrame menuFrame) {
        controller = new Controller(authToken, menuFrame);
    }

    public ActionListener getStatsListener() {
        return e -> controller.stats();
    }

    public ActionListener getNewGameListener() {
        return e -> controller.newGame();
    }

    public ActionListener getWatchGameListener() {
        return e -> controller.watchGame();
    }

    public ActionListener getScoreBoardListener() {
        return e -> controller.scoreBoard();
    }

}
