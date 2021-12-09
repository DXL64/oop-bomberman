package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.controller.Timer;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimationEntity implements Obstacle {

    private long timeSet;
    private boolean exploded;
    private int flame;

    public Bomb(int xUnit, int yUnit, Image img, int flame) {
        super(xUnit, yUnit, img);
        timeSet = Timer.now();
        count = 0;
        exploded = false;
        this.flame = flame;
    }

    @Override
    public void update() {

        count++;
        count = count % 75;

        img = chooseSprite();
        if (!exploded) exploded = Timer.now() - timeSet > Timer.TIME_FOR_BOMB_EXPLODE;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public Image chooseSprite() {
        int chooseFrame = count / 25;
        switch (chooseFrame) {
            case 0:
                return Sprite.bomb.getFxImage();
            case 1:
                return Sprite.bomb_1.getFxImage();
            case 2:
                return Sprite.bomb_2.getFxImage();
        }

        return null;
    }

    public void setFlame(int flame){
        this.flame = flame;
    }
    public int getFlame(){
        return flame;
    }
}
