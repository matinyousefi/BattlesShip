package mat.client.config;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader extends Properties {
    public static final String resourcesPath = "src/main/resources";
    private static final String configLoaderPath = resourcesPath + "/client/config/configLoader";

    ConfigLoader(){
        try {
            FileReader fileReader = new FileReader(configLoaderPath);
            load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key){
        return (String) super.get(key);
    }



}
