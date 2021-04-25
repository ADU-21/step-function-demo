package com.adu21.step.function.demo;

import com.adu21.step.function.demo.module.ApplicationModule;
import com.adu21.step.function.demo.module.PostConstructModule;
import com.adu21.step.function.demo.thread.StepFunctionExecutor;
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
    private final StepFunctionExecutor stepFunctionExecutor;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule(), new PostConstructModule());
        injector.getInstance(StepFunctionDemoApplication.class);
    }
}
