package com.memoryboost.util.email;

import org.springframework.mail.javamail.JavaMailSender;

public class MemoryBoostSignUpMailSenderThread implements Runnable {

    private MemoryBoostMailhandler memoryBoostMailhandler;

    public MemoryBoostSignUpMailSenderThread(MemoryBoostMailhandler memoryBoostMailhandler) {
        this.memoryBoostMailhandler = memoryBoostMailhandler;
    }

    @Override
    public void run() {

        memoryBoostMailhandler.send();

    }
}
