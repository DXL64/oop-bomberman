package uet.oop.bomberman.entities;

import java.net.DatagramPacket;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.SocketGame;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.GameMenu;
import uet.oop.bomberman.controller.Direction.DIRECTION;

public abstract class AnimationEntity extends Entity {
    protected DIRECTION direction = DIRECTION.RIGHT;
    protected DIRECTION backStep = DIRECTION.RIGHT;
    protected int countStep = 0;
    protected boolean isRunning = false;
    protected int speed;
    protected int indexOfFlex;

    public AnimationEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    public void update(DIRECTION direct, boolean success, int curNumberInMap) {
        switch (GameMenu.gameState) {
            case IN_SINGLE_GAME:
                updateDirect(direct, success);
                break;
            case IN_MULTIPLAYER_GAME:
            case IN_SURVIVAL_GAME:
                sendMessageSocket(direct, success, curNumberInMap);
                break;
            default:
                throw new AssertionError("Unhandled game state in AnimationEntity.update()");
        }
    }

    public void updateDirect(DIRECTION direct, boolean success) {
        if (success) {
            isRunning = true;
            direction = direct;
            if (direct == DIRECTION.DOWN)
                y += speed;
            if (direct == DIRECTION.UP)
                y -= speed;
            if (direct == DIRECTION.LEFT)
                x -= speed;
            if (direct == DIRECTION.RIGHT)
                x += speed;
        } else {
            isRunning = false;
        }
    }

    public void sendMessageSocket(DIRECTION direct, boolean success, int curNumberInMap) {
        String msg = new String();
        if (direct == DIRECTION.DOWN)
            msg = curNumberInMap + ",S," + success;
        if (direct == DIRECTION.UP)
            msg = curNumberInMap + ",W," + success;
        if (direct == DIRECTION.LEFT)
            msg = curNumberInMap + ",A," + success;
        if (direct == DIRECTION.RIGHT)
            msg = curNumberInMap + ",D," + success;
        byte[] data = msg.getBytes();
        DatagramPacket outPacket = new DatagramPacket(data, data.length, SocketGame.address, SocketGame.PORT);
        try {
            SocketGame.socket.send(outPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setIndexOfFlex(int indexOfFlex){
        this.indexOfFlex = indexOfFlex;
    }
    public int getIndexOfFlex(){
        return indexOfFlex;
    }

    public abstract Image chooseSprite();

}
