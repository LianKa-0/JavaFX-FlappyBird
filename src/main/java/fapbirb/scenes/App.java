package fapbirb.scenes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    protected static Stage stage;
    public static final int WIDTH = 1000, HEIGHT = 500;
    protected static Scenes scenes = Scenes.SCORE_SCENE;
    enum Scenes {
        GAME_SCENE, SCORE_SCENE
    }


    @Override
    public void start(Stage startStage) {
        stage = startStage;
        stage.setTitle("FappyBirb (vewy owiginal gaym, defo neva seen befowe)");
        stage.getIcons().add(new Image("Icon1.jpg"));
        setScene();
    }

    public static void setScene() {
        Scene scene;

        switch (scenes) {
            case GAME_SCENE -> {
                Game game = new Game();
                scene = new Scene(game.getPane(), WIDTH, HEIGHT);
                scene.setOnKeyPressed(l -> {
                    game.spacePressed(l);
                    game.startGame(l);
                });
            }
            case  SCORE_SCENE -> {
                Score score = new Score();
                scene = new Scene(score.getPane(), WIDTH, HEIGHT);
            }
            default -> scene = new Scene(new Pane());
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}