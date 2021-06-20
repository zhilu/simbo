package com.shi.simbo.task;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {

    public static ExecutorService executor = new ThreadPoolExecutor(
            5,10,5, TimeUnit.MINUTES,new LinkedBlockingQueue<>(100));
}
