package mat.model.networkEvents.stats;

import java.io.Serializable;

public class StatsResponse implements Serializable {

    private String username;
    private int winCount;
    private int lostCount;
    private int score;

    public StatsResponse(String username, int winCount, int lostCount, int score) {
        this.username = username;
        this.winCount = winCount;
        this.lostCount = lostCount;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public int getScore() {
        return score;
    }
}
