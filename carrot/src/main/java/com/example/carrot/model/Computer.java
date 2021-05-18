package com.example.carrot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Computer implements Serializable , Callable {
    private String name;
    private String cpu;
    public void so(){
        System.out.println("computer");
    }
    public void so2(){
        System.out.println("computer2");
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}
