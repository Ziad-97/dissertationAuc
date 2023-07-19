package com.dissertationauc.dissertationauc.Auction.utils;


import com.dissertationauc.dissertationauc.Auction.data.UserLifeCycleData;

public class ThreadContext {
    private static ThreadLocal<UserLifeCycleData> userLifeCycleDataThreadLocal = new ThreadLocal<>();

    public static void setThreadContextData(UserLifeCycleData userLifeCycleData) {
        userLifeCycleDataThreadLocal.set(userLifeCycleData);

    }

    public static UserLifeCycleData getThreadContextData(){
        return userLifeCycleDataThreadLocal.get();

    }

    public static void clearThreadContextData(){
        userLifeCycleDataThreadLocal.remove();
    }
}
