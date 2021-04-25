package com.adu21.step.function.demo;

import com.adu21.step.function.demo.module.ApplicationModule;
import com.adu21.step.function.demo.service.HelloWorldService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class StepFunctionDemoApplication {
    private final HelloWorldService helloWorldService;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule());
        StepFunctionDemoApplication application = injector.getInstance(StepFunctionDemoApplication.class);

        application.start();
    }

    private void start() {
        helloWorldService.sayHello();
    }
}
