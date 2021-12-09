package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.net.DatagramPacket;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.GameMenu;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.controller.Sound;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Graphics;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.SocketGame;
import uet.oop.bomberman.controller.Camera;

public class Bomber extends DestroyableEntity {
    private KeyListener keyListener;
    private CollisionManager collisionManager;
    private Boolean isCamFollow = false;
    private BombManager bombManager;
    protected int curNumberInMap;
    private Pair<Integer, Integer> lastBombCoordinate;
    private boolean ready = false;

    public Bomber(int x, int y, Image img, KeyListener keyListener, CollisionManager collisionManager) {
        super(x, y, img);
        this.keyListener = keyListener;
        this.collisionManager = collisionManager;
        bombManager = new BombManager(collisionManager);
        speed = 2;
    }

    public void setIsCamFollow(Boolean is) {
        isCamFollow = is;
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
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + speed, y, DIRECTION.RIGHT);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle ||
                    checkCollide(x + speed, y))) {
                super.update(DIRECTION.RIGHT, true, indexOfFlex);
            } else {
                super.update(DIRECTION.RIGHT, false, indexOfFlex);
            }
        }

        // Handle Key Press A
        if (keyListener.isPressed(KeyCode.A)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - speed, y, DIRECTION.LEFT);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle ||
                    checkCollide(x - speed, y))) {
                super.update(DIRECTION.LEFT, true, indexOfFlex);
            } else {
                super.update(DIRECTION.LEFT, false, indexOfFlex);
            }

        }
        // Handle Key Press W
        if (keyListener.isPressed(KeyCode.W))

        {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - speed, DIRECTION.UP);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle ||
                    checkCollide(x, y - speed))) {
                super.update(DIRECTION.UP, true, indexOfFlex);
            } else {
                super.update(DIRECTION.UP, false, indexOfFlex);
            }
        }

        // Handle Key Press S
        if (keyListener.isPressed(KeyCode.S)) {
            Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + speed, DIRECTION.DOWN);
            if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle ||
                    checkCollide(x, y + speed))) {
                super.update(DIRECTION.DOWN, true, indexOfFlex);
            } else {
                super.update(DIRECTION.DOWN, false, indexOfFlex);
            }
        }
    }

    public void updateItems(){
        Entity item = collisionManager.getEntityAt((x+16) / Sprite.SCALED_SIZE, (y+16) / Sprite.SCALED_SIZE);
        if (item instanceof Items) {
            Sound.item.play();
            System.out.println(item);
            Items tmp = (Items) item;
            tmp.powerUp(this);
            collisionManager.getMap().replace((x+16) / Sprite.SCALED_SIZE, (y+16) / Sprite.SCALED_SIZE,
                    new Grass((x+16) / Sprite.SCALED_SIZE, (y+16) / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
        }
    }

    private boolean checkCollide(int xPredict, int yPredict) {
        boolean result = false;
        boolean onLastBomb = false;
        for (int i = 0; i < bombManager.getBombs().size(); i++) {
            if (collisionManager.collide(xPredict, yPredict, bombManager.getBombs().get(i))) {
                if (lastBombCoordinate != null) {
                    if (bombManager.getBombs().get(i).x / Sprite.SCALED_SIZE == lastBombCoordinate.getKey() &&
                            bombManager.getBombs().get(i).y / Sprite.SCALED_SIZE == lastBombCoordinate.getValue()) {
                        onLastBomb = true;
                        result = false;
                    } else {
                        if (i == bombManager.getBombs().size() - 2) {
                            return false;
                        }
                        result = true;
                        break;
                    }
                } else {
                    result = true;
                    break;
                }
            }
        }
        if (!onLastBomb)
            lastBombCoordinate = null;
        return result;
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
            Bomb bomb = new Bomb(xBomb, yBomb, Sprite.bomb.getFxImage(), bombManager.getFlame());
            // getMap().replace(xBomb, yBomb, bomb);
            if (bombManager.canSetBomb(xBomb, yBomb)) {
                switch (GameMenu.gameState) {
                    case IN_SINGLE_GAME:
                        lastBombCoordinate = new Pair<Integer, Integer>(xBomb, yBomb);
                        bombManager.addBomb(bomb);
                        break;
                    case IN_MULTIPLAYER_GAME:
                    case IN_SURVIVAL_GAME:
                        // TODO: Check if this is suitable
                        lastBombCoordinate = new Pair<Integer, Integer>(xBomb, yBomb);

                        String msg = indexOfFlex + ",Bomb," + xBomb + "," + yBomb;
                        byte[] data = msg.getBytes();
                        DatagramPacket outPacket = new DatagramPacket(data, data.length, SocketGame.address,
                                SocketGame.PORT);
                        try {
                            SocketGame.socket.send(outPacket);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
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

        img = chooseSprite();
        isRunning = false;
        if (isCamFollow == true) {
            int xRender = x;
            int yRender = y;
            if (camera.getX() > 0 && camera.getX() < camera.getScreenWidth() * Sprite.SCALED_SIZE
                    - Graphics.WIDTH * Sprite.SCALED_SIZE) {
                xRender = Graphics.WIDTH * Sprite.DEFAULT_SIZE;
            } else if (camera.getX() == camera.getScreenWidth() * Sprite.SCALED_SIZE
                    - Graphics.WIDTH * Sprite.SCALED_SIZE) {
                xRender = x - (camera.getScreenWidth() * Sprite.SCALED_SIZE - Graphics.WIDTH * Sprite.SCALED_SIZE);
            }

            if (camera.getY() > 0 && camera.getY() < camera.getScreenHeight() * Sprite.SCALED_SIZE
                    - Graphics.HEIGHT * Sprite.SCALED_SIZE) {
                yRender = Graphics.HEIGHT * Sprite.DEFAULT_SIZE;
            } else if (camera.getY() == camera.getScreenHeight() * Sprite.SCALED_SIZE
                    - Graphics.HEIGHT * Sprite.SCALED_SIZE) {
                yRender = y - (camera.getScreenHeight() * Sprite.SCALED_SIZE - Graphics.HEIGHT * Sprite.SCALED_SIZE);
            }

            gc.drawImage(img, xRender, yRender);

        } else {
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        }
        // Render bombs front of Bomberman;
        bombManager.renderBombs(gc, camera);
    }

    @Override
    public void die() {
        // TODO Auto-generated method stub
        death = true;
    }

    public int getFlame() {
        return bombManager.getFlame();
    }

    public void setFlame(int flame) {
        bombManager.setFlame(flame);
    }

    public int getNumberOfBombs() {
        return bombManager.getNumberOfBombs();
    }

    public void setNumberOfBombs(int numberOfBombs) {
        bombManager.setNumberOfBombs(numberOfBombs);
    }

    public BombManager getBombManager() {
        return bombManager;
    }

    public boolean getReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
