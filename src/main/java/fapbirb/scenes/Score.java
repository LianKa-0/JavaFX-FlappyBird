package fapbirb.scenes;


import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
public class Score implements SceneTemp {
    protected static List<Integer> scores = new ArrayList<>();
    protected static double speed;
    private float translate;
    enum Speed {
        SLOW, MEDIUM, FAST
    }

    @Override
    public Parent getPane() {
        translate = (float) App.HEIGHT/2;
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-image: url('bg.png')");
        Collections.sort(scores);
        setScores(pane);

        ComboBox<Speed> speedComboBox = new ComboBox<>();
        speedComboBox.getItems().addAll(Speed.SLOW, Speed.MEDIUM, Speed.FAST);
        speedComboBox.setTranslateY(translate - 100);

        Text text = new Text("Press [b] to start the game");
        text.setTranslateY(translate - 50);
        text.setFill(Color.MINTCREAM);
        text.setFont(Font.font("Verdana", 30));

        pane.setOnKeyPressed(l -> {
            if (l.getText().equals("b")) {
                speed = speedComboBox.getValue() == null ? 1 : speedComboBox.getValue().ordinal() + 0.75;
                App.scenes = App.Scenes.GAME_SCENE;
                App.setScene();
            }
        });

        pane.getChildren().addAll(text, speedComboBox);
        return pane;
    }

    public void setScores(StackPane pane) {
        Text text = new Text("SCORES:");
        text.setFill(Color.LIGHTGOLDENRODYELLOW);
        text.setFont(Font.font("Verdana", 50));
        scores.sort(Comparator.reverseOrder());

        text.setTranslateY(-translate + 60);
        int[] offset = { 100 };

        scores.stream().limit(5).
                forEach(score -> {
                    Text text0 = new Text(formattedString(score));
                    text0.setFill(Color.LIGHTSEAGREEN);
                    text0.setFont(Font.font("Verdana", 35));
                    text0.setTranslateY(-translate + (offset[0] += 50));
                    pane.getChildren().add(text0);
                });

        pane.getChildren().add(text);
    }

    public String formattedString(int value) {
        StringBuilder sb = new StringBuilder(value + "");
        int remainingZeros = 10 - sb.length();

        if (remainingZeros > 0)
            for (int i = 0; i < remainingZeros; i++)
                sb.insert(0, "0");

        return sb.toString();
    }


}
