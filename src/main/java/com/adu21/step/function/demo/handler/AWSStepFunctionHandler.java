package com.adu21.step.function.demo.handler;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Log4j2
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AWSStepFunctionHandler {
    private final AWSStepFunctions awsStepFunctionClient;
}
