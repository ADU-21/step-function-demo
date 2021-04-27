package com.adu21.step.function.demo.module;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
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
import com.google.inject.name.Names;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
@Log4j2
public class AWSStepFunctionModule extends AbstractModule {
    @Override
    protected void configure() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            log.error("Could not load config: ", e);
            System.exit(1);
        }
        Names.bindProperties(binder(), properties);
    }

    @Singleton
    @Provides
    public Set<StepFunctionActivityRunnable> stepFunctionActivities(
        List<AbstractStepFunctionActivity> stepFunctionActivities,
        AWSStepFunctionHandler awsStepFunctionHandler) {
        return stepFunctionActivities.stream()
            .map(stepFunctionActivity -> new StepFunctionActivityRunnable<>(stepFunctionActivity,
                awsStepFunctionHandler)).collect(Collectors.toSet());
    }

    @Provides
    public List<AbstractStepFunctionActivity> stepFunctionActivities(DeploymentActivity deploymentActivity,
        DeploymentSucceededActivity deploymentSucceededActivity) {
        return ImmutableList.of(deploymentActivity, deploymentSucceededActivity);
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
