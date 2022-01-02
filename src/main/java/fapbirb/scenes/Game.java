package fapbirb.scenes;

import fapbirb.sprites.Birb;
import fapbirb.sprites.Pipe;
import fapbirb.sprites.SpiteBase;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Data
@NoArgsConstructor
public class Game implements SceneTemp {
    private GraphicsContext pipeGC, birbGC;
    public static int PIPE_GAP_HOR;
    private List<Pipe> pipes = new ArrayList<>();
    private boolean gameOver = false, gameStarted = false;
    private Label label;
    private int score;
    private Birb birb;

    public void startGame(KeyEvent k) {
        if (k.getText().equals("b") && !gameStarted) {
            startTimer();
            gameStarted = true;
        }
    }

    public void startTimer() {

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                pipeGC.clearRect(0,0, App.WIDTH,App.HEIGHT);
                birbGC.clearRect(0,0,App.WIDTH,App.HEIGHT);
                checkPos();

                pipes.forEach(pipe -> pipe.decrementXAndY(Score.speed,0));

                birb.calcPos();
                checkCollision();
                drawEverything();
            }
        };
        animationTimer.start();
    }

    public void spacePressed(KeyEvent event) {
        if (!event.getText().equals(" ")) return;
        birb.setSpeedY(birb.getMAX_SPEED());
    }

    public void checkCollision() {
        if (pipes.get(0).checkForCollision(birb.getX(), birb.getY()) && !gameOver) {
            gameOver = true;
            Score.scores.add(score);
            App.scenes = App.Scenes.SCORE_SCENE;
            App.setScene();
        }
    }

    public void checkPos() {
        Random random = new Random();
        if (pipes.get(0).getX() < 0) {
            score ++;
            birb.setCurrScore(birb.getCurrScore() + 1);
            pipes.remove(0);
            pipes.add(new Pipe(random.nextInt(App.HEIGHT - Pipe.PIPE_GAP), App.WIDTH, 0, pipeGC));
        }
    }

    public void initList() {
        Random random = new Random();
        for (int i = 0; i < 4; i++)
            pipes.add(new Pipe(random.nextInt(App.HEIGHT - Pipe.PIPE_GAP), App.WIDTH - (PIPE_GAP_HOR * i),0,pipeGC));

        birb = new Birb(60,App.HEIGHT/2, birbGC);
        sortList();
    }

    public void sortList() { pipes.sort(Comparator.comparingDouble(SpiteBase::getX)); }

    public Parent getPane() {
        Pane root = new Pane();
        root.setStyle("-fx-background-image: url('bg.png')");
        Canvas pipeCanvas = new Canvas(App.WIDTH, App.HEIGHT);
        Canvas birbCanvas = new Canvas(App.WIDTH, App.HEIGHT);
        PIPE_GAP_HOR = App.WIDTH / 4;

        label = new Label("Score: "+ score);
        label.setFont(Font.font("Verdana", 30));
        label.setTextFill(Color.ORANGERED);

        pipeGC = pipeCanvas.getGraphicsContext2D();
        birbGC = birbCanvas.getGraphicsContext2D();
        initList();

        birbGC.setStroke(Color.ORANGERED);
        birbGC.setFont(Font.font("Verdana", 15));
        birbGC.strokeText("Press [b] to start the game", 5 , (float)App.HEIGHT/2 + 50);
        drawEverything();

        root.getChildren().addAll(pipeCanvas, birbCanvas, label);
        return root;
    }

    public void drawEverything() {
       pipes.forEach(SpiteBase::draw);
       birb.draw();
       label.setText("Score: " + score);
    }

}
