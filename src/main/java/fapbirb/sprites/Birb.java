package fapbirb.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Birb extends SpiteBase {
    public final int MAX_SPEED = 450;
    public static final int BIRB_WIDTH = 42 , BIRB_HEIGHT = 26;
    private double speedY;

    public Birb(int x, int y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("birb.png",BIRB_WIDTH, BIRB_HEIGHT,false,false);

    }

    public void calcPos() {
        y -= speedY * 0.01;
        float GRAV = 10;
        if (speedY > -MAX_SPEED) speedY -= GRAV;
        setRotation();
    }

    public void setRotation() {
        rot = (int) (speedY * -0.1);
    }
}
