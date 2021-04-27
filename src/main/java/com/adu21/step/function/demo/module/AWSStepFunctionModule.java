package com.adu21.step.function.demo.module;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.adu21.step.function.demo.activties.AbstractStepFunctionActivity;
import com.adu21.step.function.demo.activties.deployment.DeploymentActivity;
import com.adu21.step.function.demo.activties.deployment.DeploymentSucceededActivity;
import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.adu21.step.function.demo.thread.StepFunctionActivityRunnable;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.google.common.collect.ImmutableList;
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
    public Set<StepFunctionActivityRunnable> stepFunctionActivities(
        List<AbstractStepFunctionActivity> stepFunctionActivities,
        AWSStepFunctionHandler awsStepFunctionHandler) {
        return stepFunctionActivities.stream()
            .map(stepFunctionActivity -> {
                return new StepFunctionActivityRunnable<>(stepFunctionActivity, awsStepFunctionHandler);
            }).collect(Collectors.toSet());
    }

    @Provides
    public List<AbstractStepFunctionActivity> stepFunctionActivities(DeploymentActivity deploymentActivity,
        DeploymentSucceededActivity deploymentSucceededActivity) {
        return ImmutableList.of(deploymentActivity, deploymentSucceededActivity);
    }

    @Singleton
    @Provides
    public DeploymentActivity deploymentActivity(AWSStepFunctionHandler stepFunctionHandler, Gson gson) {
        return new DeploymentActivity("arn:aws:states:us-east-1:393344114027:activity:Deployment", stepFunctionHandler,
            gson);
    }

    @Singleton
    @Provides
    public DeploymentSucceededActivity deploymentSucceededActivity(AWSStepFunctionHandler stepFunctionHandler,
        Gson gson) {
        return new DeploymentSucceededActivity("arn:aws:states:us-east-1:393344114027:activity:DeploymentSucceeded",
            stepFunctionHandler, gson);
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
