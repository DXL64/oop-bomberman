package uet.oop.bomberman;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.Scene;
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
import uet.oop.bomberman.entities.OnealEnemy;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class MultiPlayerMap extends Map {
    SocketGame socketGame;

    public MultiPlayerMap(int level, KeyListener keyListener){
        super(level, keyListener);
        socketGame = new SocketGame(getBombermans(), this);
    }
}
