package com.example.nicholas.mybttouchmouse;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class MyBTTouchMouseServer extends Thread{
    private MyMouseController rob;


    public MyBTTouchMouseServer(){
        try {
            rob = new MyMouseController();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        LocalDevice local = null;
        StreamConnectionNotifier notifier;
        StreamConnection connection = null;
        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);
            UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
            System.out.println(uuid.toString());
            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier) Connector.open(url);
        } catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not turned on.");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        byte[] b = new byte[20];
        while (true) {
            try {
                System.out.println("waiting for connection...");
                connection = notifier.acceptAndOpen();
                InputStream inputStream = connection.openInputStream();
                System.out.println("waiting for input");
                try {
                    while (true) {
                        int count = inputStream.read(b);
                        if (count > 0) {
                            String command = new String(b, 0, count);
                            System.out.println(command);
                            StringTokenizer st = new StringTokenizer(command);
                            while(st.hasMoreTokens()){
                                try{
                                    paser(st.nextToken());
                                }catch(Exception e){}
                            }
                        } else {
                            System.out.println();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void paser(String command) {
        //System.out.println(command);

        if (command != null && command.length() > 0) {
            if (command.startsWith("u") || command.startsWith("d")|| command.startsWith("c")) {
                System.out.println(command);
                switch (command) {
                    case "u1":
                        rob.leftUp();
                        break;
                    case "d1":
                        rob.leftDown();
                        break;
                    case "c1":
                        rob.leftDown();
                        rob.leftUp();
                        break;
                    case "u3":
                        rob.rightUp();
                        break;
                    case "d3":
                        rob.rightDown();
                        break;
                    case "c3":
                        rob.rightDown();
                        rob.rightUp();
                        break;

                }
            }else if (command.startsWith("m")){
                command = command.substring(2);
                String[] d = command.split(":");
                rob.move(Integer.parseInt(d[0]), Integer.parseInt(d[1]));
            }else if (command.startsWith("vk")){
                switch (command) {
                    case "vkpu":
                        rob.clickPageUp();
                        break;
                    case "vkpd":
                        rob.clickPageDown();
                        break;
                }
            }
        }
    }
}
