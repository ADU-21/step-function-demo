package com.adu21.step.function.demo.service;

import java.util.UUID;

import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.adu21.step.function.demo.model.DeploymentInput;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * @author LukeDu
 * @date 2021/4/28
 */
@Singleton
public class DeploymentService {
    private final AWSStepFunctionHandler awsStepFunctionHandler;
    private final Gson gson;
    private final String deploymentStateMachineARN;

    @Inject
    public DeploymentService(@Named("aws.step.function.deployment.state.machine.arn") String ARN,
        Gson gson, AWSStepFunctionHandler awsStepFunctionHandler) {
        this.awsStepFunctionHandler = awsStepFunctionHandler;
        this.gson = gson;
        this.deploymentStateMachineARN = ARN;
    }

    public void executeDeployment() {
        awsStepFunctionHandler.startExecution(deploymentStateMachineARN, gson.toJson(
            DeploymentInput.builder().deploymentId(UUID.randomUUID()).build()
        ));
    }
}
