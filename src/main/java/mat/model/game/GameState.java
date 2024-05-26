package mat.model.game;

import mat.model.authentification.AuthToken;
import mat.model.authentification.User;

import javax.swing.*;
import java.io.Serializable;

public class GameState implements Serializable {

    private final User user1;
    private final User user2;
    private Board board1;
    private Board board2;
    private boolean ready1 = false;
    private boolean ready2 = false;
    private int alternationLeft1 = 3;
    private int alternationLeft2 = 3;
    private int turn = 0;

    public GameState(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        board1 = Board.generate();
        board2 = Board.generate();
    }


    private final transient Object buckleUpTime1Lock = new Object();
    private int buckleUpTime1 = 30;

    private final transient Timer buckleUpTimer1 = new Timer(1000, e -> eachBuckleUpTimer1()){
        {
            start();
        }
    };

    private void eachBuckleUpTimer1() {
        synchronized (buckleUpTime1Lock) {
            buckleUpTime1--;
        }
        if (turn > 0) {
            buckleUpTimer1.stop();
        }
        if (buckleUpTime1 == 0) {
            buckleUpTimer1.stop();
            ready1 = true;
        }
    }


    private final transient Object buckleUpTime2Lock = new Object();
    private int buckleUpTime2 = 30;

    private final transient Timer buckleUpTimer2 = new Timer(1000, e -> eachBuckleUpTimer2()){
        {
            start();
        }
    };

    private void eachBuckleUpTimer2() {
        synchronized (buckleUpTime2Lock) {
            buckleUpTime2--;
        }
        if (turn > 0) {
            buckleUpTimer2.stop();
        }
        if (buckleUpTime2 == 0) {
            buckleUpTimer2.stop();
            ready2 = true;
        }
    }


    private int turnTime1 = 25;

    private final transient Timer turnTimer1 = new Timer(1000, e -> eachTurnTimer1());

    private void eachTurnTimer1() {
        turnTime1--;
        if (turnTime1 == 0){
            nextTurn();
        }
    }


    private int turnTime2 = 25;

    private final transient Timer turnTimer2 = new Timer(1000, e -> eachTurnTimer2());

    private void eachTurnTimer2() {
        turnTime2--;
        if (turnTime2 == 0){
            nextTurn();
        }
    }


    public int getTurn() {
        return turn;
    }

    public int getUserInt(AuthToken authToken) {
        if (authToken.equals(user1.getAuthToken())) {
            return 1;
        } else if (authToken.equals(user2.getAuthToken())) {
            return 2;
        } else {
            System.out.println(authToken);
            throw new RuntimeException("Invalid AuthToken");
        }
    }

    public int getAlternationLeft(AuthToken authToken) {
        if(getUserInt(authToken)==1){
            return alternationLeft1;
        } else return alternationLeft2;
    }

    public void setBoard(AuthToken authToken, Board board) {
        if(getUserInt(authToken)==1){
            board1 = board;
        } else board2 = board;
    }

    public void reduceAlternationLeft(AuthToken authToken) {
        if (getUserInt(authToken) == 1) {
            synchronized (buckleUpTime1Lock) {
                alternationLeft1--;
                buckleUpTime1 += 10;
            }
        } else{
            synchronized (buckleUpTime2Lock) {
                alternationLeft2--;
                buckleUpTime2 += 10;
            }
        }
    }

    public void setReady(AuthToken authToken) {
        if(getUserInt(authToken)==1){
            ready1 = true;
        } else ready2 = true;
    }

    public boolean isReady(AuthToken authToken) {
        if (getUserInt(authToken) == 1) {
            return ready1;
        } else return ready2;
    }

    public void nextTurn() {
        if (turn % 2 == 1) {
            turnTimer1.stop();
            turnTime1 = 25;
            turnTimer2.restart();
        } else {
            turnTimer2.stop();
            turnTime2 = 25;
            turnTimer1.restart();
        }
        turn++;
    }

    public boolean bothReady() {
        return ready2 && ready1;
    }

    public Board getBoard(AuthToken authToken) {
        if (getUserInt(authToken) == 1) {
            return board1;
        } else return board2;
    }

    public Board getEnemyBoard(AuthToken authToken) {
        if (getUserInt(authToken) == 1) {
            return board2;
        } else return board1;
    }

    public void setTurn(int i) {
        if(i == -1){
            turn = -1;
        }
    }

    public String getMyName(AuthToken authToken) {
        if (getUserInt(authToken) == 1) {
            return user1.getUsername();
        } else return user2.getUsername();
    }

    public String getEnemyName(AuthToken authToken) {
        if (getUserInt(authToken) == 1) {
            return user2.getUsername();
        } else return user1.getUsername();
    }

    public boolean isEnemyReady(AuthToken authToken) {
        if (getUserInt(authToken) == 1) {
            return ready2;
        } else return ready1;
    }

    public int getBuckleUpTime(AuthToken authToken) {
        if (getUserInt(authToken) == 1) {
            return buckleUpTime1;
        } else return buckleUpTime2;
    }

    public int getTurnTime(AuthToken authToken) {
        if(getUserInt(authToken)==1){
            return turnTime1;
        } else return turnTime2;
    }

    public int getEnemyTurnTime(AuthToken authToken) {
        if(getUserInt(authToken)==1){
            return turnTime2;
        } else return turnTime1;
    }
}
