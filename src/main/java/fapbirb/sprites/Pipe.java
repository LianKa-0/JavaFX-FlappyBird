package fapbirb.sprites;

import fapbirb.scenes.App;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Pipe extends SpiteBase {
    private int lowerPipeHeight;
    private int gapY1;
    private int gapY2;
    public final static int pipeImgWidth = 78, PIPE_GAP = 250;


    public Pipe(int lowerPipeHeight, double x, double y, GraphicsContext gc) {
        super(x,y,gc);
        this.lowerPipeHeight = lowerPipeHeight;
        parseImg();

    }

    public boolean checkForCollision(double x1, double y1) {
        int faultTolerance = 10;
        return ((x1 + Birb.BIRB_WIDTH >= x && x1 <= x + pipeImgWidth) && (y1 + faultTolerance /*+ Birb.BIRB_HEIGHT*/ < gapY1 || y1 + Birb.BIRB_HEIGHT - faultTolerance > gapY2));
    }

    public void parseImg() {
        Image img = new Image("pipe.png");
        int height = gapY1 = App.HEIGHT - lowerPipeHeight - PIPE_GAP;
        gapY2 = gapY1 + PIPE_GAP;

        //creates a canvas, on which 2 the upside down pipe and the normal pipe are drawn
        Canvas canvas = new Canvas(pipeImgWidth, App.HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(getRotatedImage(180, cutImage(img, pipeImgWidth, height)), 0, 0);
        gc.drawImage(img, -1, App.HEIGHT - lowerPipeHeight);

        //merges the upside down img and the normal img to one via Snapshot
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        super.img = gc.getCanvas().snapshot(params, null);
    }
}
