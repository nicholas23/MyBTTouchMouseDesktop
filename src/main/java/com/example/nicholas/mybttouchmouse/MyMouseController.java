package com.example.nicholas.mybttouchmouse;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MyMouseController {
    final private Robot robot;

    public MyMouseController() throws AWTException {
        robot = new Robot();
    }

    public void move(int movePX, int movePY) {
        Point b = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove((int)(b.getX()+ movePX), (int)(b.getY() + movePY));
    }


    public void setWheelAmt(int wheelAmt) {
        robot.mouseWheel(wheelAmt);
    }

    public void rightDown() {
        robot.mousePress(InputEvent.BUTTON3_MASK);
    }

    public void rightUp() {
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }

    public void leftDown() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
    }

    public void leftUp() {
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void clickPageUp(){
        robot.keyPress(KeyEvent.VK_PAGE_UP);
        robot.keyRelease(KeyEvent.VK_PAGE_UP);
    }

    public void clickPageDown(){
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
        robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

    }
}
