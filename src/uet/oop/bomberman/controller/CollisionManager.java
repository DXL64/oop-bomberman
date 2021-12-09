package uet.oop.bomberman.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class CollisionManager {
    private Map map;

    public final static int STEP = 2;
    public final static int FIX_WIDTH = 5;
    public final static int FIX_HEIGHT = 4;

    public CollisionManager(Map map) {
        this.map = map;
    }

    public Entity getEntityAt(int xMap, int yMap) {
        return map.getMap().get(yMap).get(xMap);
    }

    public Map getMap() {
        return map;
    }

    public Pair<Entity, Entity> checkCollision(int x, int y, DIRECTION direction) {
        Entity object1, object2;
        switch (direction) {
            case LEFT:
                object1 = map.getCoordinate(x + FIX_WIDTH, y + FIX_HEIGHT);
                object2 = map.getCoordinate(x + FIX_WIDTH, y + Sprite.SCALED_SIZE - FIX_HEIGHT);
                break;
            case RIGHT:
                object1 = map.getCoordinate(x + 24 - FIX_WIDTH, y + FIX_HEIGHT);
                object2 = map.getCoordinate(x + 24 - FIX_WIDTH, y + Sprite.SCALED_SIZE - FIX_HEIGHT);
                break;
            case UP:
                object1 = map.getCoordinate(x + FIX_WIDTH, y + FIX_HEIGHT);
                object2 = map.getCoordinate(x + 24 - FIX_WIDTH, y + FIX_HEIGHT);
                break;
            case DOWN:
                object1 = map.getCoordinate(x + FIX_WIDTH, y + Sprite.SCALED_SIZE - FIX_HEIGHT);
                object2 = map.getCoordinate(x + 24 - FIX_WIDTH, y + Sprite.SCALED_SIZE - FIX_HEIGHT);
                break;
            default:
                object1 = map.getCoordinate(x, y);
                object2 = map.getCoordinate(x, y);
                break;
        }
        return new Pair<Entity, Entity>(object1, object2);
    }

    public boolean collide(Entity entity1, Entity entity2) {
        ArrayList<Pair<Integer, Integer>> vertices = new ArrayList<>();
        // if (entity1 instanceof Bomber) {
        vertices.add(new Pair<Integer, Integer>(entity2.getX() + FIX_WIDTH, entity2.getY() + FIX_HEIGHT));
        vertices.add(new Pair<Integer, Integer>(entity2.getX() + Sprite.SCALED_SIZE - FIX_WIDTH * 2, entity2.getY() + FIX_HEIGHT));
        vertices.add(new Pair<Integer, Integer>(entity2.getX() + FIX_WIDTH, entity2.getY() + Sprite.SCALED_SIZE - FIX_HEIGHT * 2));
        vertices.add(
                new Pair<Integer, Integer>(entity2.getX() + Sprite.SCALED_SIZE - FIX_WIDTH * 2, entity2.getY() + Sprite.SCALED_SIZE - FIX_HEIGHT * 2));
        vertices.add(
                new Pair<Integer, Integer>(entity2.getX() + Sprite.DEFAULT_SIZE, entity2.getY() + Sprite.DEFAULT_SIZE));
        
        // System.out.println(entity1);
        // System.out.println(entity2);
        
        // System.out.println("check 0 " + contain(entity1, vertices.get(0)));
        // System.out.println("check 1 " + contain(entity1, vertices.get(1)));
        // System.out.println("check 2 " + contain(entity1, vertices.get(2)));
        // System.out.println("check 3 " + contain(entity1, vertices.get(3)));

        
        return contain(entity1, vertices.get(0))
                || contain(entity1, vertices.get(1))
                || contain(entity1, vertices.get(2))
                || contain(entity1, vertices.get(3))
                || contain(entity1, vertices.get(4));
        // }

    }

    public boolean collide(int xBomber, int yBomber, Entity entity) {
        ArrayList<Pair<Integer, Integer>> vertices = new ArrayList<>();
        // if (entity1 instanceof Bomber) {
        vertices.add(new Pair<Integer, Integer>(xBomber, yBomber));
        vertices.add(new Pair<Integer, Integer>(xBomber + 24, yBomber));
        vertices.add(new Pair<Integer, Integer>(xBomber, yBomber + Sprite.SCALED_SIZE));
        vertices.add(
                new Pair<Integer, Integer>(xBomber + 24, yBomber + Sprite.SCALED_SIZE));

        return contain(entity, vertices.get(0))
                || contain(entity, vertices.get(1))
                || contain(entity, vertices.get(2))
                || contain(entity, vertices.get(3));
    }

    private boolean contain(Entity entity2, Pair<Integer, Integer> point) {
        return (entity2.getX() + FIX_WIDTH <= point.getKey() &&
                point.getKey() - FIX_WIDTH <= entity2.getX() + Sprite.SCALED_SIZE &&
                entity2.getY() <= point.getValue() - FIX_HEIGHT &&
                point.getValue() - FIX_HEIGHT <= entity2.getY() + Sprite.SCALED_SIZE);
    }
    // public boolean go(DIRECTION direction, int x, int y) {
    // switch (direction) {
    // case RIGHT:
    // // int temp = x % Sprite.SCALED_SIZE;
    // // Entity object = map.getCoordinate(x, y);

    // // if (x < object.getX() + Sprite.SCALED_SIZE &&
    // // x + Sprite.SCALED_SIZE > object.getX() &&
    // // y < object.getY() + Sprite.SCALED_SIZE &&
    // // Sprite.SCALED_SIZE + y > object.getY()) {
    // // return true;
    // // } else {
    // // return false;
    // // }

    // // if (map.getCoordinate(x + 1 + Sprite.SCALED_SIZE, y) instanceof Obstacle)
    // {
    // // return false;
    // // }
    // // if (x % Sprite.SCALED_SIZE > FIXED_POINT) {
    // // if (map.getCoordinate(x + 1 + Sprite.SCALED_SIZE, y - Sprite.SCALED_SIZE)
    // instanceof Obstacle) {
    // // return false;
    // // }
    // // }
    // //return true;

    // case LEFT:
    // //return true;

    // case UP:
    // //return true;

    // case DOWN:
    // //return true;

    // default:
    // throw new ArithmeticException("WRONG DRIECTION in [CollisionManager]");

    // }
    // }

    public List<Bomb> getBombList() {
        List<Bomb> result = new ArrayList<>();
        // for (int i = 0; i < map.getNumberBomber(); i++) {
            for (Bomb bomb : ((Bomber)map.getBomberman()).getBombManager().getBombs()) {
                result.add(bomb);
            }
        // }
        return result;
    }
}