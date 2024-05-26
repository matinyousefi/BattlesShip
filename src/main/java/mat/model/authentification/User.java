package mat.model.authentification;

import org.json.JSONPropertyIgnore;

import java.io.Serializable;
import java.security.SecureRandom;

public class User implements Serializable {
    private final String username;
    private final char[] password;
    private int lostCount;
    private int winCount;
    private AuthToken AuthToken;

    public User(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    public void setAuthToken(AuthToken authToken) {
        AuthToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    public AuthToken getAuthToken() {
        return AuthToken;
    }

    public int getLostCount() {
        return lostCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getScore(){
        return winCount - lostCount;
    }

    public void won() {
        winCount++;
    }

    public void lost() {
        lostCount++;
    }
}
