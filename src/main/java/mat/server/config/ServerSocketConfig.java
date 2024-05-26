package mat.server.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerSocketConfig extends Properties {
    public ServerSocketConfig(){
        try {
            FileReader fileReader = new FileReader("src/main/resources/server/serverSocketConfig");
            load(fileReader);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
