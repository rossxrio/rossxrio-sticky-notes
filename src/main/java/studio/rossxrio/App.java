package studio.rossxrio;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            DataMgmt.initApp();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        new AppFrame();
    }
}