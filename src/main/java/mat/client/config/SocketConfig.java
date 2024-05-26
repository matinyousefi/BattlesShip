package mat.client.config;

public class SocketConfig extends ConfigSet{
    SocketConfig() {
        super(new ConfigLoader().get("socketConfig"));
    }
}
