package mat.client.config;

public class GraphicConfig extends ConfigSet{
    GraphicConfig() {
        super(new ConfigLoader().get("graphicConfig"));
    }
}
