package uet.oop.bomberman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uet.oop.bomberman.controller.GameMenu;
import uet.oop.bomberman.controller.KeyListener;
import uet.oop.bomberman.controller.Timer;
import uet.oop.bomberman.graphics.Graphics;
import uet.oop.bomberman.graphics.Sprite;



public class BombermanGame extends Application {
    
    private Graphics graphics;
    private Canvas canvas;
    private Timer timer;
    public static GameMenu menu;
    public static Map map;
    private KeyListener keyListener;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Graphics.WIDTH, Sprite.SCALED_SIZE * Graphics.HEIGHT);
        graphics = new Graphics(canvas);
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        
        // Tao scene
        Scene scene = new Scene(root);
        keyListener = new KeyListener(scene);
        menu = new GameMenu(keyListener);
        timer = new Timer(this);
        
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                SocketGame.socket.close();
                System.exit(0);
            }
        });
    }
    
    public void loop() {
        update();
        render();
    }

    public void update() {
        switch (menu.getGameState()) {
        case IN_MENU:
            menu.update();
            break;

        case IN_MULTIPLAYER_MENU:
            menu.update();
            break;

        case IN_SURVIVAL_MENU:
            menu.update();
            break;

        case IN_SINGLE_GAME:
            map.update();
            break;

        case IN_MULTIPLAYER_GAME:
            map.update();
            break;
        
        case IN_SURVIVAL_GAME:
            map.update();
            break;
        
        case IN_PAUSE:
            break;

        case END:
            System.exit(0);
            break;
        default:
            throw new IllegalArgumentException("Invalid game state");
        }
    }

    public void render() {
        switch (menu.getGameState()) {
        case IN_MENU:
            graphics.renderMenu(menu);
            break;
        
        case IN_MULTIPLAYER_MENU:
            graphics.renderMenu(menu);
            break;
        
        case IN_SURVIVAL_MENU:
            graphics.renderMenu(menu);
            break;

        case IN_SINGLE_GAME:
            graphics.clearScreen(canvas);
            graphics.renderMap(map);
            break;

        case IN_MULTIPLAYER_GAME:
            graphics.clearScreen(canvas);
            graphics.renderMap(map);
            break;
        
        case IN_SURVIVAL_GAME:
            graphics.clearScreen(canvas);
            graphics.renderMap(map);
            break;

        case IN_PAUSE:
            break;

        case END:
            break;
        default:
            throw new IllegalArgumentException("Invalid game state");
        }
    }

    
}
