package uet.oop.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.util.Pair;
import uet.oop.bomberman.controller.Camera;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.entities.BalloomEnemy;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.DollEnemy;
import uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.NightmareEnemy;
import uet.oop.bomberman.entities.OnealEnemy;
import uet.oop.bomberman.entities.Portal;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Map {

    protected List<List<Entity>> map = new ArrayList<>();
    protected List<Entity> flexEntities = new ArrayList<>(); // 4 first elements is for bomber
    protected Camera camera;
    protected List< Pair<Integer, Integer> > coordinateBomberman = new ArrayList<>();
    protected int height;
    protected int width;
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
            height = scanner.nextInt();
            height = scanner.nextInt();
            width = scanner.nextInt();
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
                            break;
                        case '1':
                            Enemy temp = new BalloomEnemy(j, i, Sprite.balloom_left1.getFxImage(), new CollisionManager(this));
                            flexEntities.add(temp);
                            temp.setIndexOfFlex(flexEntities.size() - 1);
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case '2':
                            temp = new OnealEnemy(j, i, Sprite.oneal_left1.getFxImage(), new CollisionManager(this));
                            flexEntities.add(temp);
                            temp.setIndexOfFlex(flexEntities.size() - 1);
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case '3':
                            temp = new DollEnemy(j, i, Sprite.doll_left1.getFxImage(), new CollisionManager(this));
                            flexEntities.add(temp);
                            temp.setIndexOfFlex(flexEntities.size() - 1);
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case '4':
                            temp = new NightmareEnemy(j, i, Sprite.doll_left1.getFxImage(), new CollisionManager(this));
                            flexEntities.add(temp);
                            temp.setIndexOfFlex(flexEntities.size() - 1);
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case 'p':
                            coordinateBomberman.add(new Pair<>(j, i));
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case 'x':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            flexEntities.add(new Portal(j, i, Sprite.portal.getFxImage()));
                            break;
                        case 'b':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            //flexEntities.add(new BombItem(j, i, Sprite.item.getFxImage()));
                            break;
                        case 'f':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            //flexEntities.add(new BombItem(j, i, Sprite.item.getFxImage()));
                            break;
                        case 's':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            //flexEntities.add(new BombItem(j, i, Sprite.item.getFxImage()));
                            break;
                        default:
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                    }
                } 
                map.add(tempList);
            }
            setupBomberman(keyListener);
            camera = new Camera(0, 0, width);
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
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
            if(flexEntities.get(i) instanceof Bomber){
                Bomber bomber = (Bomber)flexEntities.get(i);
                bomber.getBombManager().update();
            }
            if(flexEntities.get(i) instanceof BalloomEnemy || flexEntities.get(i) instanceof DollEnemy) flexEntities.get(i).update();
            if(flexEntities.get(i) instanceof OnealEnemy || flexEntities.get(i) instanceof NightmareEnemy) flexEntities.get(i).update(map, getBombermans());
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
        for(int i = 0; i < numberBomber; ++i) bombermans.add((Bomber)flexEntities.get(i));
        return bombermans;
    }
    public List<Bomber> getAllBombermans(){
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

    public void sendItemRand(){
        if(currentBomber != 0) return;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(map.get(i).get(j) instanceof Brick){
                    int rand = (int)(Math.random() * 6);
                    if(rand >= 5) continue;
                    String msg = "0,Item," + rand + "," + j + "," + i;
                    byte[] data = msg.getBytes();
                    DatagramPacket outPacket = new DatagramPacket(data, data.length, SocketGame.address, SocketGame.PORT);
                    try {
                        SocketGame.socket.send(outPacket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
