package com.adu21.step.function.demo.thread;

import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
@Log4j2
public class StepFunctionActivityRunnable implements Runnable {
    @Override
    public void run() {
        log.info("running...");
    }
}
