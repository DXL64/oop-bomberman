package uet.oop.bomberman;
import java.io.IOException;
import java.net.*;
import java.util.List;

import javafx.application.Platform;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.GameMenu;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.entities.AnimationEntity;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.BombItem;
import uet.oop.bomberman.entities.BombManager;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.FlameItem;
import uet.oop.bomberman.entities.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

public class SocketGame {

    public static final String GROUP_ADDRESS = "224.0.0.224";
    public static final int PORT = 7777;
    public final static byte[] BUFFER = new byte[2048];
    public static MulticastSocket socket;
    public static DatagramPacket inPacket;
    public static DatagramPacket outPacket;
    public static InetAddress address;
    public static String hashCode;
    public static Thread threadSocket;

    public SocketGame(Map map){
        threadSocket = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    address = InetAddress.getByName(GROUP_ADDRESS);
                    socket = new MulticastSocket(PORT);
                    socket.setBroadcast(true);
                    socket.joinGroup(address);
                    hashCode = String.valueOf(socket.hashCode());
                    
                    String msgHello = hashCode + ",Hello";
                    byte[] data = msgHello.getBytes();
                    outPacket = new DatagramPacket(data, data.length, SocketGame.address, SocketGame.PORT);

                    List<Entity> flexEntities = map.getFlexEntities();
                    
                    try {
                        //Send messages to multicast group
                        socket.send(outPacket);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    while (true) {
                        // Receive the information and print it.
                        inPacket = new DatagramPacket(BUFFER, BUFFER.length);
                        socket.receive(inPacket);
                        String msg = new String(BUFFER, 0, inPacket.getLength());
                        String[] tokens = msg.split(","); 
                        if(tokens[1].equals("Hello")){
                            if(!tokens[0].equals(hashCode)){
                                map.setNumberBomber(map.getNumberBomber() + 1);
                                System.out.println("You are number: " + map.getCurrentBomber());
                                System.out.println("Number players current is in room: " + map.getNumberBomber());
                                String msgRely = hashCode + ",Hi," + (map.getNumberBomber() - 1) + "," + tokens[0]; 
                                byte[] dataRely = msgRely.getBytes();
                                outPacket = new DatagramPacket(dataRely, dataRely.length, SocketGame.address, SocketGame.PORT);
                                try {
                                    socket.send(outPacket);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                        else if(tokens[1].equals("Hi")){
                            if(tokens[3].equals(hashCode)){
                                map.setCurrentBomber(Integer.parseInt(tokens[2]));
                                map.setNumberBomber(map.getCurrentBomber() + 1);
                                System.out.println("You are number: " + map.getCurrentBomber());
                                System.out.println("Number players current is in room: " + map.getNumberBomber());
                                Bomber bomberCur = (Bomber)flexEntities.get(map.getCurrentBomber());
                                bomberCur.setIsCamFollow(true);
                            }
                        }
                        else if(tokens[1].equals("D")){
                            int cur = Integer.parseInt(tokens[0]);
                            Boolean success = Boolean.parseBoolean(tokens[2]);
                            AnimationEntity animationEntity = (AnimationEntity)flexEntities.get(cur);
                            animationEntity.updateDirect(DIRECTION.RIGHT, success);
                        }
                        else if(tokens[1].equals("S")){
                            int cur = Integer.parseInt(tokens[0]);
                            Boolean success = Boolean.parseBoolean(tokens[2]);
                            AnimationEntity animationEntity = (AnimationEntity)flexEntities.get(cur);
                            animationEntity.updateDirect(DIRECTION.DOWN, success);
                        }
                        else if(tokens[1].equals("A")){
                            int cur = Integer.parseInt(tokens[0]);
                            Boolean success = Boolean.parseBoolean(tokens[2]);
                            AnimationEntity animationEntity = (AnimationEntity)flexEntities.get(cur);
                            animationEntity.updateDirect(DIRECTION.LEFT, success);
                        }
                        else if(tokens[1].equals("W")){
                            int cur = Integer.parseInt(tokens[0]);
                            Boolean success = Boolean.parseBoolean(tokens[2]);
                            AnimationEntity animationEntity = (AnimationEntity)flexEntities.get(cur);
                            animationEntity.updateDirect(DIRECTION.UP, success);
                        }
                        else if(tokens[1].equals("Bomb")){
                            int curBomber = Integer.parseInt(tokens[0]);
                            Bomber bomberCur = (Bomber)flexEntities.get(curBomber);
                            BombManager bombManager = bomberCur.getBombManager();
                            int xBomb = Integer.parseInt(tokens[2]);
                            int yBomb = Integer.parseInt(tokens[3]);
                            bombManager.addBomb(new Bomb(xBomb, yBomb, Sprite.bomb.getFxImage(), bombManager.getFlame()));
                        }
                        else if(tokens[1].equals("Item")){
                            int typeItem =  Integer.parseInt(tokens[2]);
                            int xMod = Integer.parseInt(tokens[3]);
                            int yMod = Integer.parseInt(tokens[4]);
                            map.setItem(yMod, xMod, typeItem);
                        }
                        else if(tokens[1].equals("Start")){
                            if(GameMenu.gameState == GameMenu.GAME_STATE.IN_MULTIPLAYER_MENU){
                                System.out.println("[ENTER MULTIPLAYER GAME]");
                                BombermanGame.menu.setGameState(GameMenu.GAME_STATE.IN_MULTIPLAYER_GAME);
                            }
                            else if(GameMenu.gameState == GameMenu.GAME_STATE.IN_SURVIVAL_MENU){
                                System.out.println("[ENTER SURVIVAL GAME]");
                                BombermanGame.menu.setGameState(GameMenu.GAME_STATE.IN_SURVIVAL_GAME);
                            }
                        }
                        else if(tokens[1].equals("Ready")){
                            int curBomber = Integer.parseInt(tokens[0]);
                            Bomber bomberCur = (Bomber)flexEntities.get(curBomber);
                            bomberCur.setReady(true);
                        }
                        else if(tokens[1].equals("NotReady")){
                            int curBomber = Integer.parseInt(tokens[0]);
                            Bomber bomberCur = (Bomber)flexEntities.get(curBomber);
                            bomberCur.setReady(false);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    socket.close();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    socket.close();
                }
            }
        });
        threadSocket.start();
    }
}
