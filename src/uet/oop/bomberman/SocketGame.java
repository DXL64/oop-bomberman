package uet.oop.bomberman;
import java.io.IOException;
import java.net.*;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import javafx.application.Platform;
import uet.oop.bomberman.controller.CollisionManager;
import uet.oop.bomberman.controller.Direction.DIRECTION;
import uet.oop.bomberman.entities.Bomber;

public class SocketGame {

    public static final String GROUP_ADDRESS = "224.0.0.224";
    public static final int PORT = 7777;
    public final static byte[] BUFFER = new byte[4096];
    public static MulticastSocket socket;
    public static DatagramPacket inPacket;
    public static DatagramPacket outPacket;
    public static InetAddress address;
    public static String hashCode;
    public static Thread threadSocket;

    public SocketGame(List<Bomber> bombermans, Map map){
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

                    try {
                        socket.send(outPacket);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    while (true) {
                        // Receive the information and print it.
                        inPacket = new DatagramPacket(BUFFER, BUFFER.length);
                        socket.receive(inPacket);
                        String msg = new String(BUFFER, 0, inPacket.getLength());
                        System.out.println(msg);
                        String[] tokens = msg.split(","); 
                        System.out.println("msg: " + msg);
                        if(tokens[1].equals("Hello")){
                            if(!tokens[0].equals(hashCode)){
                                map.setNumberBomber(map.getNumberBomber() + 1);
                                String msgRely = hashCode + ",Hi," + (map.getNumberBomber() - 1) + "," + tokens[0]; 
                                byte[] dataRely = msgRely.getBytes();
                                outPacket = new DatagramPacket(dataRely, dataRely.length, SocketGame.address, SocketGame.PORT);
                                try {
                                    socket.send(outPacket);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                                if(map.getCurrentBomber() == 0){
                                    msgRely = hashCode + ",Rand," + Map.randomStart; 
                                    dataRely = msgRely.getBytes();
                                    outPacket = new DatagramPacket(dataRely, dataRely.length, SocketGame.address, SocketGame.PORT);
                                    try {
                                        socket.send(outPacket);
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        else if(tokens[1].equals("Rand")){
                            Map.randomStart = Integer.parseInt(tokens[2]);
                        }
                        else if(tokens[1].equals("Hi")){
                            if(tokens[3].equals(hashCode)){
                                map.setCurrentBomber(Integer.parseInt(tokens[2]));
                                map.setNumberBomber(map.getCurrentBomber() + 1);
                                bombermans.get(map.getCurrentBomber()).setIsCamFollow(true);
                                System.out.println("You are number: " + map.getCurrentBomber());
                                System.out.println("Number players current is in room: " + map.getCurrentBomber());
                            }
                        }
                        else if(tokens[1].equals("D")){
                            int curBomber = Integer.parseInt(tokens[0]);
                            Boolean success = Boolean.parseBoolean(tokens[2]);
                            bombermans.get(curBomber).updateDirect(DIRECTION.RIGHT, success);
                        }
                        else if(tokens[1].equals("S")){
                            int curBomber = Integer.parseInt(tokens[0]);
                            Boolean success = Boolean.parseBoolean(tokens[2]);
                            bombermans.get(curBomber).updateDirect(DIRECTION.DOWN, success);
                        }
                        else if(tokens[1].equals("A")){
                            int curBomber = Integer.parseInt(tokens[0]);
                            Boolean success = Boolean.parseBoolean(tokens[2]);
                            bombermans.get(curBomber).updateDirect(DIRECTION.LEFT, success);
                        }
                        else if(tokens[1].equals("W")){
                            int curBomber = Integer.parseInt(tokens[0]);
                            Boolean success = Boolean.parseBoolean(tokens[2]);
                            bombermans.get(curBomber).updateDirect(DIRECTION.UP, success);
                        }

                    }
                    /*
                    try (Socket socket = new Socket("127.0.0.1", 7777)) {
                        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                        dout.writeUTF("Hello Server");
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        String msg = (String) input.readUTF();
                        System.out.println("Client: " + msg);
                        dout.flush();
                        dout.close();
                        socket.close();
                    }
                    */
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
