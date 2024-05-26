package mat.client.config;

public class GameConfig extends ConfigSet{
    GameConfig() {
        super(new ConfigLoader().get("gameConfig"));
    }
}
