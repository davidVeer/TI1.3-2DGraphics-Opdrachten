import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SlideShow {
    private ArrayList<BufferedImage> images;
    private BufferedImage Background;
    private BufferedImage fadingLayer;
    private float currentOpacity;
    private int currentSlide;
    private int canvasHeight;
    private int canvasWidth;

    public SlideShow(ArrayList<String> images, int canvasWidth, int canvasHeight) {
        currentOpacity = 0.0f;
        currentSlide = 0;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        setupImages(images);
    }

    private void setupImages(ArrayList<String> imagePaths) {
        this.images = new ArrayList<>();
        for (String image : imagePaths) {
            try {
                this.images.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(image))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Background = images.get(currentSlide);
        fadingLayer = images.get(currentSlide);
    }

    public void update() {
        currentOpacity += 0.05f;
        if (currentOpacity > 1) {
            currentOpacity = 0.0f;
            if (currentSlide + 1 > images.size() - 1) {
                Background = images.get(currentSlide);
                fadingLayer = images.get(0);
                currentSlide = 0;
            } else {
                Background = images.get(currentSlide);
                fadingLayer = images.get(currentSlide + 1);
                currentSlide++;
            }
        }


    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(Background, 0, 0, canvasWidth, canvasHeight, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentOpacity));
        graphics.drawImage(fadingLayer, 0, 0, canvasWidth, canvasHeight, null);

    }

}
