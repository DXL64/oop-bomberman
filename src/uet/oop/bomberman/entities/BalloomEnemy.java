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
        ++count;
        if (death) return;
        if (count % 4 == 0 || count % 4 == 2 || count % 4 == 3)
            return;
        if (count % 64 == 1) {
            goRand();
            return;
        }
        if (direction == DIRECTION.LEFT) {
            if (checkCollide(x, y)) {
                goRand();
            }
            else goLeft();
        }
        else if (direction == DIRECTION.RIGHT) {
            if (checkCollide(x, y)) {
                goRand();
            }
            else goRight();
        }
        else if (direction == DIRECTION.DOWN) {
            if (checkCollide(x, y)) {
                goRand();
            }
            else goDown();
        }
        else if (direction == DIRECTION.UP) {
            if (checkCollide(x, y)) {
                goRand();
            }
            else goUp();
        }
        //super.update();
    }
    
    public Image chooseSprite() {
        if (death) {
            if (count < 30) {       
                return Sprite.balloom_dead.getFxImage();
            }
            return null;
        }
        spriteImage = count % 9;
        if(direction == DIRECTION.LEFT || direction == DIRECTION.UP){
            switch(spriteImage / 3){
                case 0: return Sprite.balloom_left1.getFxImage();
                case 1: return Sprite.balloom_left2.getFxImage();
                case 2: return Sprite.balloom_left3.getFxImage();
            }
        }
        else if(direction == DIRECTION.RIGHT || direction == DIRECTION.DOWN){
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