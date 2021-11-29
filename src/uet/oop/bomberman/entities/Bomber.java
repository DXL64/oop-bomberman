package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.controller.CollisionManager.DIRECTION;
import uet.oop.bomberman.graphics.Graphics;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.controller.Camera;

public class Bomber extends Entity {

    private KeyListener keyListener;
    private CollisionManager collisionManager;
    private Map map;
    public final static int moveLeft = -1;
    public final static int moveRight = 1;
    public final static int moveUp = -2;
    public final static int moveDown = 2;
    public static boolean isRunning = false;
    public static int direction = moveRight;
    public static int backStep = moveRight;
    public static int countStep = 0;
    

    public Bomber(int x, int y, Image img, KeyListener keyListener, CollisionManager collisionManager) {
        super(x, y, img);
        this.keyListener = keyListener;
        this.collisionManager = collisionManager;
        // System.out.println(this.getWidth() + " " + this.getHeight());
    }

    @Override
    public void update() {
        isRunning = false;
        if (keyListener.isPressed(KeyCode.D)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + CollisionManager.STEP, y, 1);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                x += CollisionManager.STEP;
            }
            isRunning = true;
            direction = moveRight;
        }     
        if (keyListener.isPressed(KeyCode.A)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - CollisionManager.STEP, y, -1);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                x -= CollisionManager.STEP;
            }
            isRunning = true;
            direction = moveLeft;
        }
        if (keyListener.isPressed(KeyCode.W)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - CollisionManager.STEP, -2);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                y -= CollisionManager.STEP;
            }
            isRunning = true;
            direction = moveUp;
        }
        if (keyListener.isPressed(KeyCode.S)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + CollisionManager.STEP, 2);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                y += CollisionManager.STEP;
            }
            isRunning = true;
            direction = moveDown;
        }
    }
    public Image chooseSprite() {
        if(!isRunning) {
            switch(direction) {
                case moveLeft:
                    return Sprite.player_left.getFxImage();
                case moveRight:
                    return Sprite.player_right.getFxImage();
                case moveUp:
                    return Sprite.player_up.getFxImage();
                case moveDown:
                    return Sprite.player_down.getFxImage();
                default:
                    return Sprite.player_right.getFxImage();
            }
        }
        else {
            if(direction == backStep) {
                countStep++;
                countStep = countStep%3;

            }
            else
                countStep = 0;
            switch (direction) {
                case moveLeft:
                    backStep = moveLeft;
                    if(countStep == 0) return Sprite.player_left.getFxImage();
                    if(countStep == 1) return Sprite.player_left_1.getFxImage();
                    if(countStep == 2) return Sprite.player_left_2.getFxImage();
                    break;
                case moveRight:
                    backStep = moveRight;
                    if(countStep == 0) return Sprite.player_right.getFxImage();
                    if(countStep == 1) return Sprite.player_right_1.getFxImage();
                    if(countStep == 2) return Sprite.player_right_2.getFxImage();
                    break;
                case moveUp:
                    backStep = moveUp;
                    if(countStep == 0) return Sprite.player_up.getFxImage();
                    if(countStep == 1) return Sprite.player_up_1.getFxImage();
                    if(countStep == 2) return Sprite.player_up_2.getFxImage();
                    break;
                case moveDown:
                    backStep = moveDown;
                    if(countStep == 0) return Sprite.player_down.getFxImage();
                    if(countStep == 1) return Sprite.player_down_1.getFxImage();
                    if(countStep == 2) return Sprite.player_down_2.getFxImage();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid game state");
            }
        }
        return img;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        
        img = chooseSprite();
        // TODO Auto-generated method stub
        if (camera.getX() > 0 && camera.getX() < camera.getScreenWidth() * Sprite.SCALED_SIZE - Graphics.WIDTH * Sprite.SCALED_SIZE) {
            int tempX = Graphics.WIDTH * Sprite.DEFAULT_SIZE;
            gc.drawImage(img, tempX, y);
        } else if (camera.getX() == camera.getScreenWidth() * Sprite.SCALED_SIZE - Graphics.WIDTH * Sprite.SCALED_SIZE) {
            int tempX = x - (camera.getScreenWidth()* Sprite.SCALED_SIZE - Graphics.WIDTH * Sprite.SCALED_SIZE);
            gc.drawImage(img, tempX, y);
        } else {
            gc.drawImage(img, x, y);
        }
    }
}