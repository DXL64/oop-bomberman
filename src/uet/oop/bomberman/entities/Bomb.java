package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.controller.Timer;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimationEntity implements Obstacle {

    private long timeSet;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timeSet = Timer.now();
    }


    @Override
    public void update() {
        
    }

    public boolean explode() {
        return Timer.now() - timeSet > Timer.TIME_FOR_BOMB_EXPLODE;
    }
    
    @Override
    public void render(GraphicsContext gc, Camera camera) {
        gc.drawImage(Sprite.grass.getFxImage(), x - camera.getX(), y - camera.getY());
        super.render(gc, camera);
    }


    @Override
    public Image chooseSprite() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
