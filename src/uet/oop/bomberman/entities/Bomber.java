package uet.oop.bomberman.entities;

import java.net.DatagramPacket;
import java.net.Socket;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.MultiPlayerMap;
import uet.oop.bomberman.SocketGame;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.GameMenu;
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
    public boolean isRunning = false;
    public int direction = moveRight;
    public int backStep = moveRight;
    public int countStep = 0;
    private Boolean isCamFollow = false;
    private int curNumberInMap;
    

    public Bomber(int x, int y, Image img, KeyListener keyListener, CollisionManager collisionManager) {
        super(x, y, img);
        this.keyListener = keyListener;
        this.collisionManager = collisionManager;
        // System.out.println(this.getWidth() + " " + this.getHeight());
    }

    public void setIsCamFollow(Boolean is){
        isCamFollow = is;
    }

    public void setCurNumberInMap(int number){
        curNumberInMap = number;
    }

    @Override
    public void update() {
        if (keyListener.isPressed(KeyCode.D)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + CollisionManager.STEP, y, 1);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SINGLE_GAME) updateDirect(moveRight, true);
                else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME) sendMessageSocket(moveRight, true);
                return;
            }
            if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SINGLE_GAME) updateDirect(moveRight, false);
            else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME) sendMessageSocket(moveRight, false);
        }     
        if (keyListener.isPressed(KeyCode.A)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - CollisionManager.STEP, y, -1);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SINGLE_GAME) updateDirect(moveLeft, true);
                else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME) sendMessageSocket(moveLeft, true);
                return;
            }
            if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SINGLE_GAME) updateDirect(moveLeft, false);
            else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME) sendMessageSocket(moveLeft, false);
        }
        if (keyListener.isPressed(KeyCode.W)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - CollisionManager.STEP, -2);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SINGLE_GAME) updateDirect(moveUp, true);
                else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME) sendMessageSocket(moveUp, true);
                return;
            }
            if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SINGLE_GAME) updateDirect(moveUp, false);
            else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME) sendMessageSocket(moveUp, false);
        }
        if (keyListener.isPressed(KeyCode.S)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + CollisionManager.STEP, 2);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
                if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SINGLE_GAME) updateDirect(moveDown, true);
                else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME) sendMessageSocket(moveDown, true);
                return;
            }
            if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SINGLE_GAME) updateDirect(moveDown, false);
            else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME) sendMessageSocket(moveDown, false);
        }
    }
    public void updateDirect(int direct, Boolean success){
        direction = direct;
        isRunning = true;
        if(success == false) return;
        if(direct == moveDown) y += CollisionManager.STEP;
        if(direct == moveUp) y -= CollisionManager.STEP;
        if(direct == moveLeft) x -= CollisionManager.STEP;
        if(direct == moveRight) x += CollisionManager.STEP;
    }
    public void sendMessageSocket(int direct, Boolean success){
        String msg = new String();
        if(direct == moveDown) msg = curNumberInMap + ",S," + success;
        if(direct == moveUp) msg = curNumberInMap + ",W," + success;
        if(direct == moveLeft) msg = curNumberInMap + ",A," + success;
        if(direct == moveRight) msg = curNumberInMap + ",D," + success;
        byte[] data = msg.getBytes();
        DatagramPacket outPacket = new DatagramPacket(data, data.length, SocketGame.address, SocketGame.PORT);
        try {
            SocketGame.socket.send(outPacket);
        } catch (Exception e){
            e.printStackTrace();
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
                countStep = countStep%15;

            }
            else
                countStep = 0;
                switch (direction) {
                    case moveLeft:
                        backStep = moveLeft;
                        if(countStep/5 == 0) return Sprite.player_left.getFxImage();
                        if(countStep/5 == 1) return Sprite.player_left_1.getFxImage();
                        if(countStep/5 == 2) return Sprite.player_left_2.getFxImage();
                        break;
                    case moveRight:
                        backStep = moveRight;
                        if(countStep/5 == 0) return Sprite.player_right.getFxImage();
                        if(countStep/5 == 1) return Sprite.player_right_1.getFxImage();
                        if(countStep/5 == 2) return Sprite.player_right_2.getFxImage();
                        break;
                    case moveUp:
                        backStep = moveUp;
                        if(countStep/5 == 0) return Sprite.player_up.getFxImage();
                        if(countStep/5 == 1) return Sprite.player_up_1.getFxImage();
                        if(countStep/5 == 2) return Sprite.player_up_2.getFxImage();
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
        isRunning = false;
        if(isCamFollow == true){
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
        else {
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        }
    }
}
