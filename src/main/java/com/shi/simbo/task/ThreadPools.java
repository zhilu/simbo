package com.shi.simbo.task;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPools {

    public static ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
}
