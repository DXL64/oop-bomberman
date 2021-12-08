package uet.oop.bomberman.entities;

import uet.oop.bomberman.controller.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Items extends Entity{
    public Items(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        
    }
    @Override
    public void render(GraphicsContext gc, Camera camera) {
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }

    public abstract void powerUp(Bomber bomber);
}
