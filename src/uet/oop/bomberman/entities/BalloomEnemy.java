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

    public BalloomEnemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager){
        super(xUnit, yUnit, img, collisionManager);
        this.speed = 2;
    }

    public void update(){
        ++count;
        if(count % 4 == 0 || count % 4 == 2 || count % 4 == 3) return;
        if(count % 64 == 1){
            goRand();
            return;
        }
        if(direction == moveLeft) goLeft();
        else if(direction == moveRight) goRight();
        else if(direction == moveDown) goDown();
        else if(direction == moveUp) goUp();
        return;
    }
    public Boolean goLeft(){
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - speed, y, DIRECTION.LEFT);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x -= speed;
            direction = moveLeft;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }
    public Boolean goRight(){
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + speed, y, DIRECTION.RIGHT);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x += speed;
            direction = moveRight;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }
    public Boolean goUp(){
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - speed, DIRECTION.UP);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            y -= speed;
            direction = moveUp;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }
    public Boolean goDown(){
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
        if(direction == moveLeft || direction == moveUp){
            switch(spriteImage){
                case 0: return Sprite.balloom_left1.getFxImage();
                case 1: return Sprite.balloom_left2.getFxImage();
                case 2: return Sprite.balloom_left3.getFxImage();
            }
        }
        else if(direction == moveRight || direction == moveDown){
            switch(spriteImage){
                case 0: return Sprite.balloom_right1.getFxImage();
                case 1: return Sprite.balloom_right2.getFxImage();
                case 2: return Sprite.balloom_right3.getFxImage();
            }
        }
        return null;
    }
    @Override
    public void render(GraphicsContext gc, Camera camera) {
        Image img = chooseSprite();
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }
}