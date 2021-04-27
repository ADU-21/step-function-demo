package com.adu21.step.function.demo;

import com.adu21.step.function.demo.module.AWSStepFunctionModule;
import com.adu21.step.function.demo.module.PostConstructModule;
import com.adu21.step.function.demo.thread.StepFunctionObserver;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class StepFunctionDemoApplication {
    private final StepFunctionObserver stepFunctionObserver;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AWSStepFunctionModule(), new PostConstructModule());
        injector.getInstance(StepFunctionDemoApplication.class);
    }
}
