package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.controller.Timer;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimationEntity implements Obstacle {

    private long timeSet;
    private boolean exploded;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        timeSet = Timer.now();
        countStep = 0;
        exploded = false;
    }

    @Override
    public void update() {

        countStep++;
        countStep = countStep % 75;

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
        int chooseFrame = countStep / 25;
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

}
