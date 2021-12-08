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
import uet.oop.bomberman.graphics.Graphics;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.controller.Camera;

public class AutoPlayBomber extends Bomber{
    public AutoPlayBomber(int x, int y, Image img, KeyListener keyListener, CollisionManager collisionManager){
        super(x, y, img, keyListener, collisionManager);
    }
    public void update(Map map){
        
    }
}
