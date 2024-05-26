package mat.client.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public abstract class ConfigSet extends Properties {
    ConfigSet(String path){
        try {
            FileReader fileReader = new FileReader(path);
            load(fileReader);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String get(String key){
        return (String) super.get(key);
    }

    public int getInt(String key){
        return Integer.parseInt((String) super.get(key));
    }
}
