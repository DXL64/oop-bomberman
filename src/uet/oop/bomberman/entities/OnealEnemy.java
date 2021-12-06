package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import uet.oop.bomberman.controller.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.graphics.Sprite;

public class OnealEnemy extends Enemy {

    public OnealEnemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager){
        super(xUnit, yUnit, img, collisionManager);
        this.speed = 2;
    }
    public void update(List<List<Entity>> map, int xModBomber, int yModBomber){
        ++count;
        if(count % 2 == 0) return;
        if(count % 32 == 1){
            List<List<Integer>> data = formatData(map, xModBomber, yModBomber);
            direction = getDirectFromAStar(data, map.size(), map.get(0).size(), yModBomber, xModBomber);
            if(direction == notGo){
                sizeCheckCollision = Sprite.SCALED_SIZE;
                goRand();
                return;
            }
        }
        sizeCheckCollision = speed;
        if(direction == moveLeft) goLeft();
        else if(direction == moveRight) goRight();
        else if(direction == moveDown) goDown();
        else if(direction == moveUp) goUp();
        return;
    
    }
    private int getDirectFromAStar(List<List<Integer>> data, int height, int width, int yModBomber, int xModBomber){
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
    private List<List<Integer>> formatData(List<List<Entity>> map, int xModBomber, int yModBomber){
        List<List<Integer>> formatMap = new ArrayList<>();
        int height = map.size();
        int width = map.get(0).size();
        for (int i = 0; i < height ; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if(map.get(i).get(j) instanceof Wall || map.get(i).get(j) instanceof Brick) row.add(OBSTACLE);
                else row.add(GRASS);
            } 
            formatMap.add(row);
        }
        formatMap.get(yModBomber).set(xModBomber, BOMBERMAN);
        formatMap.get(getModY()).set(getModX(), ENEMY);
        return formatMap;
    }
    public Image chooseSprite() {
        if(direction == moveLeft || direction == moveUp){
            switch(spriteImage / 3){
                case 0: return Sprite.oneal_left1.getFxImage();
                case 1: return Sprite.oneal_left2.getFxImage();
                case 2: return Sprite.oneal_left3.getFxImage();
            }
        }
        else if(direction == moveRight || direction == moveDown){
            switch(spriteImage / 3){
                case 0: return Sprite.oneal_right1.getFxImage();
                case 1: return Sprite.oneal_right2.getFxImage();
                case 2: return Sprite.oneal_right3.getFxImage();
            }
        }
        return null;
    }
    @Override
    public void render(GraphicsContext gc, Camera camera) {
        Image img = chooseSprite();
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }
}