package com.adu21.step.function.demo.thread;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
@Log4j2
@Singleton
public class StepFunctionObserver {
    private final Set<StepFunctionActivityRunnable> stepFunctionActivities;
    private ScheduledExecutorService stepFunctionObservedThreadPool;

    @Inject
    public StepFunctionObserver(
        Set<StepFunctionActivityRunnable> stepFunctionActivities) {
        this.stepFunctionActivities = stepFunctionActivities;
        this.stepFunctionObservedThreadPool = Executors.newScheduledThreadPool(stepFunctionActivities.size());
    }

    @PostConstruct
    public void scheduleStepFunctionActivities() {
        log.info("Start aws step function activity observe thread pool.");
        stepFunctionActivities.forEach(stepFunctionActivity ->
            stepFunctionObservedThreadPool.scheduleAtFixedRate(stepFunctionActivity, 0, 1, TimeUnit.SECONDS)
        );
    }

}
