package org.example;

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
                    System.out.println(isRobot);
                    robot.keyRelease(69);
                    isRobot = false;
                    System.out.println(isRobot);
//                    robot.keyRelease(76);
                    try {
                        Thread.sleep(70);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
