package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Spinner;
import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.Timer;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.entities.Explosion.EXPLOSION_STATE;
import uet.oop.bomberman.graphics.Sprite;

public class BombManager {
    private List<Bomb> bombs = new ArrayList<>();
    private List<Explosion> explosions = new ArrayList<>();
    private Map map;
    private CollisionManager collisionManager;
    private int numberOfBombs = 1;
    private long delayBombSet;
    private int flame = 1;

    public BombManager(CollisionManager collisionManager) {
        this.map = collisionManager.getMap();
        this.collisionManager = collisionManager;
        delayBombSet = Timer.now();
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public void update() {
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
            if (bombs.get(i).explode()) {
                bombExplode(i);
            }
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).isExploded()) {
                explosions.remove(i);
                i--;
            }
        }
    }

    public void bombExplode(int iBomb) {
        Bomb bomb = bombs.get(iBomb);
        int x = bomb.x / Sprite.SCALED_SIZE;
        int y = bomb.y / Sprite.SCALED_SIZE;
        explosions.add(new Explosion(x, y, Sprite.bomb_exploded2.getFxImage(), DIRECTION.CENTER, EXPLOSION_STATE.MIDDLE));
        Stack<Pair<Integer, Integer>> stack = new Stack<>();

        // Destroy the down side
        for (int i = 1; i <= flame; i++) {
            if (!(map.getMap().get(y + i).get(x) instanceof Obstacle)) {
                stack.push(new Pair<Integer, Integer>(x, y + i));
            } else {
                if (map.getMap().get(y + i).get(x) instanceof Brick) {
                    explosions.add(new Explosion(x, y + i, Sprite.brick.brick_exploded2.getFxImage(), DIRECTION.DOWN, EXPLOSION_STATE.BRICK));
                    if(map.getItem(x, y + i) == SpeedItem.code)
                        map.replace(x, y + i, new SpeedItem(x, y + i, Sprite.powerup_speed.getFxImage()));
                    else if(map.getItem(x, y + i) == FlameItem.code)
                        map.replace(x, y + i, new FlameItem(x, y + i, Sprite.powerup_flames.getFxImage()));
                    else if(map.getItem(x, y + i) == BombItem.code)
                        map.replace(x, y + i, new BombItem(x, y + i, Sprite.powerup_bombs.getFxImage()));
                    else
                        map.replace(x, y + i, new Grass(x, y + i, Sprite.grass.getFxImage()));
                    System.out.println(Sprite.grass.getFxImage());
                }
                break;
            }
        }
        if (!stack.empty()) {
            explosions.add(new Explosion(stack.peek().getKey(), stack.peek().getValue(), Sprite.explosion_vertical_down_last2.getFxImage(), DIRECTION.DOWN, EXPLOSION_STATE.END));
            stack.pop();
        }
        while (!stack.empty()) {
            explosions.add(new Explosion(stack.peek().getKey(), stack.peek().getValue(), Sprite.explosion_vertical_down_last2.getFxImage(), DIRECTION.DOWN, EXPLOSION_STATE.MIDDLE));
            stack.pop();
        }

        // Destroy the left side
        for (int i = 1; i <= flame; i++) {
            if (!(map.getMap().get(y).get(x - i) instanceof Obstacle)) {
                stack.push(new Pair<Integer, Integer>(x - i, y));
            } else {
                if (map.getMap().get(y).get(x - i) instanceof Brick) {
                    explosions.add(new Explosion(x - i, y, Sprite.brick.brick_exploded2.getFxImage(), DIRECTION.DOWN, EXPLOSION_STATE.BRICK));
                    if(map.getItem(x - i, y) == SpeedItem.code)
                        map.replace(x - i, y, new SpeedItem(x - i, y, Sprite.powerup_speed.getFxImage()));
                    else if(map.getItem(x - i, y) == FlameItem.code)
                        map.replace(x - i, y, new FlameItem(x - i, y, Sprite.powerup_flames.getFxImage()));
                    else if(map.getItem(x - i, y) == BombItem.code)
                        map.replace(x - i, y, new BombItem(x - i, y, Sprite.powerup_bombs.getFxImage()));
                    else
                        map.replace(x - i, y, new Grass(x - i, y, Sprite.grass.getFxImage()));
                }
                break;
            }
        }
        if (!stack.empty()) {
            explosions.add(new Explosion(stack.peek().getKey(), stack.peek().getValue(), Sprite.explosion_vertical_down_last2.getFxImage(), DIRECTION.LEFT, EXPLOSION_STATE.END));
            stack.pop();
        }
        while (!stack.empty()) {
            explosions.add(new Explosion(stack.peek().getKey(), stack.peek().getValue(), Sprite.explosion_vertical_down_last2.getFxImage(), DIRECTION.LEFT, EXPLOSION_STATE.MIDDLE));
            stack.pop();
        }

        // Destroy the right side
        for (int i = 1; i <= flame; i++) {
            if (!(map.getMap().get(y).get(x + i) instanceof Obstacle)) {
                stack.push(new Pair<Integer, Integer>(x + i, y));
            } else {
                if (map.getMap().get(y).get(x + i) instanceof Brick) {
                    explosions.add(new Explosion(x + i, y, Sprite.brick.brick_exploded2.getFxImage(), DIRECTION.DOWN, EXPLOSION_STATE.BRICK));
                    if(map.getItem(x + i, y) == SpeedItem.code)
                        map.replace(x + i, y, new SpeedItem(x + i, y, Sprite.powerup_speed.getFxImage()));
                    else if(map.getItem(x + i, y) == FlameItem.code)
                        map.replace(x + i, y, new FlameItem(x + i, y, Sprite.powerup_flames.getFxImage()));
                    else if(map.getItem(x + i, y) == BombItem.code)
                        map.replace(x + i, y, new BombItem(x + i, y, Sprite.powerup_bombs.getFxImage()));
                    else
                        map.replace(x + i, y, new Grass(x + i, y, Sprite.grass.getFxImage()));
                }
                break;
            }
        }
        if (!stack.empty()) {
            explosions.add(new Explosion(stack.peek().getKey(), stack.peek().getValue(), Sprite.explosion_vertical_down_last2.getFxImage(), DIRECTION.RIGHT, EXPLOSION_STATE.END));
            stack.pop();
        }
        while (!stack.empty()) {
            explosions.add(new Explosion(stack.peek().getKey(), stack.peek().getValue(), Sprite.explosion_vertical_down_last2.getFxImage(), DIRECTION.RIGHT, EXPLOSION_STATE.MIDDLE));
            stack.pop();
        }

        //Destroy the up side 
        for (int i = 1; i <= flame; i++) {
            if (!(map.getMap().get(y - i).get(x) instanceof Obstacle)) {
                stack.push(new Pair<Integer, Integer>(x, y - i));
            } else {
                if (map.getMap().get(y - i).get(x) instanceof Brick) {
                    explosions.add(new Explosion(x, y - i, Sprite.brick.brick_exploded2.getFxImage(), DIRECTION.DOWN, EXPLOSION_STATE.BRICK));
                    if(map.getItem(x, y - i) == SpeedItem.code)
                        map.replace(x, y - i, new SpeedItem(x, y - i, Sprite.powerup_speed.getFxImage()));
                    else if(map.getItem(x, y - i) == FlameItem.code)
                        map.replace(x, y - i, new FlameItem(x, y - i, Sprite.powerup_flames.getFxImage()));
                    else if(map.getItem(x, y - i) == BombItem.code)
                        map.replace(x, y - i, new BombItem(x, y - i, Sprite.powerup_bombs.getFxImage()));
                    else
                        map.replace(x, y - i, new Grass(x, y - i, Sprite.grass.getFxImage()));
                }
                break;
            }
        }
        if (!stack.empty()) {
            explosions.add(new Explosion(stack.peek().getKey(), stack.peek().getValue(), Sprite.explosion_vertical_down_last2.getFxImage(), DIRECTION.UP, EXPLOSION_STATE.END));
            stack.pop();
        }
        while (!stack.empty()) {
            explosions.add(new Explosion(stack.peek().getKey(), stack.peek().getValue(), Sprite.explosion_vertical_down_last2.getFxImage(), DIRECTION.UP, EXPLOSION_STATE.MIDDLE));
            stack.pop();
        }
        
        bombs.remove(iBomb);
    }

    public boolean canSetBomb(int xBomb, int yBomb) {
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

    public int bombSize() {
        return bombs.size();
    }

    public void renderBombs(GraphicsContext gc, Camera camera) {
        for (Bomb bomb : bombs) {
            bomb.render(gc, camera);
        }
        for (Explosion explosion : explosions) {
            explosion.render(gc, camera);
        }
    }
    public int getFlame() {
        return flame;
    }

    public void setFlame(int flame) {
        this.flame = flame;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }
}
