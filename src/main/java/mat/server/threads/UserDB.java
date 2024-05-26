package mat.server.threads;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mat.client.config.ConfigLoader;
import mat.model.authentification.AuthToken;
import mat.model.authentification.User;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class UserDB {
    
    private static final Object DBLock = new Object();

    private static final HashMap<AuthToken, User> authTokens = new HashMap<>();
    
    UserDB(){
        
    }
    
    public boolean exists(String username){
        for (User user :
                getAll()) {
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Nullable
    public User get(AuthToken authToken) {
        return authTokens.get(authToken);
    }

    public HashSet<User> getAll() {
        synchronized (DBLock) {
            HashSet<User> users = new HashSet<User>();
            try {
                File usersDirectory = new File(ConfigLoader.resourcesPath + "/server/dataBase/users");
                usersDirectory.mkdir();
                for (File file :
                        usersDirectory.listFiles()) {
                    List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
                    StringBuilder s = new StringBuilder();
                    for (String line :
                            lines) {
                        s.append(line);
                    }
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setPrettyPrinting();
                    Gson gson = gsonBuilder.create();
                    User user = gson.fromJson(s.toString(), User.class);
                    users.add(user);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return users;
        }
    }

    public void update(User user) {
        add(user);
    }

    public void add(User user) {
        synchronized (DBLock){
            try {
                File file = new File(ConfigLoader.resourcesPath + "/server/dataBase/users/" + user.getUsername() + ".json");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter;
                fileWriter = new FileWriter(file, false);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                gson.toJson(user, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addAuthToken(User user, AuthToken authToken){
        authTokens.put(authToken, user);
    }

    public AuthToken makeAuthToken() {
        synchronized (DBLock) {
            SecureRandom secureRandom = new SecureRandom();
            AuthToken authToken;
            do {
                authToken = new AuthToken(secureRandom.nextInt());
            } while (exists(authToken));
            return authToken;
        }
    }

    /**
     * checks if authToken already exists
     */
    public boolean exists(AuthToken authToken) {
        return authTokens.containsKey(authToken);
    }

    @Nullable
    public User get(String username) {
        for (User user :
                getAll()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean isOnline(User user) {
        return authTokens.containsValue(user);
    }
}
