package mat.model.authentification;

import java.io.Serializable;
import java.util.Objects;

public class AuthToken implements Serializable {

    private final int authToken;

    public AuthToken(int authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return String.valueOf(authToken);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return authToken == authToken1.authToken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken);
    }
}
