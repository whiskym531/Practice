package com.example.carrot.common;

public class Singleton {
    private static Singleton instance;
    private Singleton(){}

    /**
     * 懒汉模式，不安全，可通过加synchronized实现安全
     * @return
     */
    public static Singleton getInstance(){
        if (instance == null) {
            return new Singleton();
        }
        return instance;
    }
    /**
     * 饿汉模式
     */
//    private static Singleton instance = new Singleton();
//    public static Singleton getInstance(){
//        return instance;
//    }
}
