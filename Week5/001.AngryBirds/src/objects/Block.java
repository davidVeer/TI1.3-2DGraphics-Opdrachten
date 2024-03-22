package objects;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

public class Block extends GameObject{

    public Block(String imageFile, Body body, Vector2 offset, double scale) {
        super(imageFile, body, offset, scale);
    }

    @Override
    public void update() {

    }
}
