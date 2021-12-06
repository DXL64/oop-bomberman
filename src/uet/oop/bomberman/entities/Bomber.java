package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

import java.net.DatagramPacket;
import java.net.Socket;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.MultiPlayerMap;
import uet.oop.bomberman.SocketGame;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.GameMenu;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.controller.Timer;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Graphics;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.controller.Camera;

public class Bomber extends AnimationEntity {
    private List<Bomb> bombs = new ArrayList<>();
    private KeyListener keyListener;
    private CollisionManager collisionManager;
    private int numberOfBombs;
    private long delayBombSet;
    private Boolean isCamFollow = false;
    protected int curNumberInMap;

    public Bomber(int x, int y, Image img, KeyListener keyListener, CollisionManager collisionManager) {
        super(x, y, img);
        this.keyListener = keyListener;
        this.collisionManager = collisionManager;
        numberOfBombs = 10;
        delayBombSet = Timer.now();
    }

    public Map getMap() {
        return collisionManager.getMap();
    }

    public void setIsCamFollow(Boolean is) {
        isCamFollow = is;
    }

    public void setCurNumberInMap(int number) {
        curNumberInMap = number;
    }

    @Override
    public void update() {

        updateBomberman();
        updateBombs();

    }

    private void updateBomberman() {
        isRunning = false;

        // Handle Key Press D
        if (keyListener.isPressed(KeyCode.D)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + CollisionManager.STEP, y, DIRECTION.RIGHT);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                isRunning = true;
                super.update(DIRECTION.RIGHT, true, curNumberInMap);

                return;
            }
            super.update(DIRECTION.RIGHT, false, curNumberInMap);

        }

        // Handle Key Press A
        if (keyListener.isPressed(KeyCode.A)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - CollisionManager.STEP, y, DIRECTION.LEFT);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                isRunning = true;
                super.update(DIRECTION.LEFT, true, curNumberInMap);
                return;
            }
            super.update(DIRECTION.LEFT, false, curNumberInMap);

        }
        // Handle Key Press W
        if (keyListener.isPressed(KeyCode.W))

        {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - CollisionManager.STEP, DIRECTION.UP);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                isRunning = true;
                super.update(DIRECTION.UP, true, curNumberInMap);
                return;
            }
            super.update(DIRECTION.UP, false, curNumberInMap);
        }

        // Handle Key Press S
        if (keyListener.isPressed(KeyCode.S))

        {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + CollisionManager.STEP, DIRECTION.DOWN);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                isRunning = true;
                super.update(DIRECTION.DOWN, true, curNumberInMap);
                return;
            }
            super.update(DIRECTION.DOWN, false, curNumberInMap);
        }

    }

    private void updateBombs() {
        // Handle Key Press SPACE
        if (keyListener.isPressed(KeyCode.SPACE)) {
            int xBomb = x + Sprite.DEFAULT_SIZE;
            xBomb = xBomb - xBomb % Sprite.SCALED_SIZE;
            int yBomb = y + Sprite.DEFAULT_SIZE;
            yBomb = yBomb - yBomb % Sprite.SCALED_SIZE;
            xBomb /= Sprite.SCALED_SIZE;
            yBomb /= Sprite.SCALED_SIZE;
            Bomb bomb = new Bomb(xBomb, yBomb, Sprite.bomb.getFxImage());
            // getMap().replace(xBomb, yBomb, bomb);
            if (canSetBomb(xBomb, yBomb))
                bombs.add(bomb);
        }
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).explode()) {
                removeBomb(i);
            }
        }
    }

    private void removeBomb(int iBomb) {
        // int x = bombs.get(iBomb).getX();
        // int y = bombs.get(iBomb).getY();
        bombs.remove(iBomb);

        // System.out.println(x / Sprite.SCALED_SIZE + " " + y / Sprite.SCALED_SIZE);

        // getMap().replace(x, y, new Grass(x / Sprite.SCALED_SIZE, y /
        // Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));

    }

    private boolean canSetBomb(int xBomb, int yBomb) {
        long now = Timer.now();
        if (now - delayBombSet > Timer.TIME_FOR_SINGLE_INPUT) {
            delayBombSet = now;
            if (bombs.size() < numberOfBombs) {
                for (Bomb bomb : bombs) {
                    if (bomb.getX() / Sprite.SCALED_SIZE == xBomb && bomb.getY() / Sprite.SCALED_SIZE == yBomb)
                        return false;
                }
                if (collisionManager.getEntityAt(xBomb, yBomb) instanceof Grass)
                    return true;
                else
                    return false;
            } else
                return false;
        } else
            return false;
    }

    public Image chooseSprite() {
        if (!isRunning) {
            switch (direction) {
                case LEFT:
                    return Sprite.player_left.getFxImage();
                case RIGHT:
                    return Sprite.player_right.getFxImage();
                case UP:
                    return Sprite.player_up.getFxImage();
                case DOWN:
                    return Sprite.player_down.getFxImage();
                default:
                    return Sprite.player_right.getFxImage();
            }
        } else {
            if (direction == backStep) {
                countStep++;
                countStep = countStep % 15;

            } else
                countStep = 0;

            int chooseFrame = countStep / 5;

            switch (direction) {
                case LEFT:
                    backStep = DIRECTION.LEFT;
                    if (chooseFrame == 0)
                        return Sprite.player_left.getFxImage();
                    if (chooseFrame == 1)
                        return Sprite.player_left_1.getFxImage();
                    if (chooseFrame == 2)
                        return Sprite.player_left_2.getFxImage();
                    break;
                case RIGHT:
                    backStep = DIRECTION.RIGHT;
                    if (chooseFrame == 0)
                        return Sprite.player_right.getFxImage();
                    if (chooseFrame == 1)
                        return Sprite.player_right_1.getFxImage();
                    if (chooseFrame == 2)
                        return Sprite.player_right_2.getFxImage();
                    break;
                case UP:
                    backStep = DIRECTION.UP;
                    if (chooseFrame == 0)
                        return Sprite.player_up.getFxImage();
                    if (chooseFrame == 1)
                        return Sprite.player_up_1.getFxImage();
                    if (chooseFrame == 2)
                        return Sprite.player_up_2.getFxImage();
                    break;
                case DOWN:
                    backStep = DIRECTION.DOWN;
                    if (chooseFrame == 0)
                        return Sprite.player_down.getFxImage();
                    if (chooseFrame == 1)
                        return Sprite.player_down_1.getFxImage();
                    if (chooseFrame == 2)
                        return Sprite.player_down_2.getFxImage();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid game state");
            }
        }
        return img;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        // Render bombs behind Bomberman;
        for (Entity bomb : bombs) {
            bomb.render(gc, camera);
        }
        img = chooseSprite();
        if (isCamFollow == true) {
            if (camera.getX() > 0 && camera.getX() < camera.getScreenWidth() * Sprite.SCALED_SIZE
                    - Graphics.WIDTH * Sprite.SCALED_SIZE) {
                int tempX = Graphics.WIDTH * Sprite.DEFAULT_SIZE;
                gc.drawImage(img, tempX, y);
            } else if (camera.getX() == camera.getScreenWidth() * Sprite.SCALED_SIZE
                    - Graphics.WIDTH * Sprite.SCALED_SIZE) {
                int tempX = x - (camera.getScreenWidth() * Sprite.SCALED_SIZE - Graphics.WIDTH * Sprite.SCALED_SIZE);
                gc.drawImage(img, tempX, y);
            } else {
                gc.drawImage(img, x, y);
            }
        } else {
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        }
    }
}
