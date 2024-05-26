package mat;

import mat.client.Client;
import mat.server.Server;

public class Main {
    public static void main(String[] args) {
        new Server().start();
        new Client().start();
        new Client().start();
    }
}
