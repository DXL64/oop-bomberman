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
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.controller.CollisionManager.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy extends Entity {
    protected KeyListener keyListener;
    protected CollisionManager collisionManager;

    public Enemy(int xUnit, int yUnit, Image img, Scene scene, CollisionManager collisionManager){
        super(xUnit, yUnit, img);
        this.keyListener = new KeyListener(scene);
        this.collisionManager = collisionManager;
    }
    public Enemy(int xUnit, int yUnit, Image img, Hitbox hitbox, Scene scene, CollisionManager collisionManager) {
        super(xUnit, yUnit, img, hitbox);
        this.keyListener = new KeyListener(scene);
        this.collisionManager = collisionManager;
    }
    public void update(){

    };
}
