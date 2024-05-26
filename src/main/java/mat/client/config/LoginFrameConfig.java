package mat.client.config;

public class LoginFrameConfig extends ConfigSet {
    LoginFrameConfig() {
        super(new ConfigLoader().get("loginFrameConfig"));
    }
}
