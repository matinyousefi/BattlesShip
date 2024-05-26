package mat.client.apps.scoreBoard.model;

import mat.client.apps.scoreBoard.controller.Controller;
import mat.model.authentification.AuthToken;

import java.util.HashMap;
import java.util.TreeMap;

public class ScoreBoardPageListener {

    private final Controller controller;
    private final AuthToken authToken;

    public ScoreBoardPageListener(AuthToken authToken) {
        this.authToken = authToken;
        controller = new Controller(authToken);
    }

    public String getScoreBoard() {
        HashMap<String, Integer> scores = controller.getScores();
        StringBuilder stringBuilder = new StringBuilder();
        String[] names = new String[scores.size()];
        int[] scoreArray = new int[scores.size()];
        int i = 0;
        for (String key :
                scores.keySet()) {
            names[i] = key;
            scoreArray[i] = scores.get(key);
            i++;
        }
        for (int j = 0; j < scores.size() - 1; j++) {
            for (int k = 0; k < scores.size() - 1; k++) {
                if(scoreArray[k] < scoreArray[k+1]){
                    int tempInt;
                    tempInt = scoreArray[k+1];
                    scoreArray[k+1] = scoreArray[k];
                    scoreArray[k] = tempInt;
                    String tempString;
                    tempString = names[k+1];
                    names[k+1] = names[k];
                    names[k] = tempString;
                }
            }
        }
        stringBuilder.append("<html>");
        for (int j = 0; j < scores.size(); j++) {
            stringBuilder.append(names[j]).append(" ").append(scoreArray[j]).append("<br/>");
        }
        stringBuilder.append("<html>");
        return stringBuilder.toString();
    }
}
