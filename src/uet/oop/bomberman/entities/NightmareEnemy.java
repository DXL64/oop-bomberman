package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

import uet.oop.bomberman.controller.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.graphics.Sprite;

public class NightmareEnemy extends Enemy {

    public NightmareEnemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager){
        super(xUnit, yUnit, img, collisionManager);
        this.speed = 2;
    }
    public void update(List<List<Entity>> map, List<Bomber> bombers){
        ++count;
        if(count % 2 == 0) return;
        if(count % 32 == 1){
            int indexNearest = getNearestBomber(bombers);
            int xModBomber = bombers.get(indexNearest).getModX();
            int yModBomber = bombers.get(indexNearest).getModY();
            List<List<Integer>> data = formatData(map, xModBomber, yModBomber, bombers);
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

    private int getNearestBomber(List<Bomber> bombers){
        int index = 0;
        Double minManhattan = Math.sqrt((bombers.get(0).getX() - x) * (bombers.get(0).getX() - x) + (bombers.get(0).getY() - y) * (bombers.get(0).getY() - y));
        for(int i = 1; i < bombers.size(); ++i){
            Double manhattan = Math.sqrt((bombers.get(i).getX() - x) * (bombers.get(i).getX() - x) + (bombers.get(i).getY() - y) * (bombers.get(i).getY() - y));
            if(manhattan < minManhattan){
                minManhattan = manhattan;
                index = i;
            }
        }
        return index;
    }

    protected List<List<Integer>> formatData(List<List<Entity>> map, int xModBomber, int yModBomber, List<Bomber> bombers){
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
        for(Bomber bomber : bombers){
            List<Bomb> bombs = bomber.getBombManager().getBombs();
            for(Bomb bomb : bombs){
                int j = bomb.getModX();
                int i = bomb.getModY();
                for(int k = 1; k <= bomb.getFlame(); ++k){
                    if(i + k < height) formatMap.get(i + k).set(j, OBSTACLE);
                    if(i - k >= 0) formatMap.get(i - k).set(j, OBSTACLE);
                    if(j + k < width) formatMap.get(i).set(j + k, OBSTACLE);
                    if(j - k >= 0) formatMap.get(i).set(j - k, OBSTACLE);
                }
            }
        } 
        formatMap.get(yModBomber).set(xModBomber, BOMBERMAN);
        formatMap.get(getModY()).set(getModX(), ENEMY);
        return formatMap;
    }
    public Image chooseSprite() {
        spriteImage = count % 9;
        if(direction == moveLeft || direction == moveUp){
            switch(spriteImage / 3){
                case 0: return Sprite.kondoria_left1.getFxImage();
                case 1: return Sprite.kondoria_left2.getFxImage();
                case 2: return Sprite.kondoria_left3.getFxImage();
            }
        }
        else if(direction == moveRight || direction == moveDown){
            switch(spriteImage / 3){
                case 0: return Sprite.kondoria_right1.getFxImage();
                case 1: return Sprite.kondoria_right2.getFxImage();
                case 2: return Sprite.kondoria_right3.getFxImage();
            }
        }
        return null;
    }
    @Override
    public void render(GraphicsContext gc, Camera camera) {
        Image img = chooseSprite();
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }

    public void die() {
        if (!death) {
            death = true;
            count = 0;
            countStep = 0;
        }
    }
}