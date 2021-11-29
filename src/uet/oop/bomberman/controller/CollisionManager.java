package uet.oop.bomberman.controller;

import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Obstacle;
import uet.oop.bomberman.graphics.Sprite;

public class CollisionManager {
    private Map map;

    public final static int STEP = 2;
    public final static int fixWidth = 3;
    public final static int fixHeight = 2;
    public final static int moveLeft = -1;
    public final static int moveRight = 1;
    public final static int moveUp = -2;
    public final static int moveDown = 2;
    public enum DIRECTION {
        UP, DOWN, LEFT, RIGHT;
    }


    public CollisionManager(Map map) {
        this.map = map;
    }
 
    public Pair<Entity, Entity> checkCollision(int x, int y, int move) {
        Entity object1, object2;
        switch(move) {
            case moveLeft: 
                object1 = map.getCoordinate(x + fixWidth, y + fixHeight);
                object2 = map.getCoordinate(x + fixWidth, y + Sprite.SCALED_SIZE - fixHeight);
                break;
            case moveRight:
                object1 = map.getCoordinate(x + 24 - fixWidth, y + fixHeight);
                object2 = map.getCoordinate(x + 24 - fixWidth, y + Sprite.SCALED_SIZE - fixHeight);
                break;
            case moveUp:
                object1 = map.getCoordinate(x + fixWidth, y + fixHeight);
                object2 = map.getCoordinate(x + 24 - fixWidth, y + fixHeight);
                break;
            case moveDown:
                object1 = map.getCoordinate(x + fixWidth, y + Sprite.SCALED_SIZE - fixHeight);
                object2 = map.getCoordinate(x + 24 - fixWidth, y + Sprite.SCALED_SIZE - fixHeight);
                break;
            default:
                object1 = map.getCoordinate(x, y);
                object2 = map.getCoordinate(x, y);
                break;
        }
        return new Pair<Entity, Entity> (object1, object2);
    }
    // public boolean go(DIRECTION direction, int x, int y) {
    //     switch (direction) {
    //         case RIGHT:
    //             // int temp = x % Sprite.SCALED_SIZE;
    //             // Entity object = map.getCoordinate(x, y);

    //             // if (x < object.getX() + Sprite.SCALED_SIZE &&
    //             //     x + Sprite.SCALED_SIZE > object.getX() &&
    //             //     y < object.getY() + Sprite.SCALED_SIZE &&
    //             //     Sprite.SCALED_SIZE + y > object.getY()) {
    //             //     return true;
    //             // } else {
    //             //     return false;
    //             // }
                
    //             // if (map.getCoordinate(x + 1 + Sprite.SCALED_SIZE, y) instanceof Obstacle) {
    //             //     return false;
    //             // } 
    //             // if (x % Sprite.SCALED_SIZE > FIXED_POINT) {
    //             //     if (map.getCoordinate(x + 1 + Sprite.SCALED_SIZE, y - Sprite.SCALED_SIZE) instanceof Obstacle) {
    //             //         return false;
    //             //     }
    //             // }
    //             //return true;
            
    //         case LEFT:
    //             //return true;

    //         case UP:
    //             //return true;

    //         case DOWN:
    //             //return true;
        
    //         default:
    //             throw new ArithmeticException("WRONG DRIECTION in [CollisionManager]");

    //     }
    // }
}