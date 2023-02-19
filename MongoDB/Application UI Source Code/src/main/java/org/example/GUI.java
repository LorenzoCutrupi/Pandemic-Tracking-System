package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class GUI extends Application {
    private final HashMap<String, GUIController> nameToController = new HashMap<>();
    private final HashMap<String, Scene> nameToScene = new HashMap<>();
    private Scene currentScene;
    private Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        setup();
        this.stage = stage;
        run();
    }

    private void setup() {
        List<String> fxmList = new ArrayList<>(Arrays.asList("starting.fxml","login.fxml"));
        try {
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                Parent root = (Parent) loader.load();
                GUIController controller = loader.getController();
                controller.setGui(this);
                nameToScene.put(path,new Scene(root));
                nameToController.put(path, controller);
            }
            currentScene = nameToScene.get("starting.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        stage.setTitle("Green Pass Simulator");
        stage.setScene(currentScene);
        stage.getIcons().add(new Image("images/green-logo.png"));
        stage.show();
    }

    public void changeStage(String newScene) {
        currentScene = nameToScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
    }
}
