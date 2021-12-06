package uet.oop.bomberman.entities;

import javax.swing.Spring;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.graphics.Sprite;


public class Tile extends Entity {
    
    public Tile(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        //TODO Auto-generated constructor stub
    }


    @Override
    public void update() {
        
    }



    @Override
    public void render(GraphicsContext gc, Camera camera) {
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }
    
}
