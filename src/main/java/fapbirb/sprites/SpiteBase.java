package fapbirb.sprites;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SpiteBase {
    protected Image img;
    protected GraphicsContext gc;
    protected double x, y;
    protected int currScore;
    protected int rot;

    public SpiteBase(double x, double y, GraphicsContext gc) {
        this.x = x;
        this.y = y;
        rot = 0;
        this.gc = gc;
    }

    public void draw() {
        if (rot == 0) {
            this.gc.drawImage(img, x, y);
        }
        else {
            this.gc.drawImage(getRotatedImage(rot, img), x, y);
        }
    }

    public void decrementXAndY(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    public static Image getRotatedImage(int rot, Image img) {
        ImageView iv = new ImageView(img);
        iv.setRotate(rot);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return iv.snapshot(params, null);
    }

    public static Image cutImage(Image img, int width, int height) {
        PixelReader reader = img.getPixelReader();
        return new WritableImage(reader,0, 0, width, height);
    }
}
