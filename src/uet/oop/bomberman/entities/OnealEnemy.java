package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import uet.oop.bomberman.controller.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.graphics.Sprite;

public class OnealEnemy extends Enemy {

    public OnealEnemy(int xUnit, int yUnit, Image img, CollisionManager collisionManager) {
        super(xUnit, yUnit, img, collisionManager);
        this.speed = 2;
    }

    public void update(List<List<Entity>> map, List<Bomber> bombers) {
        ++count;
        if (death) return;
        if (count % 2 == 0)
            return;
        if (count % 32 == 1) {
            int indexNearest = getNearestBomber(bombers);
            int xModBomber = bombers.get(indexNearest).getModX();
            int yModBomber = bombers.get(indexNearest).getModY();
            List<List<Integer>> data = formatData(map, xModBomber, yModBomber, bombers);
            direction = getDirectFromAStar(data, map.size(), map.get(0).size(), yModBomber, xModBomber);
            if (direction == DIRECTION.NOTGO) {
                sizeCheckCollision = Sprite.SCALED_SIZE;
                goRand();
                return;
            }
        }
        sizeCheckCollision = speed;
        if (direction == DIRECTION.LEFT) goLeft();
        else if (direction == DIRECTION.RIGHT) goRight();
        else if (direction == DIRECTION.DOWN) goDown();
        else if (direction == DIRECTION.UP) goUp();
    }

    private int getNearestBomber(List<Bomber> bombers) {
        int index = 0;
        Double minManhattan = Math.sqrt((bombers.get(0).getX() - x) * (bombers.get(0).getX() - x)
                + (bombers.get(0).getY() - y) * (bombers.get(0).getY() - y));
        for (int i = 1; i < bombers.size(); ++i) {
            Double manhattan = Math.sqrt((bombers.get(i).getX() - x) * (bombers.get(i).getX() - x)
                    + (bombers.get(i).getY() - y) * (bombers.get(i).getY() - y));
            if (manhattan < minManhattan) {
                minManhattan = manhattan;
                index = i;
            }
        }
        return index;
    }

    protected List<List<Integer>> formatData(List<List<Entity>> map, int xModBomber, int yModBomber,
            List<Bomber> bombers) {
        List<List<Integer>> formatMap = new ArrayList<>();
        int height = map.size();
        int width = map.get(0).size();
        for (int i = 0; i < height; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if (map.get(i).get(j) instanceof Wall || map.get(i).get(j) instanceof Brick)
                    row.add(OBSTACLE);
                else
                    row.add(GRASS);
            }
            formatMap.add(row);
        }
        formatMap.get(yModBomber).set(xModBomber, BOMBERMAN);
        formatMap.get(getModY()).set(getModX(), ENEMY);
        return formatMap;
    }

    public Image chooseSprite() {
        if (death) {
            if (count < 30) {       
                return Sprite.oneal_dead.getFxImage();
            }
            return null;
        }
        spriteImage = count % 9;
        if (direction == DIRECTION.LEFT || direction == DIRECTION.UP) {
            switch (spriteImage / 3) {
                case 0:
                    return Sprite.oneal_left1.getFxImage();
                case 1:
                    return Sprite.oneal_left2.getFxImage();
                case 2:
                    return Sprite.oneal_left3.getFxImage();
            }
        }
        else if (direction == DIRECTION.RIGHT || direction == DIRECTION.DOWN) {
            switch (spriteImage / 3) {
                case 0:
                    return Sprite.oneal_right1.getFxImage();
                case 1:
                    return Sprite.oneal_right2.getFxImage();
                case 2:
                    return Sprite.oneal_right3.getFxImage();
            }
        }
        return null;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        img = chooseSprite();
        if (death && count >= 30) return;
        gc.drawImage(img, x - camera.getX(), y - camera.getY());
    }

    public void die() {
        if (!death) {
            death = true;
            count = 0;
        }
    }

    public String toString() {
        return "Oneal [" + super.toString() + "]";
    }
}