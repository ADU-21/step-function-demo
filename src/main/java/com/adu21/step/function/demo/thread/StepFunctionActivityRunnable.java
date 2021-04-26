package com.adu21.step.function.demo.thread;

import com.adu21.step.function.demo.activties.AbstractStepFunctionActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
@Log4j2
@RequiredArgsConstructor
public class StepFunctionActivityRunnable<T extends AbstractStepFunctionActivity<?, ?>> implements Runnable {
    private final T stepFunctionActivity;

    @Override
    public void run() {
        log.info("running...");
        stepFunctionActivity.execute();
    }
}
