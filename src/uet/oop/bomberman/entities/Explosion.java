package uet.oop.bomberman.entities;

import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.PickResult;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends AnimationEntity {

    public static enum EXPLOSION_STATE {
        BEGIN, MIDDLE, END;
    }

    private DIRECTION direction;
    private boolean exploded;
    private EXPLOSION_STATE explosionState;

    public Explosion(int xUnit, int yUnit, Image img, DIRECTION direction, EXPLOSION_STATE explosionState) {
        super(xUnit, yUnit, img);
        this.direction = direction;
        this.explosionState = explosionState;
        exploded = false;
        countStep = 0;
    }

    public void update() {
        countStep++;

        if (countStep == 15)
            exploded = true;

        img = chooseSprite();
    }

    public boolean isExploed() {
        return isExploed();
    }

    @Override
    public Image chooseSprite() {
        int chooseFrame = countStep / 5;

        switch (explosionState) {
            case BEGIN:
                switch (chooseFrame) {
                    case 0:
                        switch (direction) {
                            case RIGHT:
                                return Sprite.explosion_horizontal_left_last2.getFxImage();
                            case LEFT:
                                return Sprite.explosion_horizontal_right_last2.getFxImage();
                            case UP:
                                return Sprite.explosion_vertical_down_last2.getFxImage();
                            case DOWN:
                                return Sprite.explosion_vertical_top_last2.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded2.getFxImage();
                        }
                    case 1:
                        switch (direction) {
                            case RIGHT:
                                return Sprite.explosion_horizontal_left_last1.getFxImage();
                            case LEFT:
                                return Sprite.explosion_horizontal_right_last1.getFxImage();
                            case UP:
                                return Sprite.explosion_vertical_down_last1.getFxImage();
                            case DOWN:
                                return Sprite.explosion_vertical_top_last1.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded1.getFxImage();
                        }
                    case 2:
                        switch (direction) {
                            case RIGHT:
                                return Sprite.explosion_horizontal_left_last.getFxImage();
                            case LEFT:
                                return Sprite.explosion_horizontal_right_last.getFxImage();
                            case UP:
                                return Sprite.explosion_vertical_down_last.getFxImage();
                            case DOWN:
                                return Sprite.explosion_vertical_top_last.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded.getFxImage();
                        }
                }
            case MIDDLE:
                switch (chooseFrame) {
                    case 0:
                        switch (direction) {
                            case RIGHT:
                            case LEFT:
                                return Sprite.explosion_horizontal2.getFxImage();
                            case UP:
                            case DOWN:
                                return Sprite.explosion_vertical2.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded2.getFxImage();
                        }
                    case 1:
                        switch (direction) {
                            case RIGHT:
                            case LEFT:
                                return Sprite.explosion_horizontal1.getFxImage();
                            case UP:
                            case DOWN:
                                return Sprite.explosion_vertical1.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded1.getFxImage();
                        }
                    case 2:
                        switch (direction) {
                            case RIGHT:
                            case LEFT:
                                return Sprite.explosion_horizontal.getFxImage();
                            case UP:
                            case DOWN:
                                return Sprite.explosion_vertical.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded.getFxImage();
                        }
                }
            case END:
                switch (chooseFrame) {
                    case 0:
                        switch (direction) {
                            case RIGHT:
                                return Sprite.explosion_horizontal_right_last2.getFxImage();
                            case LEFT:
                                return Sprite.explosion_horizontal_left_last2.getFxImage();
                            case UP:
                                return Sprite.explosion_vertical_top_last2.getFxImage();
                            case DOWN:
                                return Sprite.explosion_vertical_down_last2.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded2.getFxImage();
                        }
                    case 1:
                        switch (direction) {
                            case RIGHT:
                                return Sprite.explosion_horizontal_right_last1.getFxImage();
                            case LEFT:
                                return Sprite.explosion_horizontal_left_last1.getFxImage();
                            case UP:
                                return Sprite.explosion_vertical_top_last1.getFxImage();
                            case DOWN:
                                return Sprite.explosion_vertical_down_last1.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded1.getFxImage();
                        }
                    case 2:
                        switch (direction) {
                            case RIGHT:
                                return Sprite.explosion_horizontal_right_last.getFxImage();
                            case LEFT:
                                return Sprite.explosion_horizontal_left_last.getFxImage();
                            case UP:
                                return Sprite.explosion_vertical_top_last.getFxImage();
                            case DOWN:
                                return Sprite.explosion_vertical_down_last.getFxImage();
                            case CENTER:
                                return Sprite.bomb_exploded.getFxImage();
                        }
                }
        }
        return null;
    }

}
