package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;
import javafx.util.Pair;
import uet.oop.bomberman.controller.Camera;

public class BalloomEnemy extends Enemy {
    public static final int delayFPS = 4;

    public BalloomEnemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager) {
        super(xUnit, yUnit, img, collisionManager);
        this.speed = 2;
        sizeCheckCollision = speed;
    }

    public void update() {
        if (death) {
            super.update();
        } else {
            ++count;
            if (count % 4 == 0 || count % 4 == 2 || count % 4 == 3)
                return;
            if (count % 64 == 1) {
                goRand();
                return;
            }
            if (direction == moveLeft) {
                if (checkCollide(x, y)) {
                    goRand();
                }
                else goLeft();
            }
            else if (direction == moveRight) {
                if (checkCollide(x, y)) {
                    goRand();
                }
                else goRight();
            }
            else if (direction == moveDown) {
                if (checkCollide(x, y)) {
                    goRand();
                }
                else goDown();
            }
            else if (direction == moveUp) {
                if (checkCollide(x, y)) {
                    goRand();
                }
                else goUp();
            }
        }
    }

    public Boolean goLeft() {
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - speed, y, DIRECTION.LEFT);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x -= speed;
            direction = moveLeft;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }

    public Boolean goRight() {
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + speed, y, DIRECTION.RIGHT);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x += speed;
            direction = moveRight;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }

    public Boolean goUp() {
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - speed, DIRECTION.UP);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            y -= speed;
            direction = moveUp;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }

    public Boolean goDown() {
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + speed, DIRECTION.DOWN);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            y += speed;
            direction = moveDown;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }
    
    public Image chooseSprite() {
        spriteImage = count % 9;
        if(direction == moveLeft || direction == moveUp){
            switch(spriteImage / 3){
                case 0: return Sprite.balloom_left1.getFxImage();
                case 1: return Sprite.balloom_left2.getFxImage();
                case 2: return Sprite.balloom_left3.getFxImage();
            }
        }
        else if(direction == moveRight || direction == moveDown){
            switch(spriteImage / 3){
                case 0: return Sprite.balloom_right1.getFxImage();
                case 1: return Sprite.balloom_right2.getFxImage();
                case 2: return Sprite.balloom_right3.getFxImage();
            }
        }
        return null;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        if (countStep < 30) {
            Image img = chooseSprite();
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        }
    }

    public void die() {
        if (!death) {
            death = true;
            count = 0;
            countStep = 0;
        }
    }
}