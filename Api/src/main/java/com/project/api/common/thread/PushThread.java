package com.project.api.common.thread;

import com.project.common.dto.PushDto;
import com.project.common.util.JPushUtil;

/**
 * Created by dell on 2016/9/18.
 */
public class PushThread implements Runnable{
    private PushDto pushDto;

    public PushThread(PushDto pushDto) {
        this.pushDto = pushDto;
    }

    @Override
    public void run() {
        JPushUtil.sendNotification(pushDto);
    }
}
