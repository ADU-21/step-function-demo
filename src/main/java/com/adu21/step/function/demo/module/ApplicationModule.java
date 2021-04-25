package com.adu21.step.function.demo.module;

import com.adu21.step.function.demo.service.HelloWorldService;
import com.adu21.step.function.demo.service.HelloWorldServiceImpl;
import com.google.inject.AbstractModule;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloWorldService.class).to(HelloWorldServiceImpl.class).asEagerSingleton();
    }
}
