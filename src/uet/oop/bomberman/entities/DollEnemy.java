package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;

public class DollEnemy extends Enemy {
    public DollEnemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager) {
        super(xUnit, yUnit, img, collisionManager);
        this.speed = 2;
        this.isCheckCollision = false;
        sizeCheckCollision = speed;
    }

    public void update() {
        ++count;
        if (death) return;
        if (count % 3 == 0)
            return;
        if (count % 24 == 1) {
            goRand();
            return;
        }
        if (direction == DIRECTION.LEFT)
            goLeft();
        else if (direction == DIRECTION.RIGHT)
            goRight();
        else if (direction == DIRECTION.DOWN)
            goDown();
        else if (direction == DIRECTION.UP)
            goUp();
            
        super.update();
    }

    public Image chooseSprite() {
        if (death) {
            if (count < 30) {       
                return Sprite.doll_dead.getFxImage();
            }
            return null;
        }
        spriteImage = count % 9;
        if (direction == DIRECTION.LEFT || direction == DIRECTION.UP) {
            switch (spriteImage / 3) {
                case 0:
                    return Sprite.doll_left1.getFxImage();
                case 1:
                    return Sprite.doll_left2.getFxImage();
                case 2:
                    return Sprite.doll_left3.getFxImage();
            }
        } else if (direction == DIRECTION.RIGHT || direction == DIRECTION.DOWN) {
            switch (spriteImage / 3) {
                case 0:
                    return Sprite.doll_right1.getFxImage();
                case 1:
                    return Sprite.doll_right2.getFxImage();
                case 2:
                    return Sprite.doll_right3.getFxImage();
            }
        }
        return null;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        img = chooseSprite();
        if (death && count >= 30) return;
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }

    public void die() {
        if (!death) {
            death = true;
            count = 0;
        }
    }
}
