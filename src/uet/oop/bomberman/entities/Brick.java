package uet.oop.bomberman.entities;

import javafx.scene.image.Image;


public class Brick extends DestroyableEntity implements Obstacle {
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void die() {
        // TODO Auto-generated method stub
        death = false;
    }

}
