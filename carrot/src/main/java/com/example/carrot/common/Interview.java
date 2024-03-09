package com.example.carrot.common;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class Interview {
    private Long total;
    private List<Long> secondList;
    private List<Long> minuteList;

    void hit(){
        total++;
    }
    @Scheduled(cron = "*/1 * * * * ?")
    synchronized void secondTotal(){
        secondList.add(total);
        total = 0L;
    }
    @Scheduled(cron = "* */1 * * * ?")
    void minuteTotal(){
        minuteList.add(secondList.stream().reduce(Long::sum).orElse(0L));
    }
    @Scheduled(cron = "")//定时删除
    public void autoDel(){}
    // 最近10秒的每秒平均计数量
    public long last10SecondAverage(){
        Long totalForTenSec = secondList.subList(secondList.size() - 10, secondList.size()).stream().reduce(Long::sum).orElse(0L);
        return totalForTenSec/10;
    }
    // 最近10分钟的每分钟平均计数量
    public long last10MinuteAverage(){
//        Long totalForTenMin = minuteList.subList(minuteList.size() - 10, minuteList.size()).stream().reduce(Long::sum).orElse(0L);
//        return totalForTenMin/10;
        return minuteList.get((int) ((minuteList.size()-1)-minuteList.get(minuteList.size()-11)))/10;
    };
    // 最近60分钟的每分钟平均计数量
    public long last60MinuteAverage(){
//        Long totalForTenMin = minuteList.subList(minuteList.size() - 10, minuteList.size()).stream().reduce(Long::sum).orElse(0L);
//        return totalForTenMin/60;
        return minuteList.get((int) ((minuteList.size()-1)-minuteList.get(minuteList.size()-61)))/60;

    };
    // 最近60秒的每秒计数量的集合
    public List<Long> last60SecondHistory(){
        return secondList.subList(secondList.size()-60,secondList.size());
    };
    /**
      * 通用计数器模块接口ICounter，用于系统内部的请求量计数，
      * 多个线程会频繁调用hit()方法进行计数
      *
      * 请实现ICounter接口(请关注接口实现，不需要写测试用例)
      * 不使用任何第三方的服务（包括Redis）
      */
//    interface ICounter {
//    // 调用一次，计数加1
//            void hit();
//    // 获取当前的计数统计状态
//            StatData getStat();
//    }
//    class StatData {
//    // 最近10秒的每秒平均计数量
//            public long last10SecondAverage;
//    // 最近10分钟的每分钟平均计数量
//            public long last10MinuteAverage;
//    // 最近60分钟的每分钟平均计数量
//            public long last60MinuteAverage;
//    // 最近60秒的每秒计数量的集合
//            public List<Long> last60SecondHistory;
//    }
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 201; i < 400; i++) {
            stringBuffer.append(i);
        }
        System.out.println(stringBuffer);
    }
}
