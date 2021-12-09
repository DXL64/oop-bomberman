package uet.oop.bomberman.entities;

import javax.xml.stream.events.StartDocument;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
    public static final int code = 4;
    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        gc.drawImage(Sprite.grass.getFxImage(), x - camera.getX(), y - camera.getY());
        super.render(gc, camera);
    }    

}
