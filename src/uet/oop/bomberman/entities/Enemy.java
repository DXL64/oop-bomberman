package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.text.StyledEditorKit.BoldAction;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy extends DestroyableEntity {
    protected CollisionManager collisionManager;

    public final static int notGo = 0;
    public final static int moveLeft = -1;
    public final static int moveRight = 1;
    public final static int moveUp = -2;
    public final static int moveDown = 2;
    protected int count = 0;
    protected int spriteImage = 0;
    protected int sizeCheckCollision;
    protected Boolean isCheckCollision = true;

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

    protected boolean checkCollide(int xPredict, int yPredict) {
        for (Bomb bomb : collisionManager.getBombList()) {
            if (collisionManager.collide(xPredict, yPredict, bomb)) {
                return true;
            }
        }
        return false;
    }

    public void update(List<List<Entity>> map, int xBomber, int yBomber) {
    }

    public Boolean goLeft() {
        if(isCheckCollision == false){
            if(x - speed <= 0) return false;
            super.update(DIRECTION.LEFT, true, indexOfFlex);
            return true;
        }
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - sizeCheckCollision, y, DIRECTION.LEFT);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            if (this instanceof BalloomEnemy) {
                if (checkCollide(x - sizeCheckCollision, y)) {
                    return false;
                }
            }
            super.update(DIRECTION.LEFT, true, indexOfFlex);
            return true;
        }
        return false;
    }

    protected Boolean goRight() {
        if(isCheckCollision == false){
            if(x + speed >= (BombermanGame.map.getWidth() - 1) * Sprite.SCALED_SIZE) return false;
            super.update(DIRECTION.RIGHT, true, indexOfFlex);
            return true;
        }
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + sizeCheckCollision, y, DIRECTION.RIGHT);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            if (this instanceof BalloomEnemy) {
                if (checkCollide(x + sizeCheckCollision, y)) {
                    return false;
                }
            }
            super.update(DIRECTION.RIGHT, true, indexOfFlex);
            return true;
        }
        return false;
    }

    protected Boolean goUp() {
        if(isCheckCollision == false){
            if(y - speed <= 0) return false;
            super.update(DIRECTION.UP, true, indexOfFlex);
            return true;
        }
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - sizeCheckCollision, DIRECTION.UP);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            if (this instanceof BalloomEnemy) {
                if (checkCollide(x, y - sizeCheckCollision)) {
                    return false;
                }
            }
            super.update(DIRECTION.UP, true, indexOfFlex);
            return true;
        }
        return false;
    }

    protected Boolean goDown() {
        if(isCheckCollision == false){
            if(y + speed >= (BombermanGame.map.getHeight() - 1) * Sprite.SCALED_SIZE) return false;
            super.update(DIRECTION.DOWN, true, indexOfFlex);
            return true;
        }
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + sizeCheckCollision, DIRECTION.DOWN);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            if (this instanceof BalloomEnemy) {
                if (checkCollide(x, y + sizeCheckCollision)) {
                    return false;
                }
            }
            super.update(DIRECTION.DOWN, true, indexOfFlex);
            return true;
        }
        return false;
    }
    protected void goRand(){
        int rand = (int)(Math.random() * 4);
        if(rand == 0){
            if(goLeft() == true) return;
            if(goUp() == true) return;
            if(goRight() == true) return;
            if(goDown() == true) return;
        }
        else if(rand == 1){
            if(goRight() == true) return;
            if(goDown() == true) return;
            if(goLeft() == true) return;
            if(goUp() == true) return;
        }
        else if(rand == 2){
            if(goUp() == true) return;
            if(goRight() == true) return;
            if(goDown() == true) return;
            if(goLeft() == true) return;
        }
        else if(rand == 3){
            if(goDown() == true) return;
            if(goLeft() == true) return;
            if(goUp() == true) return;
            if(goRight() == true) return;
        }
    }

    protected DIRECTION getDirectFromAStar(List<List<Integer>> data, int height, int width, int yModBomber, int xModBomber){
        int thisModX = getModX();
        int thisModY = getModY();
        List<List<Integer>> dist = new ArrayList<>();
        List<Integer> parent = new ArrayList<>();
        for(int i = 0; i < height; ++i){
            List<Integer> row = new ArrayList<>();
            for(int j = 0; j < width; ++j){
                row.add(Integer.MAX_VALUE / 2);
                parent.add(0);
            }
            dist.add(row);
        }
        PriorityQueue< Pair<Integer, Pair<Integer, Integer>> > pq = new PriorityQueue< Pair<Integer, Pair<Integer, Integer>> >((x, y) -> x.getKey() - y.getKey());

        pq.add(new Pair<>(Math.abs(xModBomber - thisModX) + Math.abs(yModBomber - thisModY), new Pair<>(yModBomber, xModBomber)));
        dist.get(yModBomber).set(xModBomber, 0);
        while(!pq.isEmpty()){
            Pair<Integer, Pair<Integer, Integer>> u = pq.poll();
            int x = u.getValue().getValue();
            int y = u.getValue().getKey();
            int fu = u.getKey();
            if(data.get(y).get(x) == ENEMY) break;
            if(x - 1 >= 0 && data.get(y).get(x - 1) != OBSTACLE){
                int hv = Math.abs(y - thisModY) + Math.abs(x - 1 - thisModX);
                int fv = dist.get(y).get(x - 1) + hv;
                if(fv > fu){
                    dist.get(y).set(x - 1, dist.get(y).get(x) + 1);
                    fv = dist.get(y).get(x - 1) + hv;
                    parent.set((x - 1) * height + y, x * height + y);
                    pq.add(new Pair<>(fv, new Pair<>(y, x - 1)));
                }
            }
            if(x + 1 <= width && data.get(y).get(x + 1) != OBSTACLE){
                int hv = Math.abs(y - thisModY) + Math.abs(x + 1 - thisModX);
                int fv = dist.get(y).get(x + 1) + hv;
                if(fv > fu){
                    dist.get(y).set(x + 1, dist.get(y).get(x) + 1);
                    fv = dist.get(y).get(x + 1) + hv;
                    parent.set((x + 1) * height + y, x * height + y);
                    pq.add(new Pair<>(fv, new Pair<>(y, x + 1)));
                }
            }
            if(y + 1 <= height && data.get(y + 1).get(x) != OBSTACLE){
                int hv = Math.abs(y + 1 - thisModY) + Math.abs(x - thisModX);
                int fv = dist.get(y + 1).get(x) + hv;
                if(fv > fu){
                    dist.get(y + 1).set(x,  dist.get(y).get(x) + 1);
                    fv = dist.get(y + 1).get(x) + hv;
                    parent.set(x * height + y + 1, x * height + y);
                    pq.add(new Pair<>(fv, new Pair<>(y + 1, x)));
                }
            }
            if(y - 1 >= 0 && data.get(y - 1).get(x) != OBSTACLE){
                int hv = Math.abs(y - 1 - thisModY) + Math.abs(x - thisModX);
                int fv = dist.get(y - 1).get(x) + hv;
                if(fv > fu){
                    dist.get(y - 1).set(x,  dist.get(y).get(x) + 1);
                    fv = dist.get(y - 1).get(x) + hv;
                    parent.set(x * height + y - 1, x * height + y);
                    pq.add(new Pair<>(fv, new Pair<>(y - 1, x)));
                }
            }          
        }
        //cant find way to ENEMY
        if(dist.get(thisModY).get(thisModX) == 0 || dist.get(thisModY).get(thisModX) == Integer.MAX_VALUE / 2) 
            return DIRECTION.NOTGO;
        int nextStep = parent.get(thisModX * height + thisModY);
        int newX = (int)(nextStep/height);
        int newY = nextStep % height;
        if(newX - thisModX == 1) return DIRECTION.RIGHT;
        if(newX - thisModX == -1) return DIRECTION.LEFT;
        if(newY - thisModY == -1) return DIRECTION.UP;
        if(newY - thisModY == 1) return DIRECTION.DOWN;
        return DIRECTION.NOTGO;
    }

    @Override
    public Image chooseSprite() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void die() {
        // TODO Auto-generated method stub

    }

    public void updateCount(){
        ++count;
    }
}
