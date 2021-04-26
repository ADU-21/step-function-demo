package com.adu21.step.function.demo.module;

import com.adu21.step.function.demo.activties.deployment.DeploymentActivity;
import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.adu21.step.function.demo.thread.StepFunctionActivityRunnable;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
public class AWSStepFunctionModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Singleton
    @Provides
    public StepFunctionActivityRunnable stepFunctionActivity(DeploymentActivity deploymentActivity) {
        return new StepFunctionActivityRunnable<>(deploymentActivity);
    }

    @Singleton
    @Provides
    public DeploymentActivity deploymentActivity(AWSStepFunctionHandler stepFunctionHandler, Gson gson) {
        return new DeploymentActivity(stepFunctionHandler, gson);
    }

    @Singleton
    @Provides
    public AWSStepFunctions awsStepFunctionClient() {
        return AWSStepFunctionsClientBuilder.defaultClient();
    }

    @Singleton
    @Provides
    public Gson gson() {
        return new Gson();
    }
}
