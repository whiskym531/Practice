package com.example.carrot.Controller;

import com.example.boot.service.Service;
import com.example.carrot.autoconfig.redis.JedisTemplate;
import com.example.carrot.common.EcSapUrls;
import com.example.carrot.model.Computer;
import com.example.carrot.model.User;
import com.example.carrot.service.UserService;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * spring环境测试的两个注解，搭配方法上@Test
 * @SpringBootTest
 * @RunWith(SpringRunner.class)
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
@Slf4j
@RestController
@RequestMapping("/api/admin/test")
public class Users {

    @Autowired
    private Service service;

    @Autowired
    private UserService userService;

    @Autowired
    private EcSapUrls ecSapUrls;

    @Autowired
    private JedisTemplate jedisTemplate;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean create(@RequestBody User user) {
        Boolean aBoolean = userService.create(user);
        System.out.println(aBoolean);
        return aBoolean;
    }

    @RequestMapping(value = "/find")
    public User findById(@RequestParam Long id) {
        User byId = userService.findById(id);
        System.out.println(byId);
        System.out.println(ecSapUrls.getSyncUrl());
        jedisTemplate.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.set("a","z");
                System.out.println(jedis.get("a"));
            }
        });
        return byId;
    }

    @RequestMapping("/delete")
    public Boolean delete(@RequestParam Long id) {
        Boolean delete = userService.delete(id);
        return delete;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean update(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping("/nice")
    public void auto() {
        System.out.println(service.getMyName());
        System.out.println(service.getMyNumber());
        service.setMyName("success changed");
    }
    @RequestMapping("/nice1")
    public void auto1() {
        System.out.println(service.getMyName());
        System.out.println(service.getMyNumber());
        service.setMyName("success changed to 2");
    }

    @RequestMapping("/paging")
    public List<User> paging() {
        return userService.paging();
    }

    @RequestMapping("/mq/test/{id}")
    public Boolean mtTest(@PathVariable("id")Long id){
        return userService.mq(id);
    }

    public static void main(String[] args) throws Exception {
        String[] strings = new String[]{"1", "2", "3"};
        List<Object> objects = new ArrayList<>();
        List<String> strings1 = Arrays.asList(strings);
        objects.add(1);
        objects.add(2);
        objects.add(3);
        objects.add(4);
        Object[] objects1 = objects.toArray(new Integer[0]);
        objects.remove(1);
        List<Object> objects2 = Arrays.asList(objects1);
        //objects2.remove(1); //数组转为list之后不可操作remove and add，报UnsupportedOperationException错
        System.out.println();
        System.out.println("objects1="+objects1);
        System.out.println("objects2="+objects2.get(1));
        String s = "7777";
        char[] chars = new char[20];
        s.getChars(1,2,chars,2);
        if (s.startsWith("8"))//if不加{}，那只能写一行
            System.out.println("666");
        System.out.println("555");
        /**
         * 线程池
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 3, TimeUnit.SECONDS, new ArrayBlockingQueue(10));
        System.out.println(threadPoolExecutor.getActiveCount());
        threadPoolExecutor.execute(() ->
        {
            int activeCount = threadPoolExecutor.getActiveCount();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("active--"+activeCount);
        });
        Future<Object> submit = threadPoolExecutor.submit(() -> 1);
        System.out.println("task---"+submit.get());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadPoolExecutor.getActiveCount());
        threadPoolExecutor.shutdown();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("ter---"+threadPoolExecutor.isTerminated());

    }

    class Thread1 extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run");
        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run2");
        }
    }

    @Test
    public void ThreadPool(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 3, TimeUnit.SECONDS, new ArrayBlockingQueue(10));
        Future<?> submit = threadPoolExecutor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                int i = 1;
                return i;
            }
        });
        Object o = new Object();
        try {
            o = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(o);
        AtomicInteger atomicInteger = new AtomicInteger(3);
        System.out.println(atomicInteger.get());
        atomicInteger.compareAndSet(3,4);
    }
    @Test
    public void t1() {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();

        System.out.println("sure");
        System.out.println(thread1.getState());
        System.out.println(thread2.getState());


        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 内部类
     */
    @Test
    public void nmt() {
        User user = new User();
        user.nmt(1);
    }

    /**
     * 布隆过滤器
     */
    @Test
    public void bloomTest() {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 100000, 0.01);

        bloomFilter.put("10086");

        System.out.println(bloomFilter.mightContain("123456"));
        System.out.println(bloomFilter.mightContain("10086"));
//        Jedis jedis = new Jedis();
//        jedis.set("b","b");
    }
    @Test
    public void array(){
        Integer[] target = new Integer[]{1, 2, 4,3,5,6,7};
        Integer[] arr = new Integer[]{2, 1, 3, 4,5,6};
        Arrays.sort(target);
        Arrays.sort(arr);
        System.out.println(Arrays.equals(target, arr));

        String s = "abcd";
        char[] chars = new char[20];
        System.out.println("---"+chars);
        s.getChars(1,3,chars,1);
        System.out.println(chars);
        System.out.println(chars[2]);
        System.out.println(target);
        List<Integer> integers = Arrays.asList(target);
        integers.sort(Comparator.naturalOrder());
        float f = (float) integers.size()/2;
        System.out.println(Math.round(f));
        char c = s.charAt(1);
        char d ='1';
        StringBuffer sb = new StringBuffer(c);
        System.out.println("sb----"+sb.toString());
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(10);
        arrayBlockingQueue.add(1);
        Object poll = arrayBlockingQueue.poll();
        System.out.println("poll----"+poll);
    }

    /**
     * io
     * @throws Exception
     */
    @Test
    public void iot() throws Exception{
        OutputStream outputStream = new FileOutputStream("D:\\log.txt",true); // 参数二，表示是否追加，true=追加
        outputStream.write("你好，老王\n".getBytes("utf-8"));
        outputStream.close();
        System.out.println(Runtime.getRuntime().availableProcessors());

    }
    @Test
    public void leet(){
        String s = "abcd";
        char d = s.charAt(1);
        String s1 = String.valueOf(d);

        String replace = s.replace("a", "b");
        System.out.println(replace);
        String t = "abcde";
        char res = 0;
        for (char c : s.toCharArray()) {
            res ^= c;
        }
        for (char c : t.toCharArray()) {
            res ^= c;
        }
        System.out.println("res----"+res);
    }
    @Test
    public void st(){
        String s = "abbcabfbbcvbca";
        String[] bs = s.split("b");
        System.out.println(bs.length-1);

    }

    /**
     * 深拷贝
     * @throws Exception
     */
    @Test
    public void cloneTest () throws Exception{
        User user = new User();
        user.setId(1L);
        user.setUserName("clone");
        user.setComputer(new Computer("aaa","aaa"));
        User clone = user.deepClone(user);
        user.setId(2L);
        user.setUserName("ccc");
        user.getComputer().setName("new");
        System.out.println(clone.getUserName());
        System.out.println(user.getId() == clone.getId());
        System.out.println(clone.getComputer().getName());
    }

    private HashMap sensitiveWordMap;
    @Test
    public void addSensitiveWordToHashMap() {
        Set<String> keyWordSet = new HashSet<>();
        keyWordSet.add("保温杯");
        keyWordSet.add("酸奶");
        sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
        Set<String> set = getSensitiveWord("酸奶和保温的保温杯", 1);
        System.out.println(sensitiveWordMap);
    }

    public Set<String> getSensitiveWord(String txt , int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();

        for(int i = 0 ; i < txt.length() ; i++){
            int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
            if(length > 0){    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;

    }
    @SuppressWarnings({ "rawtypes"})
    public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            System.out.println(nowMap);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            System.out.println(nowMap);
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if(1 == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词
            matchFlag = 0;
        }
        return matchFlag;
    }
    @Test
    public void hashmapT(){
        User user = new User();
        user.setUserName("user");
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(user,"11");
        user.setUserName("uuu");
        System.out.println(user);
        System.out.println(objectObjectHashMap);
    }

    /**
     * call接口&读写锁
     * @throws Exception
     */
    @Test
    public void callT() throws Exception {
        Computer computer = new Computer() {
            @Override
            public Object call() throws Exception {
                int i = 111;
                TimeUnit.SECONDS.sleep(8);
                return i;
            }
        };
        FutureTask futureTask = new FutureTask<>(computer);
        Thread future = new Thread(futureTask);
        future.start();
        ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        AtomicInteger n = new AtomicInteger(10);
        Thread startWrite = new Thread(() -> {
            reentrantReadWriteLock.writeLock().lock();
            try {
                System.out.println("start write");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            n.set(11);
            System.out.println("write---" + n);
            reentrantReadWriteLock.writeLock().unlock();
        });
        Thread startRead = new Thread(() -> {
            reentrantReadWriteLock.readLock().lock();
            try {
                System.out.println("start read");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int j = n.get();
            System.out.println("read-----"+j);
            reentrantReadWriteLock.readLock().unlock();//unlock最好放在finally里面
        });
        startWrite.start();
        startRead.start();
        startWrite.join();
        startRead.join();
//        TimeUnit.SECONDS.sleep(13);
        System.out.println("future-----"+futureTask.get());//如果调了get()，必须等待执行完
        System.out.println(n);
    }
    @Test
    public void sync(){
        synchronized (this){
            System.out.println("this");
        }
    }
    @Test
    public void hwT(){
        int[] ints = {10000};
        System.out.println(new StringBuffer(String.valueOf(ints[0])).reverse());
        System.out.println(ints.length);
        Collections.synchronizedList(new ArrayList<>());
    }
    @Test
    public void work(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        List<Integer> integers1 = integers.subList(integers.size() - 2, integers.size());
        ArrayList<Integer> integers2 = new ArrayList<>();
        integers2.add(1);
        integers2.addAll(integers1);
        System.out.println(integers1);
        integers2.add(99);
        System.out.println(integers2);
        integers2.clear();
        System.out.println(integers2);
        System.out.println(integers);
        System.out.println(15+(15>>1));
    }
    @Test
    public void resize() throws Exception{
        ArrayList<Object> objects = new ArrayList<>(15);
        System.out.println(objects.size());
        objects.add(1);
        System.out.println(objects.size());

        for (int i = 0; i < 14; i++) {
            objects.add(1);
        }
        System.out.println(objects.size());
        objects.add(1);
        System.out.println();
        /**
         * 获取容量
         */
        Field f = objects.getClass().getDeclaredField("elementData");
        f.setAccessible(true);
        Object[] o=(Object[])f.get(objects);
        System.out.println(o.length);

    }
    @Test
    public void stringTest(){
        String abc = new String("abc");
        String s = "abc";
        String s1 = "abc";
        System.out.println(s1==s);
        s1 = "ab";
        System.out.println(s1==s);
        s1 = "abc";
        s1 = new String("abc");
        System.out.println(s1==s);
        System.out.println(Math.round(-11.5));
        String s2 = "2222";
        s2.getBytes();
    }
    @Test
    public void springTest(){
        User byId = userService.findById(2L);
        System.out.println(byId);
    }
    @Test
    public void hashmapTest(){
        HashMap<Object, Object> map = new HashMap<>();
        TreeMap<Object, Object> treeMap = new TreeMap<>();
        treeMap.put("11","22");
        treeMap.put("22","33");
    }
    @Test
    public void intTest(){
        Integer i = 13;
        Integer n = 13;
        System.out.println(i == n);
    }
}
