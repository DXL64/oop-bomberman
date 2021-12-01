package uet.oop.bomberman.entities;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.controller.CollisionManager.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;
import javafx.util.Pair;
import uet.oop.bomberman.controller.Camera;

public class BalloomEnemy extends Enemy {
    private int count = 0;
    private int spriteImage = 0;
    private int direction = moveLeft;
    public static final int delayFPS = 4;

    public BalloomEnemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager){
        super(xUnit, yUnit, img, collisionManager);
        this.speed = 2;
    }

    public void update(){
        ++count;
        if(count % delayFPS == 0){
            int rand;
            switch(direction){
                case moveLeft:
                    if(goLeft() == true) return;
                    spriteImage = 0;
                    rand = (int)(Math.random() * 2);
                    switch(rand){
                        case 0:
                            if(goRight() == true) return;
                            if(goDown() == true) return;
                            if(goUp() == true) return;
                        case 1:
                            if(goDown() == true) return;
                            if(goUp() == true) return;
                            if(goRight() == true) return;
                    }
                case moveRight:
                    if(goRight() == true) return;
                    spriteImage = 0;
                    rand = (int)(Math.random() * 2);
                    switch(rand){
                        case 0:
                            if(goLeft() == true) return;
                            if(goUp() == true) return;
                            if(goDown() == true) return;
                        case 1:
                            if(goUp() == true) return;
                            if(goDown() == true) return;
                            if(goLeft() == true) return;
                    }
                case moveUp:
                    if(goUp() == true) return;
                    spriteImage = 0;
                    rand = (int)(Math.random() * 2);
                    switch(rand){
                        case 0:
                            if(goDown() == true) return;
                            if(goLeft() == true) return;
                            if(goRight() == true) return;
                        case 1:
                            if(goLeft() == true) return;
                            if(goRight() == true) return;
                            if(goDown() == true) return;
                    }
                case moveDown:
                    if(goDown() == true) return;
                    spriteImage = 0;
                    rand = (int)(Math.random() * 2);
                    switch(rand){
                        case 0:
                            if(goUp() == true) return;
                            if(goRight() == true) return;
                            if(goLeft() == true) return;
                        case 1:
                            if(goRight() == true) return;
                            if(goLeft() == true) return;
                            if(goUp() == true) return;
                    }

            }
        }
    }
    public Boolean goLeft(){
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - speed, y, moveLeft);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x -= speed;
            direction = moveLeft;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }
    public Boolean goRight(){
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + speed, y, moveRight);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x += speed;
            direction = moveRight;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }
    public Boolean goUp(){
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - speed, moveUp);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            y -= speed;
            direction = moveUp;
            spriteImage = (spriteImage + 1) % 3;
            return true;
        }
        return false;
    }
    public Boolean goDown(){
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + speed, moveDown);
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