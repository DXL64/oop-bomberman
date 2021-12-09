package uet.oop.bomberman.graphics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controller.Button;
import uet.oop.bomberman.controller.GameMenu;
import javafx.scene.image.*;


public class Graphics {
    public static final int WIDTH = 22;
    public static final int HEIGHT = 11;

    public static int X_COORDINATE = 0;
    public static int Y_COORDINATE = 1;
    public static Font TITLEFONT;
    public static Font DEFAULTFONT;
    public static Font CHOOSENFONT;
    public static Font FUTUREFONT;
    public static Font FUTUREFONTTHIN;
	public static Image backGroundMenu;
    public static Image emptySlot[] = new Image[Map.MAX_NUMBER_BOMBERS];
    public static Image readySlot[] = new Image[Map.MAX_NUMBER_BOMBERS];
    public static Image notReadySlot[] = new Image[Map.MAX_NUMBER_BOMBERS];
    public static Image winner[] = new Image[Map.MAX_NUMBER_BOMBERS];
    public static Image loser[] = new Image[Map.MAX_NUMBER_BOMBERS];
    public static int slotCoordinates[][] = new int[Map.MAX_NUMBER_BOMBERS][2];

    private GraphicsContext gc;

    public Graphics(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        try {
            DEFAULTFONT = Font.loadFont(Files.newInputStream(Paths.get("res/font/default.ttf")), 30);
            CHOOSENFONT = Font.loadFont(Files.newInputStream(Paths.get("res/font/title.ttf")), 25);
            FUTUREFONT = Font.loadFont(Files.newInputStream(Paths.get("res/font/Future Techno Italic 400.ttf")), 25);
            FUTUREFONTTHIN = Font.loadFont(Files.newInputStream(Paths.get("res/font/Future Techno Italic 400.ttf")), 15);
            backGroundMenu = new Image(Files.newInputStream(Paths.get("res/lobby/BG.png")));
            int x = 115;
            int y = 160;
            for (int i = 0; i < 4; i++) {
                emptySlot[i] = new Image(Files.newInputStream(Paths.get("res/lobby/" + (i + 1) + "Empty.png")));
                readySlot[i] = new Image(Files.newInputStream(Paths.get("res/lobby/" + (i + 1) + "Ready.png")));
                notReadySlot[i] = new Image(Files.newInputStream(Paths.get("res/lobby/" + (i + 1) + "nReady.png")));
                winner[i] = new Image(Files.newInputStream(Paths.get("res/EndState/" + (i + 1) + "win.png")));
                loser[i] = new Image(Files.newInputStream(Paths.get("res/EndState/" + (i + 1) + "lose.png")));
                slotCoordinates[i][X_COORDINATE] = x;
                slotCoordinates[i][Y_COORDINATE] = y;
                x += 135;
            }

        } catch (IOException e) {
            System.out.println("[IOException] Wrong filepaths.");
        }
    }

    public void clearScreen(Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void renderMap(Map map) {
        for (int i = 0; i < map.getMap().size(); i++) {
            map.getMap().get(i).forEach(g -> g.render(gc,map.getCamera()));    
        }
        for(int i = 0; i < map.getFlexEntities().size(); ++i){
            if(i < map.getNumberBomber() || i >= map.MAX_NUMBER_BOMBERS) 
                map.getFlexEntities().get(i).render(gc, map.getCamera());
        }
    }
    public void renderText(Font font, Text text, int x, int y) {
        gc.setFont(font);
        gc.setFill(text.getFill());
        gc.fillText(text.getText(), x, y);
    }

    public void renderMenu(GameMenu menu) {
        
        gc.drawImage(backGroundMenu, 0, 0, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        menu.render(gc);
    }

    public void renderButton(Button button) {
        renderText(button.getFont(), button.getText(), button.getX(), button.getY());
    }
}
