package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.controller.Direction.DIRECTION;

public abstract class AnimationEntity extends Entity {
    protected DIRECTION direction = DIRECTION.RIGHT;
    protected DIRECTION backStep = DIRECTION.RIGHT;
    protected int countStep = 0;

    public AnimationEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    public abstract Image chooseSprite();

    
}
