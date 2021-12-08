package uet.oop.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

import javafx.print.PageOrientation;
import javafx.util.Pair;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.entities.BalloomEnemy;
import uet.oop.bomberman.entities.BombItem;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.DollEnemy;
import uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.FlameItem;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.OnealEnemy;
import uet.oop.bomberman.entities.SpeedItem;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Map {

    protected List<List<Entity>> map = new ArrayList<>();
    protected List<Entity> flexEntities = new ArrayList<>(); // 4 first elements is for bomber
    protected List< Pair<Integer, Integer> > brickList = new ArrayList<>();
    protected int[][] itemList = new int[13][31];
    protected Camera camera;
    protected List< Pair<Integer, Integer> > coordinateBomberman = new ArrayList<>();
    protected int mapLevel;
    protected int currentBomber = 0;
    protected int numberBomber = 1;
    public static final int MAX_NUMBER_BOMBERS = 4;
    public static int randomStart = 9;
    
    public Map(int level, KeyListener keyListener) {
        mapLevel = level;
        randomStart = (int)(Math.random() * 1000);
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        File file = new File(currentWorkingDir.normalize().toString() + "/res/levels/Level" + level +".txt");
        try {
            Scanner scanner = new Scanner(file);
            int height = scanner.nextInt();
            height = scanner.nextInt();
            int width = scanner.nextInt();
            scanner.nextLine();
            for(int i = 0; i < MAX_NUMBER_BOMBERS; ++i){
                Bomber temp = new Bomber(1, 1, Sprite.player_right.getFxImage(), keyListener, new CollisionManager(this));
                temp.setCurNumberInMap(i);
                flexEntities.add(temp);
            }
            for (int i = 0; i < height; i++) {
                String tempString = scanner.nextLine();
                List<Entity> tempList = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    switch (tempString.charAt(j)) {
                        case '#':
                        tempList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        break;
                        case '*':
                        tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        Pair<Integer, Integer> e = new Pair<Integer, Integer> (i,j);
                        brickList.add(e);
                        break;
                        // case 'x':
                        //     tempList.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        default:
                        tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    }
                    if(tempString.charAt(j) == '1'){
                        flexEntities.add(new BalloomEnemy(j, i, Sprite.balloom_left1.getFxImage(), new CollisionManager(this)));
                    }
                    if(tempString.charAt(j) == '2'){
                        flexEntities.add(new OnealEnemy(j, i, Sprite.oneal_left1.getFxImage(), new CollisionManager(this)));
                    }
                    if(tempString.charAt(j) == '3'){
                        flexEntities.add(new DollEnemy(j, i, Sprite.doll_left1.getFxImage(), new CollisionManager(this)));
                    }
                    if(tempString.charAt(j) == 'p'){
                        coordinateBomberman.add(new Pair<>(j, i));
                    }
                } 
                map.add(tempList);
            }
            setupBomberman(keyListener);
            randomItem();
            camera = new Camera(0, 0, width);
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void randomItem() {
        int n = brickList.size();
        Random random = new Random();
        Pair<Integer, Integer> x = brickList.get((random.nextInt(n/3)));
        System.out.print(x.getKey() + " ");
        System.out.println(x.getValue());
        itemList[x.getKey()][x.getValue()] = SpeedItem.code;
        x = brickList.get((random.nextInt(n/3) + n/3));
        System.out.print(x.getKey() + " ");
        System.out.println(x.getValue());
        itemList[x.getKey()][x.getValue()] = FlameItem.code;
        x = brickList.get((random.nextInt(n/3) + 2 * n / 3));
        System.out.print(x.getKey() + " ");
        System.out.println(x.getValue());
        itemList[x.getKey()][x.getValue()] = BombItem.code;
    }

    public void setupBomberman(KeyListener keyListener){
        for(int i = 0; i < coordinateBomberman.size(); ++i){
            Pair<Integer, Integer> coordinate = coordinateBomberman.get(i);
            flexEntities.get(i).setXUnit(coordinate.getKey());
            flexEntities.get(i).setYUnit(coordinate.getValue());
        }
    }

    public void update() {
        flexEntities.get(currentBomber).update();
        for (int i = 0; i < flexEntities.size(); ++i) {
            if(flexEntities.get(i) instanceof Bomber) continue;
            if(flexEntities.get(i) instanceof BalloomEnemy || flexEntities.get(i) instanceof DollEnemy) flexEntities.get(i).update();
            if(flexEntities.get(i) instanceof OnealEnemy) flexEntities.get(i).update(map, flexEntities.get(currentBomber).getModX(), flexEntities.get(currentBomber).getModY());
        }
        camera.update(flexEntities.get(currentBomber));
    }

    /**
     * Replace an entity to x, y coordinate in map.
     * @param x x coordinate in map.
     * @param y y coordinate in map.
     * @param entity entity to replace.
     */
    public void replace(int x, int y, Entity entity) {
        map.get(y).set(x, entity);
    }

    /**
     * getter for map.
     */
    public List<List<Entity>> getMap() {
        return map;
    }

    /**
     * getter for bomberman.
     */
    public Entity getBomberman() {
        return flexEntities.get(currentBomber);
    }
    public List<Bomber> getBombermans(){
        List<Bomber> bombermans = new ArrayList<>();
        for(int i = 0; i < MAX_NUMBER_BOMBERS; ++i) bombermans.add((Bomber)flexEntities.get(i));
        return bombermans;
    }
    public List<Enemy> getEnemy() {
        List<Enemy> enemies = new ArrayList<>();
        for(int i = 0; i < flexEntities.size(); ++i){
            if(flexEntities.get(i) instanceof Enemy) enemies.add((Enemy)flexEntities.get(i));
        }
        return enemies;
    }
    public int getLevel(){
        return mapLevel;
    }
    /**
     * Getter for camera.
     */
    public Camera getCamera() {
        return camera;
    }

    public List<Entity> getFlexEntities(){
        return flexEntities;
    }

    public Entity getCoordinate(int x, int y) {
        /*
        x -= x % Sprite.SCALED_SIZE;
        y -= y % Sprite.SCALED_SIZE;
        //System.out.println("\tx: " + x);
        //System.out.println("\ty: " + y);
        int modX = Math.round(x / Sprite.SCALED_SIZE);
        int modY = Math.round(y / Sprite.SCALED_SIZE);
        System.out.println("\tmodX = " + modX);
        System.out.println("\tmodY = " + modY);
        */
        int modX = Math.round(x / Sprite.SCALED_SIZE);
        int modY = Math.round(y / Sprite.SCALED_SIZE);
        return map.get(modY).get(modX);
        
    }

    public void setCurrentBomber(int temp){
        currentBomber = temp;
    }
    public int getCurrentBomber(){
        return currentBomber;
    }
    public void setNumberBomber(int temp){
        numberBomber = temp;
    }
    public int getNumberBomber(){
        return numberBomber;
    }
    public int getItem(int x, int y) {
        return itemList[y][x];
    }
}
