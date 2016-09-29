package com.project.cs.job;

import com.project.common.dto.PushDto;
import com.project.common.util.JPushUtil;

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
