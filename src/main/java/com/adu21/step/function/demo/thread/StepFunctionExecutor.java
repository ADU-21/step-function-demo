package com.adu21.step.function.demo.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
@Log4j2
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class StepFunctionExecutor {
    private final StepFunctionActivityRunnable stepFunctionActivity;

    @PostConstruct
    public void scheduleStepFunctionActivities() {
        log.info("Start ...");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        executorService.scheduleAtFixedRate(stepFunctionActivity, 0, 1, TimeUnit.SECONDS);
        log.info("Stop ...");
    }

}
