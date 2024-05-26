package mat.test;

import mat.client.apps.gamePlay.view.GameFrame;
import mat.model.authentification.AuthToken;
import mat.model.authentification.User;
import mat.model.game.Board;
import mat.model.game.GameState;

public class GameFrameTest {
    public static void main(String[] args) {
        User user = new User("matin",new char[]{'t'});
        user.setAuthToken(new AuthToken(1));
        GameState gameState =  new GameState(user, user);
        new GameFrame(new AuthToken(1), gameState, null);
    }
}
