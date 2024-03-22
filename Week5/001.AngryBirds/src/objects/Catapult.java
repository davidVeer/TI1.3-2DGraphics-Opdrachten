package objects;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

import java.util.List;

public class Catapult extends GameObject {
    private List<Body> bodies;

    public Catapult(String imageFile, Body body, Vector2 offset, double scale, List<Body> bodies) {
        super(imageFile, body, offset, scale);
        this.bodies = bodies;
    }

    public void update(){
        for (Body body : bodies) {
            if (super.getBody().isInContact(body)){
                body.applyForce(new Vector2(1000,0));
            }
        }
    }


}
