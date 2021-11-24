package com.centerm.fud_demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在线人数监听
 * @author ouyangyi
 */
@Slf4j
public class Listener implements SessionListener {

    public static final AtomicInteger SESSION_COUNT =new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        SESSION_COUNT .getAndIncrement();
    }

    @Override
    public void onStop(Session session) {
        SESSION_COUNT .getAndDecrement();
    }

    @Override
    public void onExpiration(Session session) {
        SESSION_COUNT .getAndDecrement();
    }
}
