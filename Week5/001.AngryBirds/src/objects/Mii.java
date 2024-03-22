package objects;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Created by johan on 2017-03-08.
 */
public class Mii extends GameObject{
    private List<Body> bodies;

    public Mii(String imageFile, Body body, Vector2 offset, double scale, List<Body> bodies) {
        super(imageFile, body, offset, scale);
        this.bodies = bodies;
    }

    @Override
    public void update() {
    }


}
