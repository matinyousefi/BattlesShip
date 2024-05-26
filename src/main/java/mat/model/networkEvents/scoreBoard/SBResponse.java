package mat.model.networkEvents.scoreBoard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

public class SBResponse implements Serializable {

    private final HashMap<String,Integer> scores;

    public SBResponse(HashMap<String,Integer> scores) {
        this.scores = scores;
    }

    public HashMap<String,Integer> getScores() {
        return scores;
    }
}
