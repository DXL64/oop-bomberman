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
import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.controller.CollisionManager.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy extends Entity {
    protected CollisionManager collisionManager;
    public final static int notGo = 0;
    public final static int moveLeft = -1;
    public final static int moveRight = 1;
    public final static int moveUp = -2;
    public final static int moveDown = 2;
    protected int count = 0;
    protected int spriteImage = 0;
    protected int direction = moveLeft;
    protected int speed;
    protected int sizeCheckCollision;

    public final static int OBSTACLE = 0;
    public final static int GRASS = 1;
    public final static int BOMBERMAN = 2;
    public final static int ENEMY = 3;

    public Enemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager) {
        super(xUnit, yUnit, img);
        this.collisionManager = collisionManager;
    }

    public void update() {
    }

    public void update(List<List<Entity>> map, int xBomber, int yBomber) {
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
    };

    public Boolean goLeft() {
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - sizeCheckCollision, y, moveLeft);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x -= speed;
            direction = moveLeft;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        return false;
    }

    protected Boolean goRight() {
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + sizeCheckCollision, y, moveRight);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x += speed;
            direction = moveRight;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        return false;
    }

    protected Boolean goUp() {
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - sizeCheckCollision, moveUp);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            y -= speed;
            direction = moveUp;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        return false;
    }

    protected Boolean goDown() {
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + sizeCheckCollision, moveDown);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            System.out.println(speed);
            y += speed;
            direction = moveDown;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        return false;
    }
}
