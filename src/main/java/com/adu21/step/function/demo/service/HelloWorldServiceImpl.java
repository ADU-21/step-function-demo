package com.adu21.step.function.demo.service;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
public class HelloWorldServiceImpl implements HelloWorldService {
    @Override
    public void sayHello() {
        System.out.println("hello, world!");
    }
}
