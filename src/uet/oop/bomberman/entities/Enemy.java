package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy extends AnimationEntity implements Destroyable {
    protected CollisionManager collisionManager;

    public final static int notGo = 0;
    public final static int moveLeft = -1;
    public final static int moveRight = 1;
    public final static int moveUp = -2;
    public final static int moveDown = 2;
    protected int count = 0;
    protected int spriteImage = 0;
    protected int direction = moveLeft;
    protected int sizeCheckCollision;
    protected Boolean isCheckCollision = true;

    public final static int OBSTACLE = 0;
    public final static int GRASS = 1;
    public final static int BOMBERMAN = 2;
    public final static int ENEMY = 3;
    public final static int[] randomGo = {1, 3, 2, 0, 1, 0, 2, 2, 2, 0, 1, 1, 1, 3, 1, 3, 3, 2, 3, 0, 3, 0, 2, 1, 0, 2, 1, 0, 2, 3, 3, 2, 3, 2, 1, 0, 3, 3, 3, 2, 3, 3, 2, 1, 1, 0, 1, 3, 1, 0, 3, 0, 2, 1, 1, 3, 3, 1, 1, 2, 0, 3, 2, 2, 0, 2, 0, 2, 0, 0, 2, 1, 2, 1, 2, 2, 2, 1, 1, 0, 1, 3, 0, 2, 0, 0, 2, 0, 3, 0, 0, 3, 2, 3, 1, 2, 2, 2, 1, 1, 1, 3, 3, 2, 0, 2, 1, 2, 1, 2, 1, 1, 0, 0, 2, 1, 1, 1, 1, 0, 2, 2, 1, 0, 3, 3, 3, 2, 3, 0, 2, 2, 1, 0, 2, 2, 3, 3, 3, 1, 1, 3, 1, 3, 1, 1, 1, 2, 1, 0, 2, 2, 2, 2, 1, 0, 0, 3, 2, 3, 2, 3, 0, 1, 0, 3, 3, 1, 2, 2, 3, 0, 2, 0, 0, 1, 0, 3, 0, 0, 1, 2, 1, 3, 2, 2, 0, 0, 3, 3, 0, 1, 0, 3, 3, 1, 2, 1, 1, 2, 1, 0, 3, 3, 0, 1, 3, 0, 2, 3, 0, 0, 0, 1, 1, 2, 3, 1, 1, 3, 3, 3, 2, 3, 0, 0, 2, 2, 0, 0, 2, 1, 1, 2, 1, 1, 0, 2, 2, 0, 2, 3, 2, 1, 2, 3, 3, 0, 0, 3, 2, 2, 0, 3, 0, 2, 2, 2, 3, 1, 1, 2, 0, 0, 1, 2, 2, 3, 1, 3, 1, 0, 0, 1, 3, 0, 0, 2, 3, 1, 1, 0, 1, 1, 3, 3, 2, 1, 2, 1, 0, 2, 1, 1, 0, 0, 1, 2, 1, 1, 0, 1, 0, 0, 1, 3, 2, 1, 1, 0, 1, 0, 2, 1, 3, 3, 3, 0, 3, 1, 1, 1, 2, 1, 1, 3, 1, 0, 2, 1, 0, 2, 3, 2, 2, 1, 0, 3, 2, 3, 1, 3, 1, 1, 0, 0, 0, 1, 1, 0, 1, 2, 2, 2, 1, 0, 0, 2, 1, 1, 3, 2, 1, 2, 0, 3, 0, 3, 1, 0, 2, 2, 1, 1, 2, 1, 2, 2, 0, 2, 3, 1, 2, 3, 2, 1, 1, 2, 2, 0, 2, 2, 0, 3, 1, 2, 3, 1, 2, 1};

    public Enemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager) {
        super(xUnit, yUnit, img);
        this.collisionManager = collisionManager;
    }

    public void update() {
    }

    public void update(List<List<Entity>> map, int xBomber, int yBomber) {
    }


    public Boolean goLeft() {
        if(isCheckCollision == false){
            if(x - speed <= 0) return false;
            x -= speed;
            direction = moveLeft;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x - sizeCheckCollision, y, DIRECTION.LEFT);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x -= speed;
            direction = moveLeft;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        return false;
    }

    protected Boolean goRight() {
        if(isCheckCollision == false){
            if(x + speed >= (31 - 1) * Sprite.SCALED_SIZE) return false;
            x += speed;
            direction = moveRight;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x + sizeCheckCollision, y, DIRECTION.RIGHT);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            x += speed;
            direction = moveRight;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        return false;
    }

    protected Boolean goUp() {
        if(isCheckCollision == false){
            if(y - speed <= 0) return false;
            y -= speed;
            direction = moveUp;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y - sizeCheckCollision, DIRECTION.UP);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            y -= speed;
            direction = moveUp;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        return false;
    }

    protected Boolean goDown() {
        if(isCheckCollision == false){
            if(y + speed >= (13 - 1) * Sprite.SCALED_SIZE) return false;
            y += speed;
            direction = moveDown;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        Pair<Entity, Entity> tmp = collisionManager.checkCollision(x, y + sizeCheckCollision, DIRECTION.DOWN);
        if (!(tmp.getKey() instanceof Obstacle || tmp.getValue() instanceof Obstacle)) {
            y += speed;
            direction = moveDown;
            spriteImage = (spriteImage + 1) % 9;
            return true;
        }
        return false;
    }
    protected void goRand(){
        int rand = randomGo[((count + Map.randomStart) % randomGo.length)];
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

    protected int getDirectFromAStar(List<List<Integer>> data, int height, int width, int yModBomber, int xModBomber){
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
        if(dist.get(thisModY).get(thisModX) == 0 || dist.get(thisModY).get(thisModX) == Integer.MAX_VALUE / 2) return notGo;
        int nextStep = parent.get(thisModX * height + thisModY);
        int newX = (int)(nextStep/height);
        int newY = nextStep % height;
        if(newX - thisModX == 1) return moveRight;
        if(newX - thisModX == -1) return moveLeft;
        if(newY - thisModY == -1) return moveUp;
        if(newY - thisModY == 1) return moveDown;
        return moveLeft;
    }

    @Override
    public Image chooseSprite() {
        // TODO Auto-generated method stub
        return null;
    }
}
