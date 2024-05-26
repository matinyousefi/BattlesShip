package mat.client.config;

public class ConfigAgent {
    private final mat.client.config.GameConfig gameConfig = new mat.client.config.GameConfig();
    private final LoginFrameConfig loginFrameConfig = new LoginFrameConfig();
    private final GraphicConfig graphicConfig = new GraphicConfig();
    private final SocketConfig socketConfig = new SocketConfig();

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public LoginFrameConfig getLoginFrameConfig() {
        return loginFrameConfig;
    }

    public GraphicConfig getGraphicConfig() {
        return graphicConfig;
    }

    public SocketConfig getSocketConfig() {
        return socketConfig;
    }
}
