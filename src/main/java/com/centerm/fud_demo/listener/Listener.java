package com.centerm.fud_demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
public class Listener implements SessionListener {
    public static final AtomicInteger sessionCount=new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.getAndIncrement();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.getAndDecrement();
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.getAndDecrement();
    }
    public AtomicInteger getSessionCount()
    {
        return sessionCount;
    }
}
