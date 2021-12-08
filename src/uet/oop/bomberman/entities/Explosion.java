package uet.oop.bomberman.entities;

import java.util.List;

import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.image.Image;
import javafx.scene.input.PickResult;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends AnimationEntity {

    public static enum EXPLOSION_STATE {
        BRICK, MIDDLE, END;
    }

    private DIRECTION direction;
    private boolean exploded;
    private EXPLOSION_STATE explosionState;
    private CollisionManager collisionManager;

    public Explosion(int xUnit, int yUnit, Image img, DIRECTION direction, EXPLOSION_STATE explosionState, CollisionManager collisionManager) {
        super(xUnit, yUnit, img);
        this.direction = direction;
        this.explosionState = explosionState;
        this.collisionManager = collisionManager;
        exploded = false;
        countStep = 0;
    }

    public void update() {

        Map map = collisionManager.getMap();

        for (int i = 0; i < map.getFlexEntities().size(); i++) {
            if (map.getFlexEntities().get(i) instanceof DestroyableEntity) {
                DestroyableEntity tmp = (DestroyableEntity) map.getFlexEntities().get(i);
                if (collisionManager.collide(this, tmp)) {
                    tmp.die();
                }
            }
        }
        List<Bomb> bombs = collisionManager.getBombList();
        for (Bomb bomb : bombs) {
            if (x == bomb.x && y == bomb.y) {
                bomb.setExploded(true);
            }
        }

        countStep++;

        if (countStep == 30)
            exploded = true;

        img = chooseSprite();
    }

    public boolean isExploded() {
        return exploded;
    }

    @Override
    public Image chooseSprite() {
        int chooseFrame = countStep / 10;

        switch (explosionState) {
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
            case BRICK: 
                switch (chooseFrame) {
                    case 0:
                        return Sprite.brick_exploded.getFxImage();                
                    case 1:
                        return Sprite.brick_exploded1.getFxImage();                
                    case 2:
                        return Sprite.brick_exploded2.getFxImage();                
                }
        }
        return null;
    }


    public String toString() {
        return "Explosion [" + super.toString() + "]";
    }
}
