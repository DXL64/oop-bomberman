package uet.oop.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
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
import uet.oop.bomberman.entities.NightmareEnemy;
import uet.oop.bomberman.entities.OnealEnemy;
import uet.oop.bomberman.entities.Portal;
import uet.oop.bomberman.entities.SpeedItem;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Map {

    protected List<List<Entity>> map;
    protected List<Entity> flexEntities; // 4 first elements is for bomber
    protected int[][] itemList;
    protected Camera camera;
    protected List< Pair<Integer, Integer> > coordinateBomberman;
    protected int height;
    protected int width;
    protected int mapLevel;
    protected int currentBomber = 0;
    protected int numberBomber = 1;
    protected int numberEnemyLiving = 0;
    protected int numberBomberDie = 0;
    protected int numberPlayerGoPortal = 0;
    protected boolean isWin;
    protected int whoWin;
    public static final int MAX_NUMBER_BOMBERS = 4;

    public Map(int level, KeyListener keyListener) {
        Constructor(level, keyListener);
    }
    
    public void Constructor(int level, KeyListener keyListener){
        map = new ArrayList<>();
        flexEntities = new ArrayList<>();
        itemList = new int[31][31];
        mapLevel = level;
        coordinateBomberman = new ArrayList<>();
        numberEnemyLiving = 0;
        numberPlayerGoPortal = 0;
        
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        File file = new File(currentWorkingDir.normalize().toString() + "/res/levels/Level" + level + ".txt");
        try {
            Scanner scanner = new Scanner(file);
            height = scanner.nextInt();
            height = scanner.nextInt();
            width = scanner.nextInt();
            itemList = new int[height][width];
            scanner.nextLine();
            for (int i = 0; i < MAX_NUMBER_BOMBERS; ++i) {
                Bomber temp = new Bomber(1, 1, Sprite.player_right.getFxImage(), keyListener,
                        new CollisionManager(this));
                temp.setIndexOfFlex(i);
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
                            Enemy temp = new BalloomEnemy(j, i, Sprite.balloom_left1.getFxImage(),
                                    new CollisionManager(this));
                            flexEntities.add(temp);
                            temp.setIndexOfFlex(flexEntities.size() - 1);
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++numberEnemyLiving;
                            break;
                        case '2':
                            temp = new OnealEnemy(j, i, Sprite.oneal_left1.getFxImage(), new CollisionManager(this));
                            flexEntities.add(temp);
                            temp.setIndexOfFlex(flexEntities.size() - 1);
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++numberEnemyLiving;
                            break;
                        case '3':
                            temp = new DollEnemy(j, i, Sprite.doll_left1.getFxImage(), new CollisionManager(this));
                            flexEntities.add(temp);
                            temp.setIndexOfFlex(flexEntities.size() - 1);
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++numberEnemyLiving;
                            break;
                        case '4':
                            temp = new NightmareEnemy(j, i, Sprite.doll_left1.getFxImage(), new CollisionManager(this));
                            flexEntities.add(temp);
                            temp.setIndexOfFlex(flexEntities.size() - 1);
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++numberEnemyLiving;
                            break;
                        case 'p':
                            coordinateBomberman.add(new Pair<>(j, i));
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case 'x':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            itemList[i][j] = Portal.code;
                            break;
                        case 'b':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            itemList[i][j] = BombItem.code;
                            break;
                        case 'f':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            itemList[i][j] = FlameItem.code;
                            break;
                        case 's':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            itemList[i][j] = SpeedItem.code;
                            break;
                        default:
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                    }
                }
                map.add(tempList);
            }
            setupBomberman(keyListener);
            camera = new Camera(0, 0, width, height);
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void setupBomberman(KeyListener keyListener) {
        for (int i = 0; i < coordinateBomberman.size(); ++i) {
            Pair<Integer, Integer> coordinate = coordinateBomberman.get(i);
            flexEntities.get(i).setXUnit(coordinate.getKey());
            flexEntities.get(i).setYUnit(coordinate.getValue());
        }
    }

    public void update() {
        flexEntities.get(currentBomber).update();
        for (int i = 0; i < flexEntities.size(); ++i) {
            if (flexEntities.get(i) instanceof Bomber) {
                Bomber bomber = (Bomber) flexEntities.get(i);
                bomber.getBombManager().update();
                bomber.updateItems();
            }
            if (flexEntities.get(i) instanceof Enemy) {
                if (currentBomber != 0) {
                    flexEntities.get(i).updateCount();
                } else {
                    if (flexEntities.get(i) instanceof BalloomEnemy || flexEntities.get(i) instanceof DollEnemy)
                        flexEntities.get(i).update();
                    if (flexEntities.get(i) instanceof OnealEnemy || flexEntities.get(i) instanceof NightmareEnemy)
                        flexEntities.get(i).update(map, getBombermans());
                }
            }
        }
        camera.update(flexEntities.get(currentBomber));
    }

    /**
     * Replace an entity to x, y coordinate in map.
     * 
     * @param x      x coordinate in map.
     * @param y      y coordinate in map.
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

    public List<Bomber> getBombermans() {
        List<Bomber> bombermans = new ArrayList<>();
        for (int i = 0; i < numberBomber; ++i)
            bombermans.add((Bomber) flexEntities.get(i));
        return bombermans;
    }

    public List<Bomber> getAllBombermans() {
        List<Bomber> bombermans = new ArrayList<>();
        for (int i = 0; i < MAX_NUMBER_BOMBERS; ++i)
            bombermans.add((Bomber) flexEntities.get(i));
        return bombermans;
    }

    public List<Enemy> getEnemy() {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < flexEntities.size(); ++i) {
            if (flexEntities.get(i) instanceof Enemy)
                enemies.add((Enemy) flexEntities.get(i));
        }
        return enemies;
    }

    public int getLevel() {
        return mapLevel;
    }

    /**
     * Getter for camera.
     */
    public Camera getCamera() {
        return camera;
    }

    public List<Entity> getFlexEntities() {
        return flexEntities;
    }

    public Entity getCoordinate(int x, int y) {
        /*
         * x -= x % Sprite.SCALED_SIZE;
         * y -= y % Sprite.SCALED_SIZE;
         * //System.out.println("\tx: " + x);
         * //System.out.println("\ty: " + y);
         * int modX = Math.round(x / Sprite.SCALED_SIZE);
         * int modY = Math.round(y / Sprite.SCALED_SIZE);
         * System.out.println("\tmodX = " + modX);
         * System.out.println("\tmodY = " + modY);
         */
        int modX = Math.round(x / Sprite.SCALED_SIZE);
        int modY = Math.round(y / Sprite.SCALED_SIZE);
        return map.get(modY).get(modX);

    }

    public void setCurrentBomber(int temp) {
        currentBomber = temp;
    }

    public int getCurrentBomber() {
        return currentBomber;
    }

    public void setNumberBomber(int temp) {
        numberBomber = temp;
    }

    public int getNumberBomber() {
        return numberBomber;
    }

    public int getItem(int x, int y) {
        return itemList[y][x];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void sendItemRand() {
        if (currentBomber != 0)
            return;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map.get(i).get(j) instanceof Brick) {
                    int rand = (int) (Math.random() * 8) + 1;
                    if (rand > 3)
                        continue;
                    String msg = "0,Item," + rand + "," + j + "," + i;
                    byte[] data = msg.getBytes();
                    DatagramPacket outPacket = new DatagramPacket(data, data.length, SocketGame.address,
                            SocketGame.PORT);
                    try {
                        SocketGame.socket.send(outPacket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setItem(int y, int x, int type) {
        itemList[y][x] = type;
    }

    public void setNumberEnemyLiving(int number){
        numberEnemyLiving = number;
    }
    public int getNumberEnemyLiving(){
        return numberEnemyLiving;
    }
    public void setNumberPlayerGoPortal(int number){
        numberPlayerGoPortal = number;
    }
    public int getNumberPlayerGoPortal(){
        return numberPlayerGoPortal;
    }
    public void setNumberBomberDie(int number){
        numberBomberDie = number;
    }
    public int getNumberBomberDie(){
        return numberBomberDie;
    }
    public void setIsWin(boolean is){
        isWin = is;
    }
    public boolean getIsWin(){
        return isWin;
    }
    public void setWhoWin(int who){
        whoWin = who;
    }
    public int getWhoWin(){
        return whoWin;
    }
}
