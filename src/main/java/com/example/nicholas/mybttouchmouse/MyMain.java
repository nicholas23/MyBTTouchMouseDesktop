package com.example.nicholas.mybttouchmouse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMain {

    public MyMain(){
    }

    public void setTray() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("resources/icon.png"));
            String text = "MyBTTouchMouse";
            PopupMenu popMenu = new PopupMenu();
            MenuItem itmExit = new MenuItem("Exit");
            itmExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            popMenu.add(itmExit);
            TrayIcon trayIcon = new TrayIcon(image, text, popMenu);
            try {
                tray.add(trayIcon);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyMain tray = new MyMain();
        tray.setTray();
        MyBTTouchMouseServer s = new MyBTTouchMouseServer();
        s.start();
    }
}
