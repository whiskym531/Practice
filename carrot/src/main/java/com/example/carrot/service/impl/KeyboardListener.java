package com.example.carrot.service.impl;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.junit.Test;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by IntelliJ IDEA.
 * Author: warm
 * Date: 2022/11/08
 * Description:
 */
public class KeyboardListener implements NativeKeyListener {
    Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    {
        logger.setLevel(Level.OFF);
    }

    public static volatile boolean flag = false;
    public static volatile boolean isRobot = false;

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        System.out.println("type");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
//        System.out.println("press");
        System.out.println(nativeKeyEvent.getKeyCode());
        System.out.println("pressing");
        if (nativeKeyEvent.getKeyCode() == 18 && !flag) {
            flag = true;
        }
//        flag = !flag;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        System.out.println("release");
        if (nativeKeyEvent.getKeyCode() == 18 && !isRobot) {
            flag = false;
        }
    }

    public static void main(String[] args) throws Exception {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new KeyboardListener());
//        GlobalScreen.addNativeKeyListener(new KeyboardListener2());
        Robot robot = new Robot();
        new Thread(() -> {
            while (true) {
                while (flag) {
                    robot.keyPress(69);
                    isRobot = true;
                    robot.keyRelease(69);
                    isRobot = false;
//                    robot.keyRelease(76);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Test
    public void pressR() throws AWTException {
        Robot robot = new Robot();
        for (int i = 0; i < 10; i++) {
            robot.keyPress(82);
            robot.keyRelease(82);
        }
    }

    @Test
    public void pressQuestionMark() throws Exception {
        Thread.sleep(3000);
        Robot robot = new Robot();
        for (int i = 0; i < 10; i++) {
            robot.keyPress(69);
            robot.keyRelease(69);
            Thread.sleep(3000);
            System.out.println(i);
        }
    }

    @Test
    public void press() throws Exception {
        Thread.sleep(3000);
        Robot robot = new Robot();
        for (int i = 0; i < 10; i++) {
            robot.keyPress(16);
            robot.keyPress(47);
            robot.keyRelease(47);
            robot.keyRelease(16);
            robot.keyPress(10);
            robot.keyRelease(10);
            Thread.sleep(10);
            System.out.println(i);
        }
    }

    @Test
    public void mouseMove() throws Exception {
        Robot robot = new Robot();
        robot.mouseMove(1,1);
        robot.mouseMove(960, 540);
        System.out.println(MouseInfo.getPointerInfo().getLocation().getX());
        System.out.println(MouseInfo.getPointerInfo().getLocation().getY());
    }

    @Test
    public void pasteAndSend() throws Exception {
        Thread.sleep(3000);
        Robot robot = new Robot();
        for (int i = 0; i < 10; i++) {
            robot.keyPress(17);
            robot.keyPress(86);
            robot.keyRelease(86);
            robot.keyRelease(17);
            robot.keyPress(10);
            robot.keyRelease(10);
            Thread.sleep(10);
            System.out.println(i);
        }
    }
    @Test
    public void mouseRecord() throws Exception {
        Thread.sleep(1000);
        Robot robot = new Robot();
        for (int i = 0; i < 300; i++) {
            int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
            int y = (int)MouseInfo.getPointerInfo().getLocation().getY();
            robot.mouseMove(x,y+5);
//            System.out.println("---------------------------");
            System.out.println(x);
            System.out.println(y);
            Thread.sleep(10);
        }
    }
}
