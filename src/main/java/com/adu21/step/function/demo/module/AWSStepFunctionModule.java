package com.adu21.step.function.demo.module;

import com.adu21.step.function.demo.activties.deployment.DeploymentActivity;
import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.adu21.step.function.demo.thread.StepFunctionActivityRunnable;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
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
    public StepFunctionActivityRunnable stepFunctionActivity(DeploymentActivity deploymentActivity, AWSStepFunctionHandler awsStepFunctionHandler) {
        return new StepFunctionActivityRunnable<>(deploymentActivity, awsStepFunctionHandler);
    }

    @Singleton
    @Provides
    public DeploymentActivity deploymentActivity(AWSStepFunctionHandler stepFunctionHandler, Gson gson) {
        return new DeploymentActivity(stepFunctionHandler, gson);
    }

    @Singleton
    @Provides
    public AWSStepFunctions awsStepFunctionClient(ProfileCredentialsProvider credentialsProvider) {
        return AWSStepFunctionsClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.US_EAST_1)
            .build();
    }

    @Singleton
    @Provides
    public ProfileCredentialsProvider credentialsProvider() {
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        credentialsProvider.getCredentials();
        return credentialsProvider;
    }

    @Singleton
    @Provides
    public Gson gson() {
        return new Gson();
    }
}
