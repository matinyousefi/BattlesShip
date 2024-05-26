package mat.client.apps.login.model;

public class UserPassEvent {

    private final String username;
    private final char[] password;
    private boolean register;

    public UserPassEvent(boolean register, String username, char[] password) {
        this.register = register;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    public boolean isRegister() {
        return register;
    }
}
