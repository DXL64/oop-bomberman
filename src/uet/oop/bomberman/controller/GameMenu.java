package uet.oop.bomberman.controller;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.MultiPlayerMap;
import uet.oop.bomberman.SocketGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Graphics;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteBomber;

public class GameMenu {
    public static enum GAME_STATE {
        IN_MENU, IN_SINGLE_GAME, IN_MULTIPLAYER_GAME, IN_SURVIVAL_GAME, IN_MULTIPLAYER_MENU, IN_SURVIVAL_MENU, IN_PAUSE,
        END, IN_END_STATE;
    }

    private final int SINGLE_GAME = 0;
    private final int MULTI_GAME = 1;
    private final int SURVIVAL_MULTI_GAME = 2;
    private final int BOT_GAME = 3;
    private final int EXIT = 4;
    private long delayInput = 0;
    public static GAME_STATE preGameState;

    List<Button> button = new ArrayList<>();
    Button startButton;
    Button readyButton;
    Button pauseButton;
    Button gotoMenuButton;

    private int choosenButton;
    private boolean isReady = false;
    private int numberReady = 0;

    private KeyListener keyListener;
    public static GAME_STATE gameState;

    public GameMenu(KeyListener keyListener) {
        this.gameState = GAME_STATE.IN_MENU;
        this.keyListener = keyListener;

        Text text = new Text("SINGLE PLAY");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        button.add(new Button(Graphics.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth()
                / 2,
                Graphics.HEIGHT / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("MULTI PLAY");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        button.add(new Button(Graphics.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth()
                / 2,
                Graphics.HEIGHT / 8 * 7 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("SURVIVAL PLAY");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        button.add(new Button(Graphics.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth()
                / 2,
                Graphics.HEIGHT / 3 * 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("BOT PLAY");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        button.add(new Button(Graphics.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth()
                / 2,
                Graphics.HEIGHT / 4 * 3 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("EXIT");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        button.add(new Button(Graphics.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Graphics.HEIGHT / 8 * 10 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("START");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        startButton = new Button(Graphics.WIDTH / 7 * Sprite.SCALED_SIZE,
                Graphics.HEIGHT / 8 * 10 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text);

        text = new Text("READY");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        readyButton = new Button(Graphics.WIDTH / 7 * Sprite.SCALED_SIZE,
                Graphics.HEIGHT / 8 * 10 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text);

        text = new Text("CONTINUE GAME");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        pauseButton = new Button(Graphics.WIDTH / 7 * Sprite.SCALED_SIZE,
                Graphics.HEIGHT / 8 * 10 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text);

        text = new Text("GO TO MENU");
        text.setFont(Graphics.FUTUREFONT);
        text.setFill(Color.WHITE);
        gotoMenuButton = new Button(Graphics.WIDTH / 7 * Sprite.SCALED_SIZE,
                Graphics.HEIGHT / 8 * 10 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getHeight() / 2, text);

        choosenButton = SINGLE_GAME;
    }

    public void update() {
        switch (gameState) {
            case IN_MENU:
                long now = Timer.now();
                if (now - delayInput > Timer.TIME_FOR_SINGLE_INPUT) {
                    delayInput = now;
                    if (keyListener.isPressed(KeyCode.ENTER)) {
                        Sound.menuSelect.play();
                        switch (choosenButton) {
                            case SINGLE_GAME:
                                Sound.backgroundGame.stop();
                                Sound.menu.loop();
                                System.out.println("[ENTER SINGLE GAME]");
                                gameState = GAME_STATE.IN_SINGLE_GAME;
                                BombermanGame.map = new Map(1, keyListener);
                                break;
                            case MULTI_GAME:
                                System.out.println("[ENTER MULTIPLAYER MENU]");
                                gameState = GAME_STATE.IN_MULTIPLAYER_MENU;
                                BombermanGame.map = new MultiPlayerMap(1, keyListener);
                                break;
                            case SURVIVAL_MULTI_GAME:
                                System.out.println("[ENTER SURVIVAL MENU]");
                                gameState = GAME_STATE.IN_SURVIVAL_MENU;
                                BombermanGame.map = new MultiPlayerMap(6, keyListener);
                                break;
                            case EXIT:
                                System.out.println("[ENTER END STATE]");
                                gameState = GAME_STATE.END;
                                break;
                        }
                    } else if (keyListener.isPressed(KeyCode.S)) {
                        Sound.menuMove.play();
                        choosenButton++;
                        if (choosenButton == button.size()) {
                            choosenButton = 0;
                        }
                    } else if (keyListener.isPressed(KeyCode.W)) {
                        Sound.menuMove.play();
                        choosenButton--;
                        if (choosenButton < 0) {
                            choosenButton = button.size() - 1;
                        }
                    } else if (keyListener.isPressed(KeyCode.C)) {
                        choosenButton = SINGLE_GAME;
                    } else if (keyListener.isPressed(KeyCode.V)) {
                        choosenButton = MULTI_GAME;
                    } else if (keyListener.isPressed(KeyCode.B)) {
                        choosenButton = EXIT;
                    }
                }
                break;

            case IN_MULTIPLAYER_MENU:
            case IN_SURVIVAL_MENU:
                now = Timer.now();
                if (now - delayInput > Timer.TIME_FOR_SINGLE_INPUT) {
                    delayInput = now;
                    if (keyListener.isPressed(KeyCode.ENTER)) {
                        if (BombermanGame.map.getCurrentBomber() == 0) {
                            numberReady = 0;
                            for (Bomber bomber : BombermanGame.map.getBombermans()) {
                                if (bomber.getReady() == true)
                                    ++numberReady;
                            }
                            if (numberReady == BombermanGame.map.getNumberBomber() - 1 && numberReady > 0) {
                                String msg = "0,Start";
                                byte[] data = msg.getBytes();
                                DatagramPacket outPacket = new DatagramPacket(data, data.length, SocketGame.address,
                                        SocketGame.PORT);
                                try {
                                    SocketGame.socket.send(outPacket);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (gameState == GAME_STATE.IN_SURVIVAL_MENU
                                        || gameState == GAME_STATE.IN_SURVIVAL_GAME)
                                    BombermanGame.map.sendItemRand();
                            }
                        } else {
                            String msg = new String();
                            if (isReady == false) {
                                isReady = true;
                                msg = BombermanGame.map.getCurrentBomber() + ",Ready";
                            } else {
                                isReady = false;
                                msg = BombermanGame.map.getCurrentBomber() + ",NotReady";
                            }
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
                break;

            case IN_PAUSE:
                now = Timer.now();
                if (now - delayInput > Timer.TIME_FOR_SINGLE_INPUT) {
                    delayInput = now;
                    if (keyListener.isPressed(KeyCode.ENTER))
                        gameState = GAME_STATE.IN_SINGLE_GAME;
                }
                break;

            case IN_END_STATE:
                now = Timer.now();
                if (now - delayInput > Timer.TIME_FOR_SINGLE_INPUT) {
                    delayInput = now;
                    if (keyListener.isPressed(KeyCode.ENTER)) {
                        gameState = GAME_STATE.IN_MENU;
                        if (SocketGame.socket != null)
                            SocketGame.socket.close();
                    }
                }
                break;

            case END:
                break;
        }
    }

    /**
     * Getter for gameState.
     */
    public GAME_STATE getGameState() {
        return gameState;
    }

    public void setGameState(GAME_STATE state) {
        gameState = state;
    }

    /**
     * Render menu.
     */
    public void render(GraphicsContext gc) {
        switch (gameState) {
            case IN_MENU:
                for (int i = 0; i < button.size(); i++) {
                    if (choosenButton == i) {
                        button.get(i).renderChoosen(gc);
                    } else {
                        button.get(i).render(gc);
                    }
                }
                break;
            case IN_MULTIPLAYER_MENU:
            case IN_SURVIVAL_MENU:
                for (int i = 0; i < BombermanGame.map.getNumberBomber(); i++) {
                    int x = Graphics.slotCoordinates[i][Graphics.X_COORDINATE];
                    int y = Graphics.slotCoordinates[i][Graphics.Y_COORDINATE];
                    if (BombermanGame.map.getBombermans().get(i).getReady()) {
                        gc.drawImage(Graphics.readySlot[i], x, y, 120, 160);
                    } else {
                        gc.drawImage(Graphics.notReadySlot[i],
                                Graphics.slotCoordinates[i][Graphics.X_COORDINATE],
                                Graphics.slotCoordinates[i][Graphics.Y_COORDINATE], 120, 160);
                    }
                    gc.drawImage(SpriteBomber.player_down[i].getFxImageOrigin(), x + 60 - SpriteBomber.DEFAULT_SIZE / 2,
                            y + 80 - SpriteBomber.DEFAULT_SIZE / 2, SpriteBomber.DEFAULT_SIZE,
                            SpriteBomber.DEFAULT_SIZE);
                }
                for (int i = BombermanGame.map.getNumberBomber(); i < Map.MAX_NUMBER_BOMBERS; i++) {
                    gc.drawImage(Graphics.emptySlot[i],
                            Graphics.slotCoordinates[i][Graphics.X_COORDINATE],
                            Graphics.slotCoordinates[i][Graphics.Y_COORDINATE], 120, 160);
                }

                if (BombermanGame.map.getCurrentBomber() == 0)
                    startButton.render(gc);
                else {
                    if (isReady == false)
                        readyButton.render(gc);
                    else
                        readyButton.renderChoosen(gc);
                }
                break;
            case IN_PAUSE:
                pauseButton.render(gc);
            case IN_END_STATE:
                switch (gameState) {
                    case IN_SINGLE_GAME:
                        if (BombermanGame.map.getIsWin()) {
                            gc.drawImage(Graphics.winner[BombermanGame.map.getCurrentBomber()], 0, 0,
                                    Graphics.WIDTH * Sprite.SCALED_SIZE, Graphics.HEIGHT * Sprite.SCALED_SIZE);
                        } else
                            gc.drawImage(Graphics.loser[BombermanGame.map.getCurrentBomber()], 0, 0,
                                    Graphics.WIDTH * Sprite.SCALED_SIZE, Graphics.HEIGHT * Sprite.SCALED_SIZE);
                        break;
                    case IN_MULTIPLAYER_GAME:
                        if (BombermanGame.map.getIsWin()) {
                            gc.drawImage(Graphics.winner[BombermanGame.map.getCurrentBomber()], 0, 0,
                                    Graphics.WIDTH * Sprite.SCALED_SIZE, Graphics.HEIGHT * Sprite.SCALED_SIZE);
                        } else
                            gc.drawImage(Graphics.loser[BombermanGame.map.getCurrentBomber()], 0, 0,
                                    Graphics.WIDTH * Sprite.SCALED_SIZE, Graphics.HEIGHT * Sprite.SCALED_SIZE);
                        break;
                    case IN_SURVIVAL_GAME:
                        System.out.println("ccc");;
                        if (((Bomber) BombermanGame.map.getBomberman()).getDeath())
                            gc.drawImage(Graphics.loser[BombermanGame.map.getCurrentBomber()], 0, 0,
                                    Graphics.WIDTH * Sprite.SCALED_SIZE, Graphics.HEIGHT * Sprite.SCALED_SIZE);
                        else
                            gc.drawImage(Graphics.winner[BombermanGame.map.getCurrentBomber()], 0, 0,
                                    Graphics.WIDTH * Sprite.SCALED_SIZE, Graphics.HEIGHT * Sprite.SCALED_SIZE);
                        break;
                }
        }
    }
}
