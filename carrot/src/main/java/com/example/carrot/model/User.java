package com.example.carrot.model;

import lombok.Data;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;

@Data
public class User implements Serializable ,Cloneable{

    private Long id;
    private String userName;
    private Integer role;
    private String code;
    private Computer computer;
    private String password;
    private String sex;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public User deepClone(User user){
        try {
            /**
             * 深拷贝
             */
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
            byteArrayOutputStream.close();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            User clone = (User)objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return clone;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String nmt(Integer i) {
//        i = 2;  不能改，因为↓匿名类用到了i，代码有可能外部走完了但是内部↓没走完，这个时候就会复制一份给内部，如果你修改过，就可能有问题了
        Computer computer = new Computer() {
            @Override
            public void so() {
                System.out.println("in computer" + i);
            }

            @Override
            public void so2() {
                super.so2();
                System.out.println("computer in user");
            }
        };
        computer.so();
        return "6";
    }

    public void test() {
        Inner inner = new Inner();
        System.out.println(inner.s);
    }

    class Inner {
        private String s = "1";

        {
            System.out.println("inner");
        }
    }

    public static void main(String[] args) throws Exception {
        User user = new User();
        Inner inner = user.new Inner();
//        user.test();
//        Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(),UserServiceImpl.class.getInterfaces(),InvocationHandler);
        Method method = User.class.getDeclaredMethod("nmt", Integer.class);
        Class<User> userClass = User.class;
        User user1 = new User();
        Object invoke = method.invoke(user1, 2);
        System.out.println(invoke);
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback((MethodInterceptor) (o, method1, objects, methodProxy) -> {
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put(1,1);
            return o;
        });
    }
}
