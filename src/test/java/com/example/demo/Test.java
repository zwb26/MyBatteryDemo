package com.example.demo;

import redis.clients.jedis.Jedis;

public class Test {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        //取值
        System.out.println("redis取cmd命令建立的值name:  " + jedis.get("name"));
        //建立新值
        jedis.set("app" , "CSDN");
        System.out.println("使用java新建的值app:  " + jedis.get("app"));
    }
}
